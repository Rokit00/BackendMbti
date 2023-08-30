package backend.mbti.repository.post;

import backend.mbti.domain.post.Comment;
<<<<<<< HEAD
=======
import backend.mbti.domain.post.Post;
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
<<<<<<< HEAD
    int countByOptionAorB(String optionAorB);
=======
    long countByPostAndOptionAorB(Post post, String optionAorB);
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
}