package backend.mbti.service.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.domain.like.CommentLike;
import backend.mbti.domain.like.PostLike;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.repository.comment.CommentRepository;
import backend.mbti.repository.like.CommentLikeRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;


    // 댓글 보여주기
    @Override
    public List<Comment> getCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return commentRepository.findByPost(post);
    }

    // 댓글 작성
    @Override
    public Comment createComment(Long postId, CommentRequest commentRequest, String username) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));

        Comment comment = new Comment(username, member.getMbti(), commentRequest.getContent(), commentRequest.getSelectOption(), post, member);
        return commentRepository.save(comment);
    }


    // 댓글 수정
    @Override
    public Comment updateComment(Long commentId, CommentUpdateRequest request, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // 댓글 작성자의 username과 현재 인증된 사용자의 username 비교
        if (!comment.getMember().getUserId().equals(username)) {
            throw new AccessDeniedException("You are not allowed to update this comment");
        }

        comment.setContent(request.getContent());
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!comment.getMember().getUserId().equals(username)) {
            throw new AccessDeniedException("You are not allowed to delete this comment");
        }

        commentRepository.delete(comment);
    }

    // 총 댓글 수
    @Override
    public Long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    // 댓글 좋아요
    @Override
    @Transactional
    public int likePost(Long commentId, String username) { // void -> int 타입으로 메소드 변경
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));

        CommentLike existingLike = commentLikeRepository.findByCommentAndMember(comment, member);

        if (existingLike == null) {
            CommentLike like = new CommentLike(comment, member);
            commentLikeRepository.save(like);

            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);
        } else {
            commentLikeRepository.delete(existingLike);

            comment.setLikeCount(comment.getLikeCount() - 1);
            commentRepository.save(comment);
        }
        return comment.getLikeCount(); // int 타입을 변경함으로 리턴값 추가
    }
}