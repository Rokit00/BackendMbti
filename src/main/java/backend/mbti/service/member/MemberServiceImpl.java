package backend.mbti.service.member;

import backend.mbti.domain.member.Member;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    // Jwt 키, 만료 시간
    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

    // 회원가입
    @Override
    public String join(String userId, String password, String nickName, String birthday, String email) {

        // userId 중복 체크
        memberRepository.findByUserId(userId)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userId + "는 이미 있습니다.");
                });

        // 저장
        Member member = Member.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .nickName(nickName)
                .birthday(birthday)
                .email(email)
                .build();
        memberRepository.save(member);

        return "SUCCESS";
    }

    // 로그인
    @Override
    public String login(String userId, String password) {
        // 아이디 없음
        Member selectedUser = memberRepository.findByUserId(userId)
                .orElseThrow(() ->new AppException(ErrorCode.USERNAME_NOT_FOUND, userId + "없습니다"));
        // 패스워드 틀림
        if (!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드 틀림");
        }
        String token = JwtTokenUtil.createToken(selectedUser.getUserId(), key, expireTimeMs);
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
