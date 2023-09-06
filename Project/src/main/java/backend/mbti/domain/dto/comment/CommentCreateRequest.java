package backend.mbti.domain.dto.comment;

import backend.mbti.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    private String content;
    private String created_at;
    private String select_option;
}