package backend.mbti.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUpdateRequest {

    private String title;

    private String optionA;

    private String optionB;
}
