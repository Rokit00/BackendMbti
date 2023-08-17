package com.example.demo.post.controller;

import com.example.demo.post.domain.Post;
import com.example.demo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sec3")
public class PostController {

    private final PostService postService;

    //게시물 내림차순으로 출력
    @GetMapping("/3")
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
    @GetMapping
    public ResponseEntity<Post> getPost(@RequestParam Long writernum) {
        Post post = postService.getPost(writernum);
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
    @PostMapping("/3/bookmark/{writerNum}")
    public ResponseEntity<String> bookmark(@PathVariable Long writerNum, @RequestParam(required = false) Long memberId) {
        postService.bookmark(writerNum,memberId);
        return ResponseEntity.ok("북마크 완료");
    }

    //북마크 삭제
    @DeleteMapping("/3/bookmark/{writerNum}")
    public void deleteBookmark(@PathVariable Long writerNum, @RequestParam(required = false) Long memberId) { //The given id must not be null
        postService.removeBookmark(writerNum, memberId);
    }

    //댓글수
    @GetMapping("/3/{writerNum}")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long writerNum) {
        int commentCount = postService.getCommentCount(writerNum);
        return ResponseEntity.ok(commentCount);
    }


}
