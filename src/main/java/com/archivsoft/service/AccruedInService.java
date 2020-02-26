/**
 * ========================================================
 *
 * @FileName : AccruedInService.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 21.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 21.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.AccruedInRepository;
import com.archivsoft.dto.AccruedInDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 미수금 리스트를 출력하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-24
 */
@Service
@AllArgsConstructor
public class AccruedInService {

    /**
     * AccruedInRepository를 사용하기 위한 변수 선언
     */
    private AccruedInRepository accruedInRepository;

    /**
     * 검색 년도의 미수금 현황과 작년 미수금 현황 리스트를 반환하는 메소드
     *
     * @history 2020 02 21 최초 생성
     */
    @Transactional
    public List<AccruedInDto> findAccruedInList(int year) {
        List<AccruedInDto> accruedInDtos = new ArrayList<>();
        AccruedInDto accruedInDto = new AccruedInDto();

        accruedInDtos.add(findLastYearAccruedInList(year - 1));
        accruedInDtos.addAll(findThisYearAccruedInList(year));

        accruedInDto.setRecInCount(accruedInDtos.get(0).getRecInCount() + accruedInDtos.get(13).getRecInCount());
        accruedInDto.setRecInSupplyPrice(accruedInDtos.get(0).getRecInSupplyPrice() + accruedInDtos.get(13).getRecInSupplyPrice());
        accruedInDto.setRecInTaxAmount(accruedInDtos.get(0).getRecInTaxAmount() + accruedInDtos.get(13).getRecInTaxAmount());

        accruedInDto.setAccInCount(accruedInDtos.get(0).getAccInCount() + accruedInDtos.get(13).getAccInCount());
        accruedInDto.setAccInSupplyPrice(accruedInDtos.get(0).getAccInSupplyPrice() + accruedInDtos.get(13).getAccInSupplyPrice());
        accruedInDto.setAccInTaxAmount(accruedInDtos.get(0).getAccInTaxAmount() + accruedInDtos.get(13).getAccInTaxAmount());

        accruedInDtos.add(accruedInDto);

        return accruedInDtos;
    }

    /**
     * 검색 년도의 작년 미수금 현황 리스트를 반환하는 메소드
     *
     * @param year
     * @history 2020 02 21 최초 생성
     */
    @Transactional
    public AccruedInDto findLastYearAccruedInList(int year) {
        AccruedInDto accruedInDto = new AccruedInDto();
        int lastYearSupply = 0, lastYearTax = 0, lastYearSupplyCol = 0, lastYearTaxCol = 0;

        List<StatisticEntity> lastYearList = findAccruedIncomeList(String.format("%s", year));
        List<StatisticEntity> lastYearListCol = findAccruedIncomeListCollected(String.format("%s", year));

        for (StatisticEntity statisticEntity : lastYearList) {
            lastYearSupply += statisticEntity.getSupplyPrice();
            lastYearTax += statisticEntity.getTaxAmount();
        }
        for (StatisticEntity statisticEntity : lastYearListCol) {
            lastYearSupplyCol += statisticEntity.getSupplyPrice();
            lastYearTaxCol += statisticEntity.getTaxAmount();
        }

        accruedInDto.setRecInCount(lastYearListCol.size());
        accruedInDto.setRecInSupplyPrice(lastYearSupplyCol);
        accruedInDto.setRecInTaxAmount(lastYearTaxCol);

        accruedInDto.setAccInCount(lastYearList.size() - lastYearListCol.size());
        accruedInDto.setAccInSupplyPrice(lastYearSupply - lastYearSupplyCol);
        accruedInDto.setAccInTaxAmount(lastYearTax - lastYearTaxCol);

        return accruedInDto;
    }

    /**
     * 검색 년도의 미수금 현황 리스트를 반환하는 메소드
     *
     * @param year
     * @history 2020 02 21 최초 생성
     */
    @Transactional
    public List<AccruedInDto> findThisYearAccruedInList(int year) {
        AccruedInDto accruedInDto = new AccruedInDto();
        List<AccruedInDto> accruedInDtos = new ArrayList<>();
        int thisYearSupply, thisYearTax, thisYearSupplyCol, thisYearTaxCol;
        int totalYearCount = 0, totalYearCountCol = 0, totalYearSupply = 0, totalYearTax = 0, totalYearSupplyCol = 0, totalYearTaxCol = 0;

        for (int i = 1; i < 13; i++) {
            accruedInDto = new AccruedInDto();
            thisYearSupply = 0;
            thisYearTax = 0;
            thisYearSupplyCol = 0;
            thisYearTaxCol = 0;
            List<StatisticEntity> thisYearList = findAccruedIncomeList(String.format("%s%02d", year, i));
            List<StatisticEntity> thisYearListCol = findAccruedIncomeListCollected(String.format("%s%02d", year, i));

            for (StatisticEntity statisticEntity : thisYearList) {
                thisYearSupply += statisticEntity.getSupplyPrice();
                thisYearTax += statisticEntity.getTaxAmount();
            }
            for (StatisticEntity statisticEntity : thisYearListCol) {
                thisYearSupplyCol += statisticEntity.getSupplyPrice();
                thisYearTaxCol += statisticEntity.getTaxAmount();
            }

            totalYearCountCol += thisYearListCol.size();
            totalYearSupplyCol += thisYearSupplyCol;
            totalYearTaxCol += thisYearTaxCol;

            totalYearCount += thisYearList.size() - thisYearListCol.size();
            totalYearSupply += thisYearSupply - thisYearSupplyCol;
            totalYearTax += thisYearTax - thisYearTaxCol;

            accruedInDto.setRecInCount(thisYearListCol.size());
            accruedInDto.setRecInSupplyPrice(thisYearSupplyCol);
            accruedInDto.setRecInTaxAmount(thisYearTaxCol);

            accruedInDto.setAccInCount(thisYearList.size() - thisYearListCol.size());
            accruedInDto.setAccInSupplyPrice(thisYearSupply - thisYearSupplyCol);
            accruedInDto.setAccInTaxAmount(thisYearTax - thisYearTaxCol);

            accruedInDtos.add(accruedInDto);
        }

        accruedInDto = new AccruedInDto();

        accruedInDto.setRecInCount(totalYearCountCol);
        accruedInDto.setRecInSupplyPrice(totalYearSupplyCol);
        accruedInDto.setRecInTaxAmount(totalYearTaxCol);

        accruedInDto.setAccInCount(totalYearCount);
        accruedInDto.setAccInSupplyPrice(totalYearSupply);
        accruedInDto.setAccInTaxAmount(totalYearTax);

        accruedInDtos.add(accruedInDto);

        return accruedInDtos;
    }

    /**
     * 년도 혹은 월별 수금된 매출 리스트
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> findAccruedIncomeListCollected(String yearMon) {
        return accruedInRepository.findAllByApproveNumStartingWithAndCollectionMoneyYNNotAndSalePurchaseClassification(yearMon, "", "S");
    }

    /**
     * 년도 혹은 월별 매출 리스트
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> findAccruedIncomeList(String yearMon) {
        return accruedInRepository.findAllByApproveNumStartingWithAndSalePurchaseClassification(yearMon, "S");
    }
}