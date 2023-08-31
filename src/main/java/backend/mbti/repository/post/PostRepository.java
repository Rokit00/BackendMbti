package backend.mbti.repository.post;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
<<<<<<< HEAD
<<<<<<< HEAD
    //void updateCount(Post post, boolean ture);
    //@Query(value = "SELECT p FROM Post p ORDER BY p.createAt DESC", nativeQuery = true) //error : Unknown column 'p' in 'field list'
    List<Post> findAllByOrderByCreateAtDesc();
//    void deleteByMemberId(Long memberId);
//    Post findByWriterNumAndMember(Long writerNum, Member member);


=======
    List<Post> findAllByOrderByCreateAtDesc();
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
=======
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments c WHERE p.id = :postId")
    Post findByIdWithComments(@Param("postId") Long postId);
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
}