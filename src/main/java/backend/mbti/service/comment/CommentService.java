package backend.mbti.service.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;

import java.util.List;


public interface CommentService {

    // 댓글 보여주기
    List<Comment> getCommentsForPost(Long postId);

    // 댓글 작성
    Comment createComment(Long postId, CommentRequest request, String username);

    // 댓글 수정
    Comment updateComment(Long commentId, CommentUpdateRequest request, String username);

    // 댓글 삭제
    void deleteComment(Long commentId, String username);

    // 총 댓글 수
    Long getCommentCount(Long postId);

    // A댓글, B댓글 각각 계산

    // 좋아요 증가 또는 감소
    void toggleLike(Long postId, Long memberId);
}
