package backend.mbti.repository.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 총 댓글 수
    Long countByPostId(Long postId);

    // 게시글에 대한 댓글
    List<Comment> findByPost(Post post);
}