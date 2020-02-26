package com.archivsoft.dto;

import com.archivsoft.domain.entity.UserinfoEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 파일명 : UserinfoDto
 *
 * 유저 정보 데이터 교환 객체
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserinfoDto {

    private Long userIdx;
    private String userName;
    private String userPosition;

    private String userUseYN;
    private LocalDateTime regdt;
    private LocalDateTime chgdt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userRegdt;


    public UserinfoEntity toEntity() {

        UserinfoEntity build = UserinfoEntity.builder().
                userIdx(userIdx).
                userName(userName).
                userPosition(userPosition).
                userRegdt(userRegdt).
                userUseYN(userUseYN).
                build();
        return build;
    }

    @Builder
    public UserinfoDto(Long userIdx, String userName, String userPosition, LocalDate userRegdt, String userUseYN, LocalDateTime regdt, LocalDateTime chgdt) {
        this.userIdx = userIdx;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userRegdt = userRegdt;
        this.userUseYN = userUseYN;
        this.regdt = regdt;
        this.chgdt = chgdt;
    }

}
