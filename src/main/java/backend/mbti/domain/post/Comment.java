package backend.mbti.domain.post;

import backend.mbti.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_num")
    private Post post;


    @ManyToOne //EAGER
    @JoinColumn(name = "")
    private Member member;

    @Column(columnDefinition = "char")
    private String optionAorB; //option 예약어오류

    @Column
    private Integer good;

    @Column
    private Date createAt;








}