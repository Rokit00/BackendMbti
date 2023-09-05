package backend.mbti.controller.post;



import backend.mbti.domain.dto.post.PostCreateRequest;
import backend.mbti.domain.dto.post.PostResponse;
import backend.mbti.domain.dto.post.PostUpdateRequest;
import backend.mbti.domain.post.Post;

import backend.mbti.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 글 리스트 조회 (테스트 완료)
    @GetMapping("/lists")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostListDesc();
        return ResponseEntity.ok(posts);
    }

    // 글 조회 시 조회 수 증가 (테스트 완료)
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostAndIncreaseViewCount(postId);
        if (postResponse != null) {
            return ResponseEntity.ok(postResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글 등록 (테스트 완료)
    @PostMapping("/writer")
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequest request, Authentication authentication) {
        String username = authentication.getName(); // 현재 인증된 사용자의 username 가져오기
        Post createdPost = postService.createPost(request, username);
        return ResponseEntity.ok(createdPost);
    }

    // 글 수정 (테스트 완료)
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Post updatedPost = postService.updatePost(postId, request, username);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 글 삭제 (테스트 완료)
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication authentication) {
        String username = authentication.getName();
        postService.deletePost(postId, username);
        return ResponseEntity.noContent().build();
    }

    // 글 좋아요
}