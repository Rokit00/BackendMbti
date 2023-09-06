package backend.mbti.controller.member;

import backend.mbti.service.member.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        oAuthService.processKakaoCallback(code);
        return ResponseEntity.ok("카카오 로그인 완료");
    }
}
