package backend.mbti.service.post;

import backend.mbti.domain.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface PostService {

    // 글 내림차순 조회
    List<Post> getPostListDesc();

    // 글 저장
    Post savePost(Post post);

    // 글 조회 시 댓글 조회
    Post getPostWithComments(Long postId);

    // 글 수정
    Post updatePost(Long writerNum, Post modifyPost);

    // 글 삭제
    void deleteById(Long writerNum);

    // 댓글 수
    Integer getCommentCount(Long postId);

    //북마크
    Post toggleBookmark(Long writerNum);

    //조회수
    Post getDebateById(Long id);

}