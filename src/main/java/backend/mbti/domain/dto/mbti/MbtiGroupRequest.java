package backend.mbti.domain.dto.mbti;

import backend.mbti.domain.member.Member;

import java.util.List;

public class MbtiGroupRequest {
    private String groupName;
    private List<MbtiAndMemberRequest> mbtiAndMemberRequests;
}
