package backend.mbti.service.api;

import backend.mbti.domain.dto.member.MemberSignUpRequest;

public interface OAuthService {
    void processKakaoCallback(String code);
}
