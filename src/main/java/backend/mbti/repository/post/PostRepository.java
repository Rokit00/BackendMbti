package backend.mbti.repository.post;


import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
<<<<<<< HEAD
    //void updateCount(Post post, boolean ture);
    //@Query(value = "SELECT p FROM Post p ORDER BY p.createAt DESC", nativeQuery = true) //error : Unknown column 'p' in 'field list'
    List<Post> findAllByOrderByCreateAtDesc();
//    void deleteByMemberId(Long memberId);
//    Post findByWriterNumAndMember(Long writerNum, Member member);


=======
    List<Post> findAllByOrderByCreateAtDesc();
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
}