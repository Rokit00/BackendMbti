package backend.mbti.controller.post;


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

    // 글 내림차순 조회
    @GetMapping
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

    // 글 등록
    @PostMapping
    public ResponseEntity<Post> writer(@RequestBody Post post) {
        Post createPost = postService.savePost(post);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Post> modifyPost(@PathVariable Long id, @RequestBody Post post) {
        Post updatePost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatePost);
    }

    // 글 삭제
    @DeleteMapping("/{id}") ///writerNum
    public void deletePost(@PathVariable Long id) {
        postService.deleteById(id);
    }

    // 댓글 수
    @GetMapping("/{postId}/comment-count")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long postId) {
        Integer commentCount = postService.getCommentCount(postId);

        if (commentCount != null) {
            return ResponseEntity.ok(commentCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 북마크
    @PostMapping("/{id}/bookmark")
    public ResponseEntity<Post> toggleBookmark(@PathVariable Long id) {
        Post updatedPost = postService.toggleBookmark(id);
        return ResponseEntity.ok(updatedPost);
    }
<<<<<<< HEAD
=======


    // 조회 수
    @GetMapping("/{id}/view")
    public ResponseEntity<Post> getDebateById(@PathVariable Long id) {
        Post debate = postService.getDebateById(id);
        return ResponseEntity.ok(debate);
    }

>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
}