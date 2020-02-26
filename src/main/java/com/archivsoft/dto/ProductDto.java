package com.archivsoft.dto;

import com.archivsoft.domain.entity.ProductEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 상품정보 조회, 등록, 수정, 삭제시 사용되는 데이터를 설정하는 클래스
 * @author 강병수
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {

    private Long productIdx;
    private String productCompany;
    private String productItem;
    private String productModelName;
    private String productSerial;
    private String productManagerNum;
    private String productUseYN;
    private String productStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productBuyDt;

    public ProductEntity toEntity() {
        ProductEntity build = ProductEntity.builder()
                .productIdx(productIdx)
                .productCompany(productCompany)
                .productItem(productItem)
                .productModelName(productModelName)
                .productSerial(productSerial)
                .productManagerNum(productManagerNum)
                .productBuyDt(productBuyDt)
                .productUseYN(productUseYN)
                .productStatus(productStatus)
                .build();
        return build;
    }

    @Builder
    public ProductDto(Long productIdx, String productCompany, String productItem, String productModelName, String productSerial, String productManagerNum, LocalDate productBuyDt, String productUseYN, String productStatus) {

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