package backend.mbti.service.post;

import backend.mbti.domain.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c


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

<<<<<<< HEAD
//    //북마크 기능 (사용자 - 게시글간의 테이블 생성)
//    void bookmark(Long writerNum, Long memberId);
//    void removeBookmark(Long writerNum, Long memberId);

    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    int getCountByChoice(String optionAorB);
=======
    //북마크
    Boolean bookmarkPost(Long writerNum);

    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    Map<String, Long> getCountByChoice(Long writerNum);
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c

    //댓글수 보여주기
    int getCommentCount(Long writeNum);

<<<<<<< HEAD
//    //좋아요수 보여주기
//    int getLikeCount(Long writerNum);
//
//    void likePost(Long writerNum);
//
//    void unlikePost(Long writerNum);
=======
    //조회수
    Post getIncrementHit(Long writetNum);

>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
}