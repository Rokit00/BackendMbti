package backend.mbti.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {
    private String userId;
    private String password;
    private String nickName;
    private String birthday;
    private String email;
}
/**
 * {
 *   "userId": "test",
 *   "password": "1234",
 *   "nickName": "test_nickName",
 *   "birthday": "1999-01-01"
 *   "email": "test@test.com",
 * }
 */