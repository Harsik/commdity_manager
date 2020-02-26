/**
 * ========================================================
 *
 * @FileName : AccruedInDto.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 21.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 21.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.dto;

import com.archivsoft.domain.entity.StatisticEntity;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;

/**
 * 월별 미수금 교환 객체
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccruedInDto {
//  ReceivedIncome  - recIn
//  AccruedIncome  - accIn

    /**
     * 기수금 건수
     */
    private int recInCount;

    /**
     * 기수금 공급가액
     */
    private int recInSupplyPrice;

    /**
     * 미수금 세액
     */
    private int recInTaxAmount;

    /**
     * 미수금 건수
     */
    private int accInCount;

    /**
     * 미수금 공급가액
     */
    private int accInSupplyPrice;

    /**
     * 미수금 세액
     */
    private int accInTaxAmount;

}