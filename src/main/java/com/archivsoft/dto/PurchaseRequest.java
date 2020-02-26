/**
 * ========================================================
 *
 * @FileName : PurchaseRequest.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 매입 데이터 요청 교환 객체
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseRequest {

    /**
     * 등록 시작일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromRegDt;

    /**
     * 등록 종료일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toRegDt;

    /**
     * 발급 시작일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromIssueDt;

    /**
     * 발급 종료일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toIssueDt;

    /**
     * 상호명
     */
    private String companyNM = "";

    /**
     * 지급여부
     */
    private String paymentYN = "";

}