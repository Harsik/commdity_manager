package com.archivsoft.domain.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Product 테이블과 매핑되는 Entity
 * @author 강병수
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class ProductEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productIdx;

    @Column(length = 30, nullable = false)
    private String productCompany;

    @Column(length = 30, nullable = false)
    private String productItem;

    @Column(length = 50, nullable = false)
    private String productModelName;

    @Column(length = 50, nullable = false)
    private String productSerial;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "TEXT", nullable = false)
    private LocalDate productBuyDt;

    @Column(length = 100, nullable = true)
    private String productManagerNum;

    @Column(length = 1, nullable = true)
    private String productUseYN;

    @Column(length = 20, nullable = true)
    private String productStatus;

    @Builder
    public ProductEntity(Long productIdx, String productCompany, String productItem, String productModelName, String productSerial, String productManagerNum, LocalDate productBuyDt, String productUseYN, String productStatus) {
        this.productIdx = productIdx;
        this.productCompany = productCompany;
        this.productItem = productItem;
        this.productModelName = productModelName;
        this.productSerial = productSerial;
        this.productManagerNum = productManagerNum;
        this.productBuyDt = productBuyDt;
        this.productUseYN = productUseYN;
        this.productStatus = productStatus;
    }
}