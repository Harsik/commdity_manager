/**
 * ========================================================
 *
 * @FileName : UnpaidInService.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 25.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 25.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.UnpaidInRepository;
import com.archivsoft.dto.AccruedInDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 미지급 리스트를 출력하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-25
 */
@Service
@AllArgsConstructor
public class UnpaidInService {

    /**
     * unpaidInRepository를 사용하기 위한 변수 선언
     */
    private UnpaidInRepository unpaidInRepository;

    /**
     * 검색 년도의 미지급 현황과 작년 미지급 현황 리스트를 반환하는 메소드
     *
     * @history 2020 02 25 최초 생성
     */
    @Transactional
    public List<AccruedInDto> findUnpaidInList(int year) {
        List<AccruedInDto> AccruedInDtos = new ArrayList<>();
        AccruedInDto AccruedInDto = new AccruedInDto();

        AccruedInDtos.add(findLastYearUnpaidInList(year - 1));
        AccruedInDtos.addAll(findThisYearUnpaidInList(year));

        AccruedInDto.setRecInCount(AccruedInDtos.get(0).getRecInCount() + AccruedInDtos.get(13).getRecInCount());
        AccruedInDto.setRecInSupplyPrice(AccruedInDtos.get(0).getRecInSupplyPrice() + AccruedInDtos.get(13).getRecInSupplyPrice());
        AccruedInDto.setRecInTaxAmount(AccruedInDtos.get(0).getRecInTaxAmount() + AccruedInDtos.get(13).getRecInTaxAmount());

        AccruedInDto.setAccInCount(AccruedInDtos.get(0).getAccInCount() + AccruedInDtos.get(13).getAccInCount());
        AccruedInDto.setAccInSupplyPrice(AccruedInDtos.get(0).getAccInSupplyPrice() + AccruedInDtos.get(13).getAccInSupplyPrice());
        AccruedInDto.setAccInTaxAmount(AccruedInDtos.get(0).getAccInTaxAmount() + AccruedInDtos.get(13).getAccInTaxAmount());

        AccruedInDtos.add(AccruedInDto);

        return AccruedInDtos;
    }

    /**
     * 검색 년도의 작년 미지급 현황 리스트를 반환하는 메소드
     *
     * @param year
     * @history 2020 02 25 최초 생성
     */
    @Transactional
    public AccruedInDto findLastYearUnpaidInList(int year) {
        AccruedInDto AccruedInDto = new AccruedInDto();
        int lastYearSupply = 0, lastYearTax = 0, lastYearSupplyCol = 0, lastYearTaxCol = 0;

        List<StatisticEntity> lastYearList = findUnpaidIncomeList(String.format("%s", year));
        List<StatisticEntity> lastYearListCol = findUnpaidIncomeListCollected(String.format("%s", year));

        for (StatisticEntity statisticEntity : lastYearList) {
            lastYearSupply += statisticEntity.getSupplyPrice();
            lastYearTax += statisticEntity.getTaxAmount();
        }
        for (StatisticEntity statisticEntity : lastYearListCol) {
            lastYearSupplyCol += statisticEntity.getSupplyPrice();
            lastYearTaxCol += statisticEntity.getTaxAmount();
        }

        AccruedInDto.setRecInCount(lastYearListCol.size());
        AccruedInDto.setRecInSupplyPrice(lastYearSupplyCol);
        AccruedInDto.setRecInTaxAmount(lastYearTaxCol);

        AccruedInDto.setAccInCount(lastYearList.size() - lastYearListCol.size());
        AccruedInDto.setAccInSupplyPrice(lastYearSupply - lastYearSupplyCol);
        AccruedInDto.setAccInTaxAmount(lastYearTax - lastYearTaxCol);

        return AccruedInDto;
    }

    /**
     * 검색 년도의 미지급 현황 리스트를 반환하는 메소드
     *
     * @param year
     * @history 2020 02 25 최초 생성
     */
    @Transactional
    public List<AccruedInDto> findThisYearUnpaidInList(int year) {
        AccruedInDto AccruedInDto;
        List<AccruedInDto> AccruedInDtos = new ArrayList<>();
        int thisYearSupply, thisYearTax, thisYearSupplyCol, thisYearTaxCol;
        int totalYearCount = 0, totalYearCountCol = 0, totalYearSupply = 0, totalYearTax = 0, totalYearSupplyCol = 0, totalYearTaxCol = 0;

        for (int i = 1; i < 13; i++) {
            AccruedInDto = new AccruedInDto();
            thisYearSupply = 0;
            thisYearTax = 0;
            thisYearSupplyCol = 0;
            thisYearTaxCol = 0;
            List<StatisticEntity> thisYearList = findUnpaidIncomeList(String.format("%s%02d", year, i));
            List<StatisticEntity> thisYearListCol = findUnpaidIncomeListCollected(String.format("%s%02d", year, i));

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

            AccruedInDto.setRecInCount(thisYearListCol.size());
            AccruedInDto.setRecInSupplyPrice(thisYearSupplyCol);
            AccruedInDto.setRecInTaxAmount(thisYearTaxCol);

            AccruedInDto.setAccInCount(thisYearList.size() - thisYearListCol.size());
            AccruedInDto.setAccInSupplyPrice(thisYearSupply - thisYearSupplyCol);
            AccruedInDto.setAccInTaxAmount(thisYearTax - thisYearTaxCol);

            AccruedInDtos.add(AccruedInDto);
        }

        AccruedInDto = new AccruedInDto();

        AccruedInDto.setRecInCount(totalYearCountCol);
        AccruedInDto.setRecInSupplyPrice(totalYearSupplyCol);
        AccruedInDto.setRecInTaxAmount(totalYearTaxCol);

        AccruedInDto.setAccInCount(totalYearCount);
        AccruedInDto.setAccInSupplyPrice(totalYearSupply);
        AccruedInDto.setAccInTaxAmount(totalYearTax);

        AccruedInDtos.add(AccruedInDto);

        return AccruedInDtos;
    }

    /**
     * 년도 혹은 월별 지급된 매입 리스트
     *
     * @history 2020 02 25 최초 생성
     */
    @Transactional
    public List<StatisticEntity> findUnpaidIncomeListCollected(String yearMon) {
        return unpaidInRepository.findAllByApproveNumStartingWithAndPaymentYNNotAndSalePurchaseClassification(yearMon, "", "P");
    }

    /**
     * 년도 혹은 월별 매입 리스트
     *
     * @history 2020 02 25 최초 생성
     */
    @Transactional
    public List<StatisticEntity> findUnpaidIncomeList(String yearMon) {
        return unpaidInRepository.findAllByApproveNumStartingWithAndSalePurchaseClassification(yearMon, "P");
    }
}