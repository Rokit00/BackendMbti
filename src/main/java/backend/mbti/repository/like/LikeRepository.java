package backend.mbti.repository.like;

import backend.mbti.domain.like.Likes;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Likes findByPostAndMember(Post post, Member member);
}
