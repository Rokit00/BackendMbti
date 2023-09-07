package backend.mbti.service.api;

import backend.mbti.configuration.kakao.KakaoProfile;
import backend.mbti.configuration.kakao.OAuthToken;
import backend.mbti.domain.dto.member.MemberSignUpRequest;

import backend.mbti.domain.member.Member;

import backend.mbti.repository.member.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService{

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void processKakaoCallback(String code) {
        RestTemplate rt = new RestTemplate();


        //httpheader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //httpbody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9394c1ee0de2fd55a8ccc154f6cc5114");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        //httpheader와 httpbody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest=
                new HttpEntity<>(params, headers);

        //http 요청
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //객체 저장
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(oAuthToken.getAccess_token());


        RestTemplate rt2 = new RestTemplate();

        //httpheader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //httpheader와 httpbody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //http 요청
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        System.out.println(response2.getBody());

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("이메일 = " + kakaoProfile.getKakao_account().getEmail());



        //Member 오브젝트
        MemberSignUpRequest kakaoUser = MemberSignUpRequest.builder()
                .userId(kakaoProfile.getKakao_account().getEmail() + kakaoProfile.getId())
                .password("cosKey")
                .nickName(kakaoProfile.getProperties().getNickname()) //getKakao_account().getProfile().getNickname()
                .birthday(kakaoProfile.getKakao_account().getBirthday())
                .email(kakaoProfile.getKakao_account().getEmail())
                .build();

        kakaoSignup(kakaoUser);

        //로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUserId(), kakaoUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //카카오 회원가입
    @Override
    public Long kakaoSignup(MemberSignUpRequest memberSignUpRequest) {

        String encPwd = bCryptPasswordEncoder.encode(memberSignUpRequest.getPassword());

        Member member = memberRepository.save(memberSignUpRequest.toEntity(encPwd));

        if (member != null) {
            return member.getId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}