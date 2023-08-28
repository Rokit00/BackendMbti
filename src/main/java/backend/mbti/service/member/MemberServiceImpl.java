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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

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
        // 앞에서 Exception(예외) 안났으면 토큰 ㄱㄱ
        return token;
    }

    // 회원 탈퇴
    @Override
    public boolean deleteMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // 회원 관련 데이터를 삭제하거나 비활성화 로직을 수행합니다.
            // 예를 들어, 회원 데이터를 삭제하는 경우:
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
}
