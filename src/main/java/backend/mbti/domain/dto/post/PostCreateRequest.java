package backend.mbti.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String optionA;
    private String optionB;
    private Date createdAt;
}
