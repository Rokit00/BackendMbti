package com.example.demo.post.service;

import com.example.demo.post.domain.Member;
import com.example.demo.post.domain.Post;
import com.example.demo.post.repository.CommentRepository;
import com.example.demo.post.repository.MemberRepository;
import com.example.demo.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
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

    //북마크
    @Override
    public void bookmark(Long writerNum,Long memberId) {
        Post post = postRepository.findById(writerNum).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        if(postRepository.findByWriterNumAndMember(writerNum, member) == null) {
            post.setMember(member);
            post.setBookmark(true);
            postRepository.save(post);
        }
    }

    //북마크 삭제
    @Override
    public void removeBookmark(Long writerNum,Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if(postRepository.findByWriterNumAndMember(writerNum, member) != null) {
            postRepository.deleteByMemberId(member.getId());
        }
    }

    //댓글수
    @Override
    public int getCommentCount(Long writerNum) {
        return commentRepository.countByWriterNum(writerNum);
    }


    //좋아요 count
    @Override
    public void updateLikeCount(Post post, boolean b) {
        if(b) {

        }
    }


}
