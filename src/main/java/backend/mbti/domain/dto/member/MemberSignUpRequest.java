package backend.mbti.domain.dto.member;

import backend.mbti.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSignUpRequest {

    private String userId;

    private String password;

    private String nickName;

    private String birthday;

    private String email;

    public Member toEntity(String encPwd) {
        return Member.builder()
                .userId(userId)
                .password(encPwd)
                .nickName(nickName)
                .birthday(birthday)
                .email(email)
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