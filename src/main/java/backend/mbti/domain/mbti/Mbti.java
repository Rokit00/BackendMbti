package backend.mbti.domain.mbti;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MBTI")
public class Mbti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mbtiType;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String groupName;
}
