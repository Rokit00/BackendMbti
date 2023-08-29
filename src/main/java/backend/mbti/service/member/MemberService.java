package backend.mbti.service.member;

import backend.mbti.domain.dto.MemberJoinRequest;

import java.util.Map;


public interface MemberService {
    String join(String userId, String password, String nickName, String birthday, String email);
    String login(String userId, String password);

    boolean deleteMember(Long memberId);

    boolean isUserIdDuplicate(String userId);

    // 비밀번호 찾기
    String requestPasswordReset(String userId, String email);

    String findUsernameByEmailAndPhoneNumber(String birthday, String email);

    // 회원 정보 수정
    boolean updateMember(Long id, Map<String, String> updates);
}
