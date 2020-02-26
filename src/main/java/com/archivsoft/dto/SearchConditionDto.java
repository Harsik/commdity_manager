
package com.archivsoft.dto;

import com.archivsoft.domain.entity.ProductEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 매출계산서 목록 조회시 이용되는 검색조건을 설정하는 클래스
 * @author 강병수
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchConditionDto {

    private String companyNM;
    private String collectionMoneyYN;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstRegDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastRegDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstIssueDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastIssueDt;
}