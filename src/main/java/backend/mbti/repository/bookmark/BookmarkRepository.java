package backend.mbti.repository.bookmark;

import backend.mbti.domain.bookmark.Bookmark;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByPostAndMember(Post post, Member member);
}


