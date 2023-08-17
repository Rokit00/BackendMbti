package com.example.demo.post.domain;


import javax.persistence.*;
import lombok.*;

import java.util.Date;
// 댓글 count 보내기
// 좋아요(good) count 보내기
// optionA: 빌려준다
// optionB: 안빌려준다
// title: 이성친구가 립밤을 빌려달라고 한다.
// A: 40% hint = A, B 댓글 수 종합해서 연산 %는 프론트에서 작업
// B: 60%

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writerNum;

    @Column
    private String nickname;

    @Column
    private String title;

    @Column
    private String optionA;

    @Column
    private String optionB;

    @Column
    private Date createAt;

    //조회수
    @Column
    private Integer hit;

    //좋아요
    @Column
    private Integer good;

    @Column
    private Boolean bookmark; // true = 북마크, false = 북마크 취소

    //북마크
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;



}
