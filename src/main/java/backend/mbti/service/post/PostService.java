package backend.mbti.service.post;

import backend.mbti.domain.bookmark.Bookmark;
import backend.mbti.domain.dto.post.PostCreateRequest;
import backend.mbti.domain.dto.post.PostResponse;
import backend.mbti.domain.dto.post.PostUpdateRequest;
import backend.mbti.domain.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface PostService {

    // 글 내림차순 조회
    List<Post> getPostListDesc();

    // 글 등록
    Post createPost(PostCreateRequest request, String username);

    // 글 조회 시 조회 수 증가
    PostResponse getPostAndIncreaseViewCount(Long postId);

    // 글 수정
    Post updatePost(Long postId, PostUpdateRequest request, String username);

    // 글 삭제
    void deletePost(Long postId, String username);

    // 댓글 수
    Integer getCommentCount(Long postId);

    //북마크
    Bookmark toggleBookmark(Long postId, String username);
}