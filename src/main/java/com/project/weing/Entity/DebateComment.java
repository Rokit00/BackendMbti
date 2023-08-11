package com.project.weing.Entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class DebateComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WRITE_NUM")
    private DebateWrite debateWrite;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String comment;

    @Column(name = "OPT", columnDefinition = "CHAR") // 컬럼 이름과 타입이 일치하도록 지정
    private String optionSelected; // A or B
    
    @Column(name = "LIKES") // 컬럼 이름과 일치하도록 지정
    private Integer likeCount;

    @Column(name = "CREATE_AT") // 컬럼 이름과 일치하도록 지정
    private Date createdAt;



    // member's nickname 가져오기
    @Transient      // @Transient: 데이터베이스에 저장되지 않는 임시 필드로 정의
    public String getNickname() {
        return member != null ? member.getNickname() : null;
    }
}



/*
외래키만 사용하는 방식:
장점: 데이터 정규화를 유지하고 불필요한 중복을 피할 수 있음. DebateComment 엔티티 내에 중복된 정보가 없기 때문에 데이터 일관성이 높을 수 있음.
단점: 데이터를 조회할 때마다 필요한 정보를 가져오기 위해 추가적인 조회 또는 조인 작업이 필요. 이로 인해 성능이 약간 저하될 수 있음.

외래키와 필드 사용하는 방식:
장점: 필요한 정보를 한 번의 조회로 가져올 수 있어 성능상 이점. DebateComment 엔티티 내에 필요한 정보가 모두 들어있기 때문.
단점: 데이터 중복이 발생하여 데이터 일관성 유지에 주의가 필요. Member 엔티티의 닉네임이 변경되면 DebateComment 엔티티의 닉네임 정보도 함께 업데이트되어야 함.

 */