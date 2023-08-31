package backend.mbti.service.comment;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.comment.CommentCreateRequest;
import backend.mbti.domain.dto.comment.CommentUpdateRequest;
import backend.mbti.domain.post.Post;
import backend.mbti.repository.comment.CommentRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 게시글에 대한 댓글 조회
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }


    // 댓글 생성
    @Override
    public Comment createComment(Long postId, CommentCreateRequest commentCreateRequest) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            Comment comment = new Comment();
            comment.setContent(commentCreateRequest.getContent());
            comment.setSelectOption(commentCreateRequest.getSelect_option());
            comment.setCreatedAt(new Date()); // 현재 시간 설정
            comment.setPost(post);

            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    // 댓글 수정
    public Comment updateComment(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if (request.getContent() != null) {
                comment.setContent(request.getContent());
            }

            if (request.getSelectOption() != null) {
                comment.setSelectOption(request.getSelectOption());
            }

            if (request.getCreatedAt() != null) {
                comment.setCreatedAt(request.getCreatedAt());
            }

            if (request.getLikeCount() != null) {
                comment.setLikeCount(request.getLikeCount());
            }

            return commentRepository.save(comment);
        }

        return null;
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }




    // 댓글 수



    // 좋아정
    @Override
    public int getLikeCount(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다"));

        return comment.getLikeCount();
    }
}