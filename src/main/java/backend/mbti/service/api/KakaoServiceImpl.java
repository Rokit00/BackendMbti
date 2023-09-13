package backend.mbti.service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoServiceImpl implements KakaoService {
    @Override
    public String processKakaoCallback(String code) {
        RestTemplate rt = new RestTemplate();


        //httpheader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //httpbody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9394c1ee0de2fd55a8ccc154f6cc5114"); //카카오 디벨로퍼의 REST API 키
        params.add("redirect_uri", "http://localhost:8080/kakao");
        params.add("code", code);


        // HttpHeader + HttpBody
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //http 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                Object.class
        );
        return code;
    }
}