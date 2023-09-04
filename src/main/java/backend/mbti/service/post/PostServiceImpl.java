package backend.mbti.service.post;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.post.Post;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.comment.CommentRepository;
import backend.mbti.repository.like.LikeRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    // 글 전체 리스트 조회
    @Transactional
    @Override
    public List<Post> getPostListDesc() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return posts;
    }



    // 글 저장
    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }


    // 글 상세 조회 (댓글 포함)
    @Override
    public Post getPostWithComments(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<Comment> comments = commentRepository.findByPost(post);
            post.setComments(comments);
            return post;
        }
        return null;
    }



    // 글 수정
    @Override
    public Post updatePost(Long writerNum, Post modifyPost) {
        Post existingPost = postRepository.findById(writerNum)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물을 찾을 수 없습니다."));

        // 기존 게시물의 필드를 수정된 게시물로 업데이트
        BeanUtils.copyProperties(modifyPost, existingPost, "writeNum", "createdAt");

        // 수정된 게시물을 저장하고 반환
        return postRepository.save(existingPost);
    }

    // 글 삭제
    @Override
    public void deleteById(Long writerNum) {
        postRepository.deleteById(writerNum);
    }



    // 댓글 수
    public Integer getCommentCount(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<Comment> comments = post.getComments();
            return comments.size();
        } else {
            return null;
        }
    }

    // 북마크
    public Post toggleBookmark(Long writerNum) {
        Post post = postRepository.findById(writerNum)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물을 찾을 수 없습니다."));

        // 북마크 상태 토글
        post.setBookmark(!post.getBookmark());

        // 업데이트된 게시물 저장 및 반환
        return postRepository.save(post);
    }


    // 조회 수
    public Post getDebateById(Long id) {
        Optional<Post> optionalDebate = postRepository.findById(id);
        if (optionalDebate.isPresent()) {
            Post debate = optionalDebate.get();
            // 조회수 증가
            debate.setView(debate.getView() + 1);
            return postRepository.save(debate); // 변경 내용 저장 및 업데이트된 엔터티 반환
        } else {
            throw new AppException(ErrorCode.POST_NOT_FOUND, id + "");
        }
    }


}