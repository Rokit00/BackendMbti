package backend.mbti.controller.post;


import backend.mbti.domain.post.Post;
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
@RequestMapping("/sec3")
public class PostController {

    private final PostService postService;

    //게시물 내림차순으로 출력 (x - 직렬화 오류 : 해결)
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostListDesc();
        return ResponseEntity.ok(posts);
    }

    //토론 글 저장
    @PostMapping("/writer")
    public ResponseEntity<Post> writer(@RequestBody Post post) {
        Post createPost = postService.savePost(post);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    //토론 글 조회
    @GetMapping("/{writerNum}")
    public ResponseEntity<Post> getPost(@PathVariable Long writerNum) {
        Post post = postService.getPost(writerNum);
        return ResponseEntity.ok(post);
    }

    //글 수정
    @PutMapping("/{writerNum}")
    public ResponseEntity<Post> modifyPost(@PathVariable Long writerNum, @RequestBody Post post) {
        Post updatePost = postService.getPostById(writerNum, post);
        return ResponseEntity.ok(updatePost);
    }

    //글 삭제
    @DeleteMapping("/{writerNum}") ///writerNum
    public void deletePost(@PathVariable Long writerNum) {
        postService.deleteById(writerNum);
    }

    //북마크
    @PostMapping("/{writerNum}/bookmark")
    public ResponseEntity<String> bookmarkPost(@PathVariable Long writerNum) {
        boolean bookmarked = postService.bookmarkPost(writerNum);
        if(bookmarked) {
            return ResponseEntity.ok("현재 북마크 상태 저장 완료");
        }else return ResponseEntity.badRequest().body("북마크 실패");
    }

    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    @GetMapping("/3/commentAorB/{writerNum}")
    public ResponseEntity<Map<String, Long>> getCountByChoice(@PathVariable Long writerNum) {
        Map<String, Long> counts = postService.getCountByChoice(writerNum);
        return ResponseEntity.ok(counts);
    }

    // 댓글 수
    @GetMapping("/3/comment/{writerNum}")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long writerNum) {
        int commentCount = postService.getCommentCount(writerNum);
        return ResponseEntity.ok(commentCount);
    }

    //조회수
    @GetMapping("/3/hitCount/{writerNum}")
    public ResponseEntity<Post> getHitCount(@PathVariable Long writerNum) {
        Post post = postService.getIncrementHit(writerNum);
        return ResponseEntity.ok(post);
    }


}