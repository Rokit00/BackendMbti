package backend.mbti.controller.comment;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentRequest;
import backend.mbti.domain.dto.comment.CommentTotalCountResponse;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.service.comment.CommentService;
import backend.mbti.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    // 댓글 보여주기 (테스트 X)
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성 (테스트 X)
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody CommentRequest request, Authentication authentication) {
        String username = authentication.getName();
        Comment createdComment = commentService.createComment(postId, request, username);

        if (createdComment != null) {
            return ResponseEntity.ok(createdComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 수정 (테스트 X)
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request, Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 username 가져오기
        Comment updatedComment = commentService.updateComment(commentId, request, username);

        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 삭제 (테스트 X)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 username 가져오기
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }

    // 총 댓글 수 (테스트 X)
    @GetMapping("/{postId}/comment-count")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long postId) {
        Integer commentCount = postService.getCommentCount(postId);

        if (commentCount != null) {
            return ResponseEntity.ok(commentCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 총 댓글 수 (테스트 X)
    @GetMapping("/count/{postId}")
    public ResponseEntity<CommentTotalCountResponse> getCommentTotalCount(@PathVariable Long postId) {
        Long commentCount = commentService.getCommentCount(postId);
        CommentTotalCountResponse response = new CommentTotalCountResponse();
        response.setCount(commentCount);

        return ResponseEntity.ok(response);
    }

    // A댓글, B댓글 각각 계산 (구현해야함)

    // 좋아요 증가 또는 감소 (테스트 X)
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(@RequestParam Long postId, @RequestParam Long memberId) {
        commentService.toggleLike(postId, memberId);
        return ResponseEntity.ok("Like toggled successfully");
    }

    // 리포트
}
