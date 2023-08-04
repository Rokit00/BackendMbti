package com.example.demo.post;

import com.example.demo.post.domain.Hashtag;
import com.example.demo.post.domain.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/sec3/save")
    public ResponseEntity<Void> createPost(HttpServletRequest httpServletRequest, @RequestBody Post post, @RequestBody Hashtag hashtag) {
        postService.save(post);
        postService.saveHashtag(hashtag);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/list")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostList();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sec3/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.findPostById(id);

        if (post.getWriter_num() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post);
    }



    @DeleteMapping("/sec3/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        postService.deleteById(id);

        return ResponseEntity.ok("Post deleted successfully");
    }




//    @PostMapping(value = "/sec3/writer")
//    public List<Post> savePost(Post post){
//        List<Post> list = PostService.save(post);
//        return list;
//    }
}
