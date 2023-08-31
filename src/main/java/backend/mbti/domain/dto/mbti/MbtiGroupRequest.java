package backend.mbti.domain.dto.mbti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MbtiGroupRequest {
    private String mbtiType;
    private String userName;
    private String groupName;
}
