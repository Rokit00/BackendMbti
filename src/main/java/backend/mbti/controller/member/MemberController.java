package backend.mbti.controller.member;

import backend.mbti.domain.dto.mbti.MbtiGroupRequest;
import backend.mbti.domain.dto.member.MemberFindId;
import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 (테스트 완료)
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        String signUp = memberService.signup(memberSignUpRequest);
        log.info(memberSignUpRequest.getNickName());
        return ResponseEntity.ok().body(signUp + ": SignUp Success");
    }

    // 로그인 (테스트 완료)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String token = memberService.login(memberLoginRequest);
        return ResponseEntity.ok(token);
    }

    // 로그아웃 (테스트 완료)
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("로그아웃 완료");
    }

    // 회원 가입 시 아이디 중복 (테스트 X)
    @GetMapping("/check-duplicate")
    public ResponseEntity<String> checkDuplicateUserId(@RequestParam("userId") String userId) {
        boolean isDuplicate = memberService.isUserIdDuplicate(userId);
        if (isDuplicate) {
            return ResponseEntity.badRequest().body("이미 사용 중인 아이디입니다.");
        } else {
            return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
        }
    }

    // 아이디 찾기 (테스트 X)
    @PutMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestBody MemberFindId memberFindId) {
        String username = memberService.findUsernameByEmailAndPhoneNumber(memberFindId.getBirthday(), memberFindId.getEmail());
        if (username != null) {
            return ResponseEntity.ok().body("찾으시는 아이디는 " + username + "입니다.");
        } else {
            return ResponseEntity.badRequest().body("일치하는 아이디가 없습니다.");
        }
    }


    // 비밀번호 찾기 (테스트 X)
    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String userId, @RequestParam String email) {
        String newPassword = memberService.requestPasswordReset(userId, email);
        if (newPassword != null) {

            return ResponseEntity.ok().body("비밀번호 재설정 완료." + newPassword);
        } else {
            return ResponseEntity.badRequest().body("오류.");
        }
    }

    // MBTI GROUP 저장 (구현해야함)

}


