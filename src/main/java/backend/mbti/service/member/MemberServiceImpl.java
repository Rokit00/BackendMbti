package backend.mbti.service.member;

import backend.mbti.domain.dto.mbti.MbtiGroupRequest;
import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.mbti.MbtiRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.configuration.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간


    // 회원가입
    @Override
    public String signup(MemberSignUpRequest memberSignUpRequest) {

        String encPwd = bCryptPasswordEncoder.encode(memberSignUpRequest.getPassword());

        Member member = memberRepository.save(memberSignUpRequest.toEntity(encPwd));

        // 에러 처리 해야함

        return member.getUserId();
    }


    // 로그인
    @Override
    public String login(MemberLoginRequest memberLoginRequest) {
        // 아이디 틀림
        Member selectedUser = memberRepository.findByUserId(memberLoginRequest.getUserId())
                .orElseThrow(() ->new AppException(ErrorCode.USERNAME_NOT_FOUND, memberLoginRequest.getUserId() + "없습니다"));
        // 패스워드 틀림
        if (!bCryptPasswordEncoder.matches(memberLoginRequest.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드 틀림");
        }
        String token = JwtProvider.createToken(selectedUser.getUserId(), key, expireTimeMs);
        // 앞에서 Exception(예외) 안났으면 토큰 반환
        return token;
    }

    // 아이디 중복
    public boolean isUserIdDuplicate(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    // 아이디 찾기
    public String findUsernameByEmailAndPhoneNumber(String birthday, String email) {
        Member member = memberRepository.findByBirthdayAndEmail(birthday, email);
        if (member != null) {
            return member.getUserId();
        }
        return null;
    }

    // 비밀번호 찾기
    public String requestPasswordReset(String userId, String email) {
        Member member = memberRepository.findByUserIdAndEmail(userId, email);

        if (member != null) {
            String temporaryPassword = generateTemporaryPassword();
            member.setPassword(temporaryPassword);
            memberRepository.save(member);

            return temporaryPassword;
        }
        return null;
    }

    // 비밀번호 생성
    public String generateTemporaryPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder temporaryPassword = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            temporaryPassword.append(characters.charAt(index));
        }

        return temporaryPassword.toString();
    }
}
