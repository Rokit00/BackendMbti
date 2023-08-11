package com.project.weing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.weing.Entity.DebateComment;

import java.util.List;

public interface DebateCommentRepository extends JpaRepository<DebateComment, Long> {
    List<DebateComment> findByDebateWrite_WriteNum(Long writerNum);
    List<DebateComment> findByDebateWrite_WriteNumAndOptionSelected(String writerNum, String optionSelected);
}
