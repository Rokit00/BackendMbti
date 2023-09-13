package backend.mbti.domain.dto.mbti;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MbtiAndMemberRequest {
    private String name;
    private String mbtiType;
}
