package backend.mbti.service.api;

import backend.mbti.domain.dto.member.MemberSignUpRequest;

public interface KakaoService {
    // 카카오 로그인
    void processKakaoCallback(String code);
}
