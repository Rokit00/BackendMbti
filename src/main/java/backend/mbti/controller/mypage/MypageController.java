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

    private final MypageService mypageService;

    // 유저 정보 뷰
    @GetMapping
    public ResponseEntity<Member> viewUserInfo(Authentication authentication) {
        String username = authentication.getName();

        Member member = mypageService.getUserInfo(username);

        return ResponseEntity.ok(member);
    }

    // 내 정보 수정 (테스트 완료)
    @PutMapping("/update-all")
    public ResponseEntity<Member> updateAllMemberInfo(@RequestBody MemberUpdateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Member updatedMember = mypageService.updateAllMemberInfo(request, username);

        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 프로필 이미지 (수정도 여기 api로 보내야함) - 테스트 X
    @PostMapping("/{memberId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long memberId, @RequestParam("file") MultipartFile file) {
        mypageService.uploadProfilePicture(memberId, file);
        return ResponseEntity.ok("Profile picture uploaded successfully");
    }


    // 내가 만든 케미 (테스트 X)
    @PostMapping
    public ResponseEntity<Mbti> createMbtiGroup(@RequestBody MbtiGroupRequest request, Authentication authentication) {
        String userId = authentication.getName();
        Mbti createdGroup = mypageService.createMbtiGroup(request, userId);
        return ResponseEntity.ok(createdGroup);
    }

    // 내가 만든 토론 (테스트 완료, 유저 null)
    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsByMember(@PathVariable String userId, Authentication authentication) {
        String loggedInUserId = authentication.getName();

        if (!userId.equals(loggedInUserId)) {
            throw new AccessDeniedException("You are not allowed to view posts for this member");
        }

        List<Post> posts = mypageService.getPostsByMember(userId);
        return ResponseEntity.ok(posts);
    }

    // 문의하기 (ADMIN 계정으로 일단 보류)

    // 회원 탈퇴 (테스트 완료)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String userId, Authentication authentication) {
        String username = authentication.getName();
        log.info(username);
        mypageService.deleteMember(userId, username);
        log.info("delete");
        return ResponseEntity.noContent().build();
    }
}
