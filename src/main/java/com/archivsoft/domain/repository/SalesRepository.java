package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.StatisticEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * 매출계산서 목록을 출력하고 조회,등록,수정을 하기 위한 함수를 선언
 * @author 강병수
 * @version 1.0
 * @since 2020.02.03
 */
public interface SalesRepository extends JpaRepository<StatisticEntity, Long> {
    StatisticEntity findByApproveNum(String approveNum);

    Page<StatisticEntity> findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndCollectionMoneyYNAndSalePurchaseClassificationOrderByRegDtAsc(LocalDate firstRegDt, LocalDate lastRegDt, LocalDate firstIssueDt, LocalDate lastIssueDt, String companyNm, String collectionMoneyYN, String salePurchaseClassification, Pageable pageable);

    Page<StatisticEntity> findAllByRegDtBetweenAndSalePurchaseClassificationOrderByRegDtAsc(LocalDate firstRegDt, LocalDate lastRegDt, String salePurchaseClassification, Pageable pageable);

    Page<StatisticEntity> findAllByIssueDtBetweenAndSalePurchaseClassificationOrderByRegDtAsc(LocalDate firstIssueDt, LocalDate lastIssueDt, String salePurchaseClassification, Pageable pageable);

    Page<StatisticEntity> findAllByRecipientCompanyNMContainingAndSalePurchaseClassificationOrderByRegDtAsc(String companyNM, String salePurchaseClassification, Pageable pageable);

    Page<StatisticEntity> findAllByCollectionMoneyYNEqualsAndSalePurchaseClassificationOrderByRegDtAsc(String collectionMoneyYN, String salePurchaseClassification, Pageable pageable);

    Page<StatisticEntity> findAllBySalePurchaseClassificationOrderByRegDtAsc(String salePurchaseClassification, Pageable pageable);
}
