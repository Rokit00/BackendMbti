package backend.mbti.repository.debate;

import backend.mbti.domain.debate.DebateComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebateCommentRepository extends JpaRepository<DebateComment, Long> {
    List<DebateComment> findByDebateWrite_WriteNum(Long writerNum);
    List<DebateComment> findByDebateWrite_WriteNumAndOptionSelected(String writerNum, String optionSelected);
}