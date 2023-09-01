package backend.mbti.service.member;

import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.member.Member;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.configuration.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
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


    public boolean isUserIdDuplicate(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    // 회원 탈퇴
    @Override
    public boolean deleteMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberRepository.deleteById(memberId);
            return true;
        }
        return false;
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

    // 회원 정보 수정
    public boolean updateMember(Long id, Map<String, String> updates) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            if (updates.containsKey("userId")) {
                member.setUserId(updates.get("userId"));
            }

            if (updates.containsKey("password")) {
                member.setPassword(bCryptPasswordEncoder.encode(updates.get("password")));
            }

            if (updates.containsKey("nickName")) {
                member.setNickName(updates.get("nickName"));
            }

            if (updates.containsKey("email")) {
                member.setEmail(updates.get("email"));
            }

            if (updates.containsKey("birthday")) {
                member.setBirthday(updates.get("birthday"));
            }

            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
