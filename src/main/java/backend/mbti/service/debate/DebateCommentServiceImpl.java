package backend.mbti.service.debate;

import backend.mbti.domain.debate.DebateComment;
import backend.mbti.repository.debate.DebateCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DebateCommentServiceImpl implements DebateCommentService {
    private final DebateCommentRepository debateCommentRepository;

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
        debateComment.getMember().setNickName(updatedDebateComment.getMember().getNickName());
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

    // 특정 댓글 수 세기
    @Override
    public int countSpecificComment(List<DebateComment> comments, String optionSelected) {
        long count = comments.stream()
                .filter(comment -> optionSelected.equals(comment.getOptionSelected()))
                .count();

        return (int) count;
    }

    // 백분율 계산
    @Override
    public double calculatePercentage(int specificCount, int totalCount) {
        if (totalCount == 0) {
            return 0.0; // 0으로 나누지 않기
        }
        return (specificCount * 100.0) / totalCount;
    }

}