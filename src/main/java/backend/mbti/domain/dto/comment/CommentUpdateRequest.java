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
/**
 * {
 *     "content": "테스트 댓글 수정 입니다.",
 *     "select_option": "A",
 *     "created_at": "2000-00-00",
 *     "like_count": "1"
 * }
 */