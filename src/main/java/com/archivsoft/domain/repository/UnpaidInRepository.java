/**
 * ========================================================
 *
 * @FileName : UnpaidInRepository.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 25.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 25.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 매입 리스트를 출력하고 매입 정보를 조회하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-25
 */
public interface UnpaidInRepository extends JpaRepository<StatisticEntity, String> {

    /**
     * 미지급 리스트를 승인번호에서 특정 년도로 찾는 메소드
     */
    List<StatisticEntity> findAllByApproveNumStartingWithAndSalePurchaseClassification(String yearMon, String salePur);

    /**
     * 미지급 리스트를 승인번호에서 특정 년도로 찾고 지급 여부도 찾는 메소드
     */
    List<StatisticEntity> findAllByApproveNumStartingWithAndPaymentYNNotAndSalePurchaseClassification(String yearMon, String paymentYN, String salePur);
}

