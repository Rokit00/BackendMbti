package backend.mbti.controller.mypage;

import backend.mbti.domain.dto.mypage.MbtiGroupRequest;
import backend.mbti.domain.dto.mypage.MemberUpdateRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
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

    // 내 정보 수정
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

    // 프로필 저장
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String username = authentication.getName();
        mypageService.updateProfilePicture(username, file);
        return ResponseEntity.ok("프로필 업데이트 완료");
    }

    // 프로필 불러오기
    @GetMapping("/{memberId}/profile-image-url")
    public ResponseEntity<String> getProfileImageUrl(@PathVariable Long memberId) {
        String imageUrl = mypageService.getProfile(memberId);
        return ResponseEntity.ok(imageUrl);
    }



    // 내가 만든 케미 저장
    @PostMapping("/mbti")
    public ResponseEntity<Mbti> createMbtiGroup(@RequestBody MbtiGroupRequest request, Authentication authentication) {
        String userId = authentication.getName();
        Mbti createdGroup = mypageService.createMbtiGroup(request, userId);
        return ResponseEntity.ok(createdGroup);
    }

    // 내가 만든 케미 불러오기
    @GetMapping("/mbti")
    public ResponseEntity<List<Mbti>> viewMbtiGroup(Authentication authentication) {
        String userId = authentication.getName();
        List<Mbti> viewMbtiGroup = mypageService.viewMbtiGroup(userId);
        return ResponseEntity.ok(viewMbtiGroup);
    }

    // 내가 만든 토론
    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsByMember(@PathVariable String userId, Authentication authentication) {
        String authUserId = authentication.getName();

        if (!userId.equals(authUserId)) {
            throw new AccessDeniedException("접속 불가!");
        }

        List<Post> posts = mypageService.getPostsByMember(userId);
        return ResponseEntity.ok(posts);
    }

    // 문의하기 (ADMIN 계정으로 일단 보류)

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember(Authentication authentication) {
        String username = authentication.getName();
        log.info(username);
        mypageService.deleteMember(username);
        log.info("delete");
        return ResponseEntity.noContent().build();
    }
}
