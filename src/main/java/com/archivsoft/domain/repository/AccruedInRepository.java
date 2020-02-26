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
 * 매출 리스트를 출력하고 매출 정보를 조회하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-21
 */
public interface AccruedInRepository extends JpaRepository<StatisticEntity, String> {

    /**
     * 미수금 리스트를 승인번호에서 특정 년도로 찾는 메소드
     */
    List<StatisticEntity> findAllByApproveNumStartingWithAndSalePurchaseClassification(String yearMon, String salePur);

    /**
     * 미수금 리스트를 승인번호에서 특정 년도로 찾고 수금 여부도 찾는 메소드
     */
    List<StatisticEntity> findAllByApproveNumStartingWithAndCollectionMoneyYNNotAndSalePurchaseClassification(String yearMon, String collectionMoneyYN, String salePur);
}

