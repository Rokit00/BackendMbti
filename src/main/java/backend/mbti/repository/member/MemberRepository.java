package backend.mbti.repository.member;

import backend.mbti.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);
<<<<<<< HEAD
=======

    Member findByBirthdayAndEmail(String birthday, String email);

    Member findByUserIdAndEmail(String userId, String email);
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
}
