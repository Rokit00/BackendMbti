package backend.mbti.repository.like;

import backend.mbti.domain.like.PostLike;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByPostAndMember(Post post, Member member);
}
