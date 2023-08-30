package backend.mbti.service.debate;

import backend.mbti.domain.debate.DebateComment;

import java.util.List;


public interface DebateCommentService {
<<<<<<< HEAD

=======
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
    DebateComment createComment(DebateComment debateComment);

    DebateComment updateComment(Long commentId, DebateComment debateComment);

    void deleteComment(Long commentId);

    // 특정 토론에 대한 댓글 목록 조회
    List<DebateComment> getCommentsForDebate(Long writerNum);

    List<DebateComment> getOptionACommentsForDebate(String writerNum);
    List<DebateComment> getOptionBCommentsForDebate(Long writerNum, String optionSelected);

    int countSpecificComment(List<DebateComment> comments, String optionSelected);

    double calculatePercentage(int specificCount, int totalCount);

    // 좋아요 기능 추가
    DebateComment likeComment(Long commentId);

    int getLikeCount(Long commentId);
<<<<<<< HEAD

}

=======
}
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
