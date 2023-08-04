package com.example.demo.post;

import com.example.demo.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post post);
    List<Post> findAll();

}
