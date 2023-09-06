//package backend.mbti.service.debate;
//
//import backend.mbti.domain.debate.DebateComment;
//
//import java.util.List;
//
//
//public interface DebateCommentService {
//    DebateComment createComment(DebateComment debateComment);
//
//    DebateComment updateComment(Long commentId, DebateComment debateComment);
//
//    void deleteComment(Long commentId);
//
//    // 특정 토론에 대한 댓글 목록 조회
//    List<DebateComment> getCommentsForDebate(Long writerNum);
//
//    List<DebateComment> getOptionACommentsForDebate(String writerNum);
//    List<DebateComment> getOptionBCommentsForDebate(Long writerNum, String optionSelected);
//
//    int countSpecificComment(List<DebateComment> comments, String optionSelected);
//
//    double calculatePercentage(int specificCount, int totalCount);
//
//    // 좋아요 기능 추가
//    DebateComment likeComment(Long commentId);
//
//    int getLikeCount(Long commentId);
//
//}
//
