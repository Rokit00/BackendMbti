package com.project.weing.Service;

import com.project.weing.Repository.DebateCommentRepository;
import com.project.weing.Entity.DebateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DebateCommentServiceImpl implements DebateCommentService {
    private DebateCommentRepository debateCommentRepository;

    @Autowired
    public DebateCommentServiceImpl(DebateCommentRepository debateCommentRepository) {
        this.debateCommentRepository = debateCommentRepository;
    }

    // 댓글 생성
    @Override
    public DebateComment createComment(DebateComment debateComment) {
        return debateCommentRepository.save(debateComment);
    }

    // 댓글 수정
    @Override
    public DebateComment updateComment(Long commentId, DebateComment updatedDebateComment) {
        DebateComment debateComment = debateCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다"));

        // 수정하려는 댓글의 필드를 업데이트
        debateComment.getMember().setUserId(updatedDebateComment.getMember().getUserId());
        debateComment.getMember().setNickname(updatedDebateComment.getMember().getNickname());
        debateComment.setOptionSelected(updatedDebateComment.getOptionSelected());
        debateComment.setComment(updatedDebateComment.getComment()); // 코멘트 내용 업데이트 추가
        debateComment.setLikeCount(updatedDebateComment.getLikeCount());
        debateComment.setCreatedAt(updatedDebateComment.getCreatedAt());

        // 업데이트된 댓글 저장 후 반환
        return debateCommentRepository.save(debateComment);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        debateCommentRepository.deleteById(commentId);
    }


    // 특정 토론에 대한 댓글 목록 조회
    @Override
    public List<DebateComment> getCommentsForDebate(Long writerNum) {
        return debateCommentRepository.findByDebateWrite_WriteNum(writerNum);
    }

    @Override
    public List<DebateComment> getOptionACommentsForDebate(String writerNum) {
        return debateCommentRepository.findByDebateWrite_WriteNumAndOptionSelected(writerNum, "A");
    }

    @Override
    public List<DebateComment> getOptionBCommentsForDebate(Long writerNum, String optionSelected) {
        return debateCommentRepository.findByDebateWrite_WriteNumAndOptionSelected(writerNum.toString(), optionSelected);
    }

}

