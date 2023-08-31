package backend.mbti.service.member;

import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.utils.Jwt;

import java.util.Map;


public interface MemberService {
    // 회원가입
    Long signup(MemberSignUpRequest memberSignUpRequest);

<<<<<<< HEAD

=======
    // 로그인
    Jwt login(String userId, String password);

    // 화원 탈퇴
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
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
