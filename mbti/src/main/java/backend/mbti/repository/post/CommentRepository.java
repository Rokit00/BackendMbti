package backend.mbti.repository.post;

import backend.mbti.domain.post.Comment;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPostAndOptionAorB(Post post, String optionAorB);
}