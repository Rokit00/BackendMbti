package backend.mbti.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentUpdateRequest {
    private String content;
    private String selectOption;
    private Date createdAt;
    private Integer likeCount;
}