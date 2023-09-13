package backend.mbti.repository.mbti;

import backend.mbti.domain.mbti.MbtiGroup;
import backend.mbti.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbtiGroupRepository extends JpaRepository<MbtiGroup, Long> {
    List<MbtiGroup> findByMember(String userId);
}
