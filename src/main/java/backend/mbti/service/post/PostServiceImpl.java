package backend.mbti.service.post;


import backend.mbti.domain.post.Post;
import backend.mbti.repository.post.CommentRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
//    private final MemberRepository memberRepository;
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

    //댓글수
    @Override
    public int getCommentCount(Long writerNum) {
        Optional<Post> postOptional = postRepository.findById(writerNum);
        return postOptional.map(post -> post.getComments().size()).orElse(0);
    }



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
