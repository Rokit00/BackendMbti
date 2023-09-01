package backend.mbti.service.member;

import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.member.Member;

import java.util.Map;


public interface MemberService {
    // 회원가입
    String signup(MemberSignUpRequest memberSignUpRequest);

    // 로그인
    String login(MemberLoginRequest memberLoginRequest);

    // 화원 탈퇴
    boolean deleteMember(Long memberId);

    // 중복
    boolean isUserIdDuplicate(String userId);

    // 비밀번호 찾기
    String requestPasswordReset(String userId, String email);

    // 아이디 찾기
    String findUsernameByEmailAndPhoneNumber(String birthday, String email);

    // 회원 정보 수정
    boolean updateMember(Long id, Map<String, String> updates);
}
