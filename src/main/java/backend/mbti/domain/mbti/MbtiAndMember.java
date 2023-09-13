package backend.mbti.domain.mbti;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter

public class MbtiAndMember {
    private String name;
    private String mbtiType;
}