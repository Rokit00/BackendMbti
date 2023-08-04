package com.example.demo;

import com.example.demo.post.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    private HashtagRepository hashtagRepository;
    private PostRepository postRepository;
    private FileUploadRepository fileUploadRepository;

    public AppConfig(HashtagRepository hashtagRepository, PostRepository postRepository, FileUploadRepository fileUploadRepository) {
        this.hashtagRepository = hashtagRepository;
        this.postRepository = postRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    @Bean
    public PostService postService(){
        return new PostServiceImpl(postRepository, hashtagRepository, fileUploadRepository);
    }
}
