package backend.mbti.domain.member;

import backend.mbti.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String birthday;

    //카카오 로그인시 회원 비밀번호 수정금지
    @Column
    private String oAuth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts;
}
