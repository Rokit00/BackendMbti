package backend.mbti.repository.post;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.like.PostLike;
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
    List<Post> findAllByOrderByIdDesc();

    // 맴버가 쓴 게시글 찾기
    List<Post> findByMemberOrderByCreatedAtDesc(Member member);
}