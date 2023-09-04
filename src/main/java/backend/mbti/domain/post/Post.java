package backend.mbti.domain.post;

import backend.mbti.domain.comment.Comment;
import backend.mbti.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column
    private String title;

    // A 옵션
    @Column(name = "option_a")
    private String optionA;

    // B 옵션
    @Column(name = "option_b")
    private String optionB;

    // 작성 일자
    @Column(name = "created_at")
    private Date createdAt;

    // 좋아요 수
    @Column(name = "like_count")
    private Integer likeCount = 0;

    // 조회 수
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    // 맴버 테이블 다대일
    @JsonIgnore
    @ManyToOne
    private Member member;

    // 게시물 내림차순에 댓글 수 보여주는 속성, 게시물에 관한 댓글
    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<Comment>();

    public void increaseViewCount() {
        this.viewCount++;
    }
}
