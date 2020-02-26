package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 업체별 미수,미지급 현황 목록을 출력하고 조회하기 위한 함수를 선언
 * @author 강병수
 * @version 1.0
 * @since 2020.02.20
 */
public interface CompanyInRepository extends JpaRepository<StatisticEntity, Long> {
//      List<StatisticEntity> findCompanyInCollectionMoney(String year);
//      List<StatisticEntity> findCompanyInPaymentMoney(String year);
}
