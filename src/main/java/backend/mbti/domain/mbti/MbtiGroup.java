package backend.mbti.domain.mbti;

import backend.mbti.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MbtiGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @JsonIgnore
    private String member;

    @ElementCollection
    @CollectionTable(name = "mbti_and_member", joinColumns = @JoinColumn(name = "group_id"))
    private List<MbtiAndMember> mbtiAndMembers = new ArrayList<>();
}
