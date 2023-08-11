package com.project.weing.Entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class DebateWrite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WRITER_NUM")
    private Long writeNum; // 기본키

    private String nickname;
    private String title;

    @Column(name = "OPTION_A")
    private String optionA;

    @Column(name = "OPTION_B")
    private String optionB;

    @Column(name = "CREATE_AT")
    private Date createdAt;
    @Column(name = "LIKES")
    private Integer likeCount;
    private Integer hit;
    private Boolean bookmark;
}

