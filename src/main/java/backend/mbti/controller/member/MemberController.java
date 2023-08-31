package backend.mbti.controller.member;

<<<<<<< HEAD
import backend.mbti.domain.dto.MbtiGroupRequest;
<<<<<<< HEAD
=======
import backend.mbti.domain.dto.MemberFindId;
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
import backend.mbti.domain.dto.MemberJoinRequest;
import backend.mbti.domain.dto.MemberLoginRequest;
=======
import backend.mbti.domain.dto.member.MemberFindId;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.dto.member.MemberLoginRequest;
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
import backend.mbti.domain.member.Member;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.service.member.MemberService;
import backend.mbti.utils.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
<<<<<<< HEAD
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(
                memberJoinRequest.getUserId()
                , memberJoinRequest.getPassword()
                , memberJoinRequest.getNickName()
                , memberJoinRequest.getBirthday()
                , memberJoinRequest.getEmail()
        );
<<<<<<< HEAD
        return ResponseEntity.ok().body("회원가입 축하합니다.");
=======
        return ResponseEntity.ok().body("회원가입 축하합니다");
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
=======
    @PostMapping
    public Long signup(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        return memberService.signup(memberSignUpRequest);
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Jwt> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        Jwt token = memberService.login(memberLoginRequest.getUserId(), memberLoginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

<<<<<<< HEAD
    // 회원 탈퇴

    // 아이디 찾기

    // 비밀번호 찾기
=======
    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("로그아웃 완료");
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        // 회원 삭제 로직을 호출
        boolean deleted = memberService.deleteMember(id);
        if (deleted) {
            return ResponseEntity.ok().body("회원 탈퇴가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("회원 탈퇴에 실패했습니다.");
        }
    }

    // 회원 가입 시 아이디 중복
    @GetMapping("/check-duplicate")
    public ResponseEntity<String> checkDuplicateUserId(@RequestParam("userId") String userId) {
        boolean isDuplicate = memberService.isUserIdDuplicate(userId);
        if (isDuplicate) {
            return ResponseEntity.badRequest().body("이미 사용 중인 아이디입니다.");
        } else {
            return ResponseEntity.ok().body("사용 가능한 아이디입니다.");
        }
    }

    // 아이디 찾기
    @PutMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestBody MemberFindId memberFindId) {
        String username = memberService.findUsernameByEmailAndPhoneNumber(memberFindId.getBirthday(), memberFindId.getEmail());
        if (username != null) {
            return ResponseEntity.ok().body("찾으시는 아이디는 " + username + "입니다.");
        } else {
            return ResponseEntity.badRequest().body("일치하는 아이디가 없습니다.");
        }
    }


    // 비밀번호 찾기
    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String userId, @RequestParam String email) {
        String newPassword = memberService.requestPasswordReset(userId, email);
        if (newPassword != null) {

            return ResponseEntity.ok().body("비밀번호 재설정 완료." + newPassword);
        } else {
            return ResponseEntity.badRequest().body("오류.");
        }
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        boolean success = memberService.updateMember(id, updates);
        if (success) {
            return ResponseEntity.ok().body("회원 정보가 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("회원 정보 수정에 실패했습니다.");
        }
    }
<<<<<<< HEAD
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c

    // 모두 조회 (관리자 페이지 예정)
    @GetMapping("")
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
=======
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
}


