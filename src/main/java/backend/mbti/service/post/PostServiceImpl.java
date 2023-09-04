package backend.mbti.service.post;


import backend.mbti.domain.bookmark.Bookmark;
import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.dto.post.PostCreateRequest;
import backend.mbti.domain.dto.post.PostResponse;
import backend.mbti.domain.dto.post.PostUpdateRequest;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.bookmark.BookmarkRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

    // 글 전체 리스트 조회
    @Transactional
    @Override
    public List<Post> getPostListDesc() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return posts;
    }



    // 글 등록
    @Override
    public Post createPost(PostCreateRequest request, String username) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않는 사용자"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setOptionA(request.getOptionA());
        post.setOptionB(request.getOptionB());
        post.setCreatedAt(new Date());
        post.setMember(member);

        return postRepository.save(post);
    }


    // 글 조회 시 조회 수 증가
    @Override
    public PostResponse getPostAndIncreaseViewCount(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.increaseViewCount();
            postRepository.save(post);
            return new PostResponse(post);
        }
        return null;
    }



    // 글 수정
    @Override
    public Post updatePost(Long postId, PostUpdateRequest request, String username) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }

        if (!post.getMember().getUserId().equals(username)) {
            throw new AccessDeniedException("게시글 수정 접근 불가");
        }

        post.setTitle(request.getTitle());
        post.setOptionA(request.getOptionA());
        post.setOptionB(request.getOptionB());

        return postRepository.save(post);
    }

    // 글 삭제
    @Override
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && post.getMember().getUserId().equals(username)) {
            postRepository.delete(post);
        } else {
            throw new AccessDeniedException("게시글을 찾지 못했습니다.");
        }
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
    @Override
    public Bookmark toggleBookmark(Long postId, String username) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ""));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ""));

        if(bookmarkRepository.findByPostAndMember(post, member) == null) {
            // 좋아요를 누른적 없다면 Favorite 생성 후, 즐겨찾기 처리
            Bookmark bookmark = new Bookmark(post, member); // true 처리
            bookmarkRepository.save(bookmark);
            return bookmark;
        } else {
            // 즐겨찾기 누른적 있다면 즐겨찾기 처리 후 테이블 삭제
            Bookmark bookmark = bookmarkRepository.findByPostAndMember(post, member);
            bookmark.toggleBookmark();
            bookmarkRepository.delete(bookmark);
            return bookmark;
        }
    }
}