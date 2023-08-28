package backend.mbti.service.post;


import backend.mbti.domain.post.Post;
import backend.mbti.repository.post.CommentRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    //게시물 내림차순으로 출력
    @Transactional
    @Override
    public List<Post> getPostListDesc() {
        List<Post> posts = postRepository.findAllByOrderByCreateAtDesc();
        return posts;
    }

    //토론 글 저장
    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    //토론 글 조회
    @Override
    public Post getPost(Long writerNum) {
        return postRepository.findById(writerNum).orElse(null);
    }
    //글 수정
    @Override
    public Post getPostById(Long writerNum, Post modifyPost) {
        Post getPost = postRepository.findById(writerNum).orElse(null);
        if(getPost != null) {
            modifyPost.setWriterNum(getPost.getWriterNum());
        }
        return postRepository.save(modifyPost);
    }

    //글 삭제
    @Override
    public void deleteById(Long writerNum) {
        postRepository.deleteById(writerNum);
    }

    //북마크 저장 + 삭제
    @Override
    public Boolean bookmarkPost(Long writerNum) {
        Optional<Post> postOptional = postRepository.findById(writerNum);
        if(postOptional.isPresent()) {
            Post post = postOptional.get();
            boolean newBookmarkStatus = !post.isBookmark();
            post.setBookmark(newBookmarkStatus);
            postRepository.save(post);
            return newBookmarkStatus;
        }
        return false;
    }


    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    @Override
    public Map<String, Long> getCountByChoice(Long writerNum) {
        Post post = postRepository.findById(writerNum).orElse(null);

        Map<String, Long> counts = new HashMap<>();
        counts.put("a", commentRepository.countByPostAndOptionAorB(post, "a"));
        counts.put("b", commentRepository.countByPostAndOptionAorB(post, "b"));

        return counts;
    }


    //댓글수
    @Override
    public int getCommentCount(Long writerNum) {
        Optional<Post> postOptional = postRepository.findById(writerNum);
        return postOptional.map(post -> post.getComments().size()).orElse(0);
    }

    //조회수
    @Override
    public Post getIncrementHit(Long writerNum) {
        Post post = postRepository.findById(writerNum).orElse(null);
        int currentHitCount = post.getHit();
        post.setHit(currentHitCount + 1);
        return postRepository.save(post);
    }


}
