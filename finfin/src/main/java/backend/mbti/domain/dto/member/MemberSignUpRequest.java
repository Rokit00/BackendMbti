package backend.mbti.domain.dto.member;

import backend.mbti.domain.member.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberSignUpRequest {

    private String userId;

    private String password;

    private String nickname;

    private String birthday;

    private String email;

    private String oAuth;

    public Member toEntity(String encPwd) {
        return Member.builder()
                .userId(userId)
                .password(encPwd)
                .nickname(nickname)
                .birthday(birthday)
                .email(email)
                .oAuth(oAuth)
                .build();
    }
}
//{
// "userId": "test",
//"password": "1234",
//"nickName": "test_nickName",
//"birthday": "1999-01-01",
// "email": "test@test.com"
//}