package backend.mbti.domain.comment;

import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
// mbti 컴럼
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String mbti;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column
    private Character selectOption;

    // 생성 시간
    @Column(name = "created_at")
    private Date createdAt;

    // Member에 mbti 가져오기
    public String getMbti() {
        if (member != null) {
            return member.getMbti();
        }
        return null;
    }


    public Comment(String userId, String mbti,String content,Character selectOption, Post post, Member member) {
        this.userId = userId;
        this.mbti = mbti;
        this.content = content;
        this.post = post;
        this.member = member;
        this.selectOption = selectOption;
        this.createdAt = new Date();
    }
}


/*
외래키만 사용하는 방식:
장점: 데이터 정규화를 유지하고 불필요한 중복을 피할 수 있음. DebateComment 엔티티 내에 중복된 정보가 없기 때문에 데이터 일관성이 높을 수 있음.
단점: 데이터를 조회할 때마다 필요한 정보를 가져오기 위해 추가적인 조회 또는 조인 작업이 필요. 이로 인해 성능이 약간 저하될 수 있음.

외래키와 필드 사용하는 방식:
장점: 필요한 정보를 한 번의 조회로 가져올 수 있어 성능상 이점. DebateComment 엔티티 내에 필요한 정보가 모두 들어있기 때문.
단점: 데이터 중복이 발생하여 데이터 일관성 유지에 주의가 필요. Member 엔티티의 닉네임이 변경되면 DebateComment 엔티티의 닉네임 정보도 함께 업데이트되어야 함.

 */
