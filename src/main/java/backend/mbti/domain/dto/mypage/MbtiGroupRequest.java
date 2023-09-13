package backend.mbti.domain.dto.mypage;

import backend.mbti.domain.dto.mbti.MbtiAndMemberRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MbtiGroupRequest {
    private String groupName;
    private List<MbtiAndMemberRequest> mbtiAndMembers;
}
