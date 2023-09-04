package backend.mbti.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberUpdateRequest {
    private String nickName;
    private String mbti;
    private String email;
    private String birthday;
    private String currentPassword;
    private String newPassword;

}