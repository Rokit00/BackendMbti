package backend.mbti.service.post;


import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.post.Post;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List;
=======
=======
import javax.persistence.EntityNotFoundException;
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
<<<<<<< HEAD
<<<<<<< HEAD
//    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;



=======
    private final CommentRepository commentRepository;


>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
    //게시물 내림차순으로 출력
=======


    // 글 내림차순 조회
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
    @Transactional
    @Override
    public List<Post> getPostListDesc() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts;
    }

    // 글 저장
    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }


    // 글 상세 조회 (댓글 포함)
    @Override
    public Post getPostWithComments(Long postId) {
        return postRepository.findByIdWithComments(postId);
    }


    // 글 수정
    @Override
    public Post updatePost(Long writerNum, Post modifyPost) {
        Post existingPost = postRepository.findById(writerNum)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물을 찾을 수 없습니다."));

        // 기존 게시물의 필드를 수정된 게시물로 업데이트
        BeanUtils.copyProperties(modifyPost, existingPost, "writeNum", "createdAt");

        // 수정된 게시물을 저장하고 반환
        return postRepository.save(existingPost);
    }

    // 글 삭제
    @Override
    public void deleteById(Long writerNum) {
        postRepository.deleteById(writerNum);
    }

<<<<<<< HEAD
<<<<<<< HEAD
//    //북마크
//    @Override
//    public void bookmark(Long writerNum,Long memberId) {
//        Post post = postRepository.findById(writerNum).orElse(null);
//        Member member = memberRepository.findById(memberId).orElse(null);
//
//        if(postRepository.findByWriterNumAndMember(writerNum, member) == null) {
//            post.setMember(member);
//            post.setBookmark(true);
//            //post.getMember().add(member);
//            postRepository.save(post);
//        }
//    }

//    //북마크 삭제
//    @Override
//    public void removeBookmark(Long writerNum,Long memberId) {
//        Member member = memberRepository.findById(memberId).orElse(null);
//
//        if(postRepository.findByWriterNumAndMember(writerNum, member) != null) {
//            postRepository.deleteByMemberId(member.getId());
//        }
//    }

    //A,B 댓글수 기준으로 나누어 데이터 보내주기
    @Override
    public int getCountByChoice(String optionAorB) {
        return commentRepository.countByOptionAorB(optionAorB);
    }

=======
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
=======
    // 댓글 수
    public Integer getCommentCount(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<Comment> comments = post.getComments();
            return comments.size();
        } else {
            return null;
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
        }
    }

    // 북마크
    public Post toggleBookmark(Long writerNum) {
        Post post = postRepository.findById(writerNum)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, "게시물을 찾을 수 없습니다."));

        // 북마크 상태 토글
        post.setBookmark(!post.getBookmark());

<<<<<<< HEAD
        Map<String, Long> counts = new HashMap<>();
        counts.put("a", commentRepository.countByPostAndOptionAorB(post, "a"));
        counts.put("b", commentRepository.countByPostAndOptionAorB(post, "b"));

        return counts;
    }


>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
    //댓글수
    @Override
    public int getCommentCount(Long writerNum) {
        Optional<Post> postOptional = postRepository.findById(writerNum);
        return postOptional.map(post -> post.getComments().size()).orElse(0);
    }

<<<<<<< HEAD


//    //좋아요 count
//    @Override
//    public int getLikeCount(Long writerNum) {
//        Optional<Post> postOptional = postRepository.findById(writerNum);
//        return postOptional.map(Post::getLikeCount).orElse(0);
//    }
//
//    @Override
//    public void likePost(Long writerNum) {
//        Optional<Post> postOptional = postRepository.findById(writerNum);
//        postOptional.ifPresent(Post::incrementLikeCount);
//    }
//
//    @Override
//    public void unlikePost(Long writerNum) {
//        Optional<Post> postOptional = postRepository.findById(writerNum);
//        postOptional.ifPresent(Post::decrementLikeCount);
//    }




}
=======
    //조회수
    @Override
    public Post getIncrementHit(Long writerNum) {
        Post post = postRepository.findById(writerNum).orElse(null);
        int currentHitCount = post.getHit();
        post.setHit(currentHitCount + 1);
=======
        // 업데이트된 게시물 저장 및 반환
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
        return postRepository.save(post);
    }


<<<<<<< HEAD
}
>>>>>>> fd206f63f270cde53a4899f04f8eefe6701d2d4c
=======
    // 조회 수
    public Post getDebateById(Long id) {
        Optional<Post> optionalDebate = postRepository.findById(id);
        if (optionalDebate.isPresent()) {
            Post debate = optionalDebate.get();
            // 조회수 증가
            debate.setView(debate.getView() + 1);
            return postRepository.save(debate); // 변경 내용 저장 및 업데이트된 엔터티 반환
        } else {
            throw new AppException(ErrorCode.POST_NOT_FOUND, id + "");
        }
    }


}
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
