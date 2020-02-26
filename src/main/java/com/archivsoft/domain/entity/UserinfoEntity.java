package com.archivsoft.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;

/**
 * 파일명 : UserinfoEntity
 *
 * Userinfo 테이블을 매핑한 엔티티
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "userinfo")
public class UserinfoEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(length = 15, nullable = false)
    private String userName;

    @Column(length = 20)
    private String userPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    private LocalDate userRegdt;

    @Column(length = 1)
    private String userUseYN;

    @Builder
    public UserinfoEntity(Long userIdx, String userName, String userPosition, LocalDate userRegdt, String userUseYN) {
        this.userIdx = userIdx;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userRegdt = userRegdt;
        this.userUseYN = userUseYN;
    }

}
