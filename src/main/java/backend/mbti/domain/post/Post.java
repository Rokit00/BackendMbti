package backend.mbti.domain.post;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //?
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long writerNum;

    @Column
    private String nickname;

    @Column
    private String title;

    @Column
    private String optionA;

    @Column
    private String optionB;

    @Column
    private Date createAt;

    //조회수
    @Column
    private Integer hit;

//    //좋아요
//    @Column
//    private Integer good = 0;


    // like 기능 구현 해야함

//    @Column
//    private Boolean bookmark; // true = 북마크, false = 북마크 취소

    //북마크


    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY) //cascade = CascadeType.ALL
    private List<Comment> comments = new ArrayList<>();






}