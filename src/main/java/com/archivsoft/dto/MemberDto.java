package com.archivsoft.dto;

import com.archivsoft.domain.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long idx;
    private String ip;
    private String grade;
    private String approveYn;
    private String useYn;
    private String email;
    private String password;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .idx(idx)
                .email(email)
                .ip(ip)
                .grade(grade)
                .useYn(useYn)
                .approveYn(approveYn)
                .password(password)
                .build()
        ;
    }

    @Builder
    public MemberDto(Long idx, String email, String password, String ip,String grade, String approveYn, String useYn){
        this.idx = idx;
        this.email = email;
        this.password = password;
        this.ip = ip;
        this.grade = grade;
        this.approveYn = approveYn;
        this.useYn = useYn;
    }
}
