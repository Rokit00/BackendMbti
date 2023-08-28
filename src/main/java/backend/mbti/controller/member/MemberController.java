package backend.mbti.controller.member;

import backend.mbti.domain.dto.MbtiGroupRequest;
import backend.mbti.domain.dto.MemberFindId;
import backend.mbti.domain.dto.MemberJoinRequest;
import backend.mbti.domain.dto.MemberLoginRequest;
import backend.mbti.domain.member.Member;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.service.mbti.MbtiService;
import backend.mbti.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(
                memberJoinRequest.getUserId()
                , memberJoinRequest.getPassword()
                , memberJoinRequest.getNickName()
                , memberJoinRequest.getBirthday()
                , memberJoinRequest.getEmail()
        );
        return ResponseEntity.ok().body("회원가입 축하합니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequest memberLoginRequest) {
        String token = memberService.login(memberLoginRequest.getUserId(), memberLoginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }

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

    // 모두 조회 (관리자 페이지 예정)
    @GetMapping("")
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}


