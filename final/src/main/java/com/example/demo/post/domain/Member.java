package com.example.demo.post.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private Date birthday;

    @Column(columnDefinition = "char")
    private String gender;

//    @ManyToMany
//    @JoinColumn(name = "posts")
//    private Set<Post> posts = new HashSet<>();

//    @JoinTable(name = "member_bookmarked_posts",
//            joinColumns = @JoinColumn(name = "member_id"),
//            inverseJoinColumns = @JoinColumn(name = "post_id"))
}
