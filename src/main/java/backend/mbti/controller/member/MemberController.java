package backend.mbti.controller.member;

import backend.mbti.domain.dto.MbtiGroupRequest;
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

    // 회원 탈퇴

    // 아이디 찾기

    // 비밀번호 찾기

    // 모두 조회 (관리자 페이지 예정)
    @GetMapping("")
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}


