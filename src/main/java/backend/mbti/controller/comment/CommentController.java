package backend.mbti.controller.comment;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
// 댓글 관련
public class CommentController {

    private final CommentService commentService;

    // 특정 글 댓글 작성
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
        Comment createdComment = commentService.createComment(postId, commentCreateRequest);
        if (createdComment != null) {
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 수정 (수정해야함)
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        Comment updatedComment = commentService.updateComment(commentId, request);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 삭제 (수정해야함)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // 좋아요 수 (수정해야함)
    @GetMapping("/{commentId}/likeCount")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long commentId) {
        int likeCount = commentService.getLikeCount(commentId);
        return new ResponseEntity<>(likeCount, HttpStatus.OK);
    }

    // 리포트
}
