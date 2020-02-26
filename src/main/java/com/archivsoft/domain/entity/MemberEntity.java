package com.archivsoft.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Entity
@Table(name = "member_at")
public class MemberEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String ip;

    @Column(length = 1, nullable = false)
    private String useYn;

    @Column(length = 1, nullable = false)
    private String approveYn;

    @Column(length = 10, nullable = false)
    private String grade;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDt;

    @Builder
    public MemberEntity(Long idx, String email, String password, String ip, String useYn, String grade, String approveYn){
        this.idx = idx;
        this.email = email;
        this.password = password;
        this.ip = ip;
        this.useYn = useYn;
        this.grade = grade;
        this.approveYn = approveYn;
//        this.lastLoginDt = lastLoginDt;
    }

}
