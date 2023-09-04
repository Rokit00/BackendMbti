package backend.mbti.domain.dto.post;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class PostCreateRequest {

    private String title;

    private String optionA;

    private String optionB;
}
