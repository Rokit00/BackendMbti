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
    private Integer hit = 0;

    //북마크
    @Column
    private boolean bookmark;


    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY) //cascade = CascadeType.ALL
    private List<Comment> comments = new ArrayList<>();






}