package com.project.weing.Service;

import com.project.weing.Entity.DebateComment;
import java.util.List;


public interface DebateCommentService {

    DebateComment createComment(DebateComment debateComment);

    DebateComment updateComment(Long commentId, DebateComment debateComment);

    void deleteComment(Long commentId);

    // 특정 토론에 대한 댓글 목록 조회
    List<DebateComment> getCommentsForDebate(Long writerNum);

    List<DebateComment> getOptionACommentsForDebate(String writerNum);
    List<DebateComment> getOptionBCommentsForDebate(Long writerNum, String optionSelected);
}
