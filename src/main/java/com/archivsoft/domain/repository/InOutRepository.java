package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.entity.UserinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 파일명 : InOutRepository
 * 매출매입 관련 jpa 메소드 처리
 *
 * @author 류민송
 * @vesrion 1.0
 * @since 2020-02-19
 */
public interface InOutRepository extends JpaRepository<StatisticEntity, Long> {
    List<StatisticEntity> findByIssueDtBetweenAndSalePurchaseClassification(LocalDate startDate, LocalDate endDate, String type);
}