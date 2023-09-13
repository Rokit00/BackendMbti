package backend.mbti.controller.api;

import backend.mbti.service.api.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String token = kakaoService.processKakaoCallback(code);
        return ResponseEntity.ok(token);
    }
}
