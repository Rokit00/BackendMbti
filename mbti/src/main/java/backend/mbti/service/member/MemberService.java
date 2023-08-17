package backend.mbti.service.member;

import backend.mbti.domain.dto.MemberJoinRequest;


public interface MemberService {
    String join(String userId, String password, String nickName, String birthday, String email);
    String login(String userId, String password);
}
