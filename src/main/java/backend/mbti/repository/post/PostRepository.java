package backend.mbti.repository.post;


import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //void updateCount(Post post, boolean ture);
    //@Query(value = "SELECT p FROM Post p ORDER BY p.createAt DESC", nativeQuery = true) //error : Unknown column 'p' in 'field list'
    List<Post> findAllByOrderByCreateAtDesc();
//    void deleteByMemberId(Long memberId);
//    Post findByWriterNumAndMember(Long writerNum, Member member);


}