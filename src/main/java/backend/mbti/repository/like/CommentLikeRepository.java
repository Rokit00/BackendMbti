package backend.mbti.repository.like;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.like.CommentLike;
import backend.mbti.domain.like.PostLike;
import backend.mbti.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    // 댓글 좋아요
    CommentLike findByCommentAndMember(Comment comment, Member member);
}
