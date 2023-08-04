package com.example.demo.post;
import com.example.demo.post.domain.Hashtag;
import com.example.demo.post.domain.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


public interface PostService {

    //게시글 저장
    void save(Post post);

    void saveHashtag(Hashtag hashtag);

    //게시글 불러오기
    List<Post> getPostList();



    public Post findPostById(Long id);

    void deleteById(Long id);

    //파일 업로드
    public void uploadFile(MultipartFile file) throws IOException;

    public boolean isUserAuthenticated (HttpServletRequest httpServletRequest);


}
