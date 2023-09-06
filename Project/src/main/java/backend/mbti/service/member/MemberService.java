package backend.mbti.service.member;

import backend.mbti.domain.dto.mbti.MbtiGroupRequest;
import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.member.Member;

import java.util.Map;


public interface MemberService {

    // 회원가입
    String signup(MemberSignUpRequest memberSignUpRequest);

    // 로그인
    String login(MemberLoginRequest memberLoginRequest);

    // 중복
    boolean isUserIdDuplicate(String userId);

    // 아이디 찾기
    String findUsernameByEmailAndPhoneNumber(String birthday, String email);

    // 비밀번호 찾기
    String requestPasswordReset(String userId, String email);
}
