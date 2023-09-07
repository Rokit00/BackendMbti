package backend.mbti.controller.api;

import backend.mbti.service.api.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
