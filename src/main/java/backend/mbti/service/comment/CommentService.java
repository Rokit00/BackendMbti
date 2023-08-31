package backend.mbti.service.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;

import java.util.List;


public interface CommentService {

    // 게시글에 대한 댓글 조회
    List<Comment> getCommentsByPostId(Long postId);

    // 댓글 생성
    Comment createComment(Long postId, CommentCreateRequest commentCreateRequest);

    // 댓글 수정
    Comment updateComment(Long commentId, CommentUpdateRequest request);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 댓글 수


    // 좋아요
    int getLikeCount(Long commentId);
}
