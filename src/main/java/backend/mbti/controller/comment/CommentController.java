package backend.mbti.controller.comment;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentRequest;
import backend.mbti.domain.dto.comment.CommentTotalCountResponse;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.service.comment.CommentService;
import backend.mbti.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    // 댓글 보여주기 (유저 정보 필요함)
    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성 (테스트 완료)
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId,
                                                 @RequestBody CommentRequest commentRequest,
                                                 Authentication authentication) {
        String username = authentication.getName();
        Comment comment = commentService.createComment(postId, commentRequest, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // 댓글 수정 (테스트 완료)
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Comment updatedComment = commentService.updateComment(commentId, request, username);

        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 삭제 (테스트 완료)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }


    // 총 댓글 수 (테스트 완료)
    @GetMapping("/{postId}/count")
    public ResponseEntity<CommentTotalCountResponse> getCommentTotalCount(@PathVariable Long postId) {
        Long commentCount = commentService.getCommentCount(postId);
        CommentTotalCountResponse response = new CommentTotalCountResponse();
        response.setCount(commentCount);

        return ResponseEntity.ok(response);
    }

    // 댓글 좋아요
}
