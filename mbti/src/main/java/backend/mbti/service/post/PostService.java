package backend.mbti.service.post;

import backend.mbti.domain.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface PostService {

    //게시물 내림차순으로 출력
    List<Post> getPostListDesc();

    //토론 글 저장
    Post savePost(Post post);

    //토론 글 조회
    Post getPost(Long writerNum);

    //글 수정
    Post getPostById(Long writerNum, Post modifyPost);

    //글 삭제
    void deleteById(Long writerNum);

    //북마크
    Boolean bookmarkPost(Long writerNum);

    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    Map<String, Long> getCountByChoice(Long writerNum);

    //댓글수 보여주기
    int getCommentCount(Long writeNum);

    //조회수
    Post getIncrementHit(Long writetNum);

}