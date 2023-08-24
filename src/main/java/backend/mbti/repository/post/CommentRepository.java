package backend.mbti.repository.post;

import backend.mbti.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByOptionAorB(String optionAorB);
}