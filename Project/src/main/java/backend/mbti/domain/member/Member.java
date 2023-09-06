package backend.mbti.domain.member;

import backend.mbti.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // 아이디
    @Column(nullable = false, unique = true)
    private String userId;

    // 프로필 이미지
    @Column
    private String profilePicturePath;

    // 패스워드
    @JsonIgnore
    @Column
    private String password;

    // 닉네임
    @Column(nullable = false, unique = true)
    private String nickName;

    // MBTI 타입
    @Column
    private String mbti;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 생년월일
    @Column(nullable = false)
    private String birthday;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts;
}
