/**
 * ========================================================
 *
 * @FileName : PurchaseRepository.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.StatisticEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 매출,매입 리스트를 출력하고 매출,매입 정보를 조회, 수정, 삭제하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
public interface PurchaseRepository extends JpaRepository<StatisticEntity, String> {

    /**
     * 모든 매출,매입 리스트를 특정 등록일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    List<StatisticEntity> findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(LocalDate fromRegDt, LocalDate toRegDt, String ceoName, String paymentYN, String salePurchaseClassification);

    /**
     * 모든 매출,매입 리스트를 특정 발급일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    List<StatisticEntity> findAllByIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByIssueDtDesc(LocalDate fromIssueDt, LocalDate toIssueDt, String ceoName, String paymentYN, String salePurchaseClassification);

    /**
     * 모든 매출,매입 리스트를 특정 등록일과 발급일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    List<StatisticEntity> findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDescIssueDtDesc(LocalDate fromRegDt, LocalDate toRegDt, LocalDate fromIssueDt, LocalDate toIssueDt, String ceoName, String paymentYN, String salePurchaseClassification);

    /**
     * 모든 매출,매입 리스트를 특정 등록일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    Page<StatisticEntity> findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(LocalDate fromRegDt, LocalDate toRegDt, String ceoName, String paymentYN, String salePurchaseClassification, Pageable pageable);

    /**
     * 모든 매출,매입 리스트를 특정 발급일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    Page<StatisticEntity> findAllByIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByIssueDtDesc(LocalDate fromIssueDt, LocalDate toIssueDt, String ceoName, String paymentYN, String salePurchaseClassification, Pageable pageable);

    /**
     * 모든 매출,매입 리스트를 특정 등록일과 발급일 사이로 상호명과 수금여부를 조건으로 등록일자 순으로 찾는 메소드
     */
    Page<StatisticEntity> findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDescIssueDtDesc(LocalDate fromRegDt, LocalDate toRegDt, LocalDate fromIssueDt, LocalDate toIssueDt, String ceoName, String paymentYN, String salePurchaseClassification, Pageable pageable);

}

