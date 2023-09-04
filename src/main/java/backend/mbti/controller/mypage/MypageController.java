package backend.mbti.controller.mypage;

import backend.mbti.domain.dto.mbti.MbtiGroupRequest;
import backend.mbti.domain.dto.mypage.MemberUpdateRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.service.member.MemberService;
import backend.mbti.service.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MypageController {

    private final MemberService memberService;
    private final MypageService mypageService;

    // 내 정보(정보 수정)
    @PutMapping("/{userId}/update-all")
    public ResponseEntity<Member> updateAllMemberInfo(@PathVariable String userId, @RequestBody MemberUpdateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Member updatedMember = mypageService.updateAllMemberInfo(userId, request, username);

        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 프로필 이미지 (수정도 여기 api로 보내야함)
    @PostMapping("/{memberId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long memberId, @RequestParam("file") MultipartFile file) {
        mypageService.uploadProfilePicture(memberId, file);
        return ResponseEntity.ok("Profile picture uploaded successfully");
    }


    // 내가 만든 케미
    @PostMapping
    public ResponseEntity<Mbti> createMbtiGroup(@RequestBody MbtiGroupRequest request, Authentication authentication) {
        String userId = authentication.getName(); // 현재 인증된 사용자의 username 가져오기
        Mbti createdGroup = mypageService.createMbtiGroup(request, userId);
        return ResponseEntity.ok(createdGroup);
    }

    // 내가 만든 토론
    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsByMember(@PathVariable String userId, Authentication authentication) {
        String loggedInUserId = authentication.getName(); // 현재 인증된 사용자의 username 가져오기

        if (!userId.equals(loggedInUserId)) {
            throw new AccessDeniedException("You are not allowed to view posts for this member");
        }

        List<Post> posts = mypageService.getPostsByMember(userId);
        return ResponseEntity.ok(posts);
    }

    // 문의하기 (ADMIN 계정으로 일단 보류)

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String userId, Authentication authentication) {
        String username = authentication.getName();
        mypageService.deleteMember(userId, username);
        return ResponseEntity.noContent().build();
    }
}
