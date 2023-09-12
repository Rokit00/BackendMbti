package backend.mbti.domain.mbti;

import backend.mbti.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mbti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "mbti_types", joinColumns = @JoinColumn(name = "mbti_id"))
    @Column(nullable = false)
    private List<String> mbtiTypes = new ArrayList<>();

    @Column(nullable = false)
    private String groupName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}