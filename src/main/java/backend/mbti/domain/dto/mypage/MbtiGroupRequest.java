package backend.mbti.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MbtiGroupRequest {
    private List<String> mbtiTypes;
    private String groupName;
}
