package backend.mbti.service.api;

import backend.mbti.domain.dto.member.MemberSignUpRequest;

public interface KakaoService {
    // 카카오 로그인
    void processKakaoCallback(String code);

    // 카카오 회원가입
    Long kakaoSignup(MemberSignUpRequest memberSignUpRequest);
}
