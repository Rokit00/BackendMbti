package com.example.demo.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Post {

    public Post() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writer_num;

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
    //@CreateDate로 수정? timestamp


//    @Column
//    private Integer hit;
//
//    @Column //like 예약어 에러
//    private Integer liky;
//
//    @Column
//    private boolean bookmark;
}
