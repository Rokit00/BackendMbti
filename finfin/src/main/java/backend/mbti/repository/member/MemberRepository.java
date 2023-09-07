package backend.mbti.repository.member;

import backend.mbti.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Member findByBirthdayAndEmail(String birthday, String email);

    Member findByUserIdAndEmail(String userId, String email);

    boolean existsUsersById(String userId);

}
