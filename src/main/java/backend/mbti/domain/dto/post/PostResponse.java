package backend.mbti.domain.dto.post;

import backend.mbti.domain.post.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String optionA;
    private String optionB;
    private Integer viewCount; // 조회수 필드 추가

    // 생성자, 게터, 세터 등 필요한 메서드를 추가

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.optionA = post.getOptionA();
        this.optionB = post.getOptionB();
        this.viewCount = post.getViewCount(); // 조회수를 가져옴
    }

    // 게터, 세터 등 필요한 메서드를 추가
}