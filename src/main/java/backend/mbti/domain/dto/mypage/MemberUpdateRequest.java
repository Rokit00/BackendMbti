package backend.mbti.domain.dto.mypage;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    private String nickName;

    private String mbti;

    private String email;

    private String birthday;

    private String currentPassword;

    private String newPassword;
}