package backend.mbti.repository.mbti;

import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    List<Mbti> findByMember(Member member);
}
