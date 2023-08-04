package com.example.demo.post;

import com.example.demo.post.domain.FileUpload;
import com.example.demo.post.domain.Hashtag;
import com.example.demo.post.domain.Post;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;
    private final FileUploadRepository fileUploadRepository;

    public PostServiceImpl(PostRepository postRepository,HashtagRepository hashtagRepository, FileUploadRepository fileUploadRepository) {
        this.postRepository = postRepository;
        this.hashtagRepository = hashtagRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    @Override
    public void save(Post post) { //게시글 저장
        postRepository.save(post);
    }

    @Override
    public void saveHashtag(Hashtag hashtag) { //해시태그 저장
        hashtagRepository.save(hashtag);
    }

    @Override
    public List<Post> getPostList() { //게시글 반환
        List<Post> post = postRepository.findAll();
        return post;
    }

    @Override
    public Post findPostById(Long id) { // 게시글 조회(수정)
        Post post = postRepository.findById(id).orElse(new Post());
        return post;
    }

    @Override
    public void deleteById(Long id) { //삭제
        postRepository.deleteById(id);
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();

        FileUpload uploadedFile = new FileUpload();
        uploadedFile.setFileName(fileName);
        //FileUploadRepository.save(uploadedFile);
    }

    @Override
    public boolean isUserAuthenticated(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);

        if (session != null && session.getAttribute("id") != null) {
            return true;
        }
        return false;
    }


}
