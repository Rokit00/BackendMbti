package backend.mbti.service.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.domain.like.Likes;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.repository.comment.CommentRepository;
import backend.mbti.repository.like.LikeRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    // 댓글 보여주기
    @Override
    public List<Comment> getCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return commentRepository.findByPost(post);
    }

    // 댓글 작성
    @Override
    public Comment createComment(Long postId, CommentRequest request, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findByUserId(username).orElseThrow(() -> new EntityNotFoundException("사용자 없음."));

        Comment newComment = new Comment(request.getContent(), post, member);
        return commentRepository.save(newComment);
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

    // A댓글, B댓글 각각 계산

    // 좋아요 증가 또는 감소
    @Override
    public void toggleLike(Long postId, Long memberId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Likes existingLike = likeRepository.findByPostAndMember(post, member);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            Likes newLike = new Likes(post, member, true);
            likeRepository.save(newLike);
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepository.save(post);
    }
}