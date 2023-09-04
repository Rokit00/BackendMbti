package backend.mbti.controller.post;


import backend.mbti.domain.dto.post.LikeRequest;
import backend.mbti.domain.post.Post;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
// 글 작성 관런
public class PostController {

    private final PostService postService;

    // 글 리스트 조회
    @GetMapping("/lists")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostListDesc();
        return ResponseEntity.ok(posts);
    }

    // 글 상세 조회 (댓글 포함)
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostWithComments(@PathVariable Long postId) {
        Post postWithComments = postService.getPostWithComments(postId);
        if (postWithComments != null) {
            return ResponseEntity.ok(postWithComments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글 등록 (수정해야함 - 로그인 시 사용자 식별 글)
    @PostMapping
    public ResponseEntity<Post> writer(@RequestBody Post post) {
        Post createPost = postService.savePost(post);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    // 글 수정 (수정해야함 - 로그인 시 사용자 식별 글)
    @PutMapping("/{id}")
    public ResponseEntity<Post> modifyPost(@PathVariable Long id, @RequestBody Post post) {
        Post updatePost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatePost);
    }

    // 글 삭제 (수정해야함 - 로그인 시 사용자 식별 글)
    @DeleteMapping("/{id}") ///writerNum
    public void deletePost(@PathVariable Long id) {
        postService.deleteById(id);
    }

    // 북마크
    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Post> toggleBookmark(@PathVariable Long id) {
        Post updatedPost = postService.toggleBookmark(id);
        return ResponseEntity.ok(updatedPost);
    }

    // 조회 수
    @GetMapping("/{id}/view")
    public ResponseEntity<Post> getDebateById(@PathVariable Long id) {
        Post debate = postService.getDebateById(id);
        return ResponseEntity.ok(debate);
    }
}