package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.InOutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 서비스명 : InOutService
 * 매출매입 데이터 처리 메소드를 선언한 서비스
 *
 * @author 류민송
 * @vesrion 1.0
 * @since 2020-02-19
 */
@Service
@AllArgsConstructor
public class InOutService {

    /**
     * inOutRepository 사용을 위한 선언
     */
    private InOutRepository inOutRepository;

    /**
     * 등록되어있는 발행일의 년도를 가져오는 메소드
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<String> findYearList() {
        List<StatisticEntity> inOutList = inOutRepository.findAll();
        List<String> tempYearList = new ArrayList<>();
        List<String> yearList = new ArrayList<>();
        String issueDt;

        for (int i = 0; i < inOutList.size(); i++) {
            issueDt = inOutList.get(i).getIssueDt().toString();
            tempYearList.add(issueDt.substring(0, 4));
        }
        for (String str : tempYearList) {
            if (!yearList.contains(str)) {
                yearList.add(str);
            }
        }

        return yearList;
    }

    /**
     * 매출매입 리스트를 가져오는 메소드
     *
     * @param year - 데이터를 받아올 년도
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> findInOutList(String year) {

        List<StatisticEntity> inOutList = new ArrayList<>();

        String[] dateArr = {"31", "28", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31"};
        String[] monthArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        for (int i = 1; i < 13; i++) {

            StatisticEntity statisticEntity = new StatisticEntity();

            int salesSupplyPrice = 0;
            int salesTaxAmount = 0;
            int salesTotalPrice = 0;
            int purchasesSupplyPrice = 0;
            int purchasesTaxAmount = 0;
            int purchasesTotalPrice = 0;

            String date = dateArr[i - 1];
            String month = monthArr[i - 1];

            String startDate = year + "-" + month + "-01";
            String endDate = year + "-" + month + "-" + date;

            List<StatisticEntity> salesList = inOutRepository.findByIssueDtBetweenAndSalePurchaseClassification(LocalDate.parse(startDate), LocalDate.parse(endDate), "S");
            List<StatisticEntity> purchasesList = inOutRepository.findByIssueDtBetweenAndSalePurchaseClassification(LocalDate.parse(startDate), LocalDate.parse(endDate), "P");

            if (salesList.size() == 0 && purchasesList.size() == 0)
                break;

            int salesCount = salesList.size();
            int purchasesCount = purchasesList.size();

            for (int idx = 0; idx < salesList.size(); idx++) {
                salesSupplyPrice += salesList.get(idx).getSupplyPrice();
                salesTaxAmount += salesList.get(idx).getTaxAmount();
                salesTotalPrice += salesList.get(idx).getTotalPrice();
            }

            for (int idx = 0; idx < purchasesList.size(); idx++) {
                purchasesSupplyPrice += purchasesList.get(idx).getSupplyPrice();
                purchasesTaxAmount += purchasesList.get(idx).getTaxAmount();
                purchasesTotalPrice += purchasesList.get(idx).getTotalPrice();
            }

            statisticEntity.setSalesCount(salesCount);
            statisticEntity.setSalesSupplyPrice(salesSupplyPrice);
            statisticEntity.setSalesTaxAmount(salesTaxAmount);
            statisticEntity.setSalesTotalPrice(salesTotalPrice);
            statisticEntity.setPurchasesCount(purchasesCount);
            statisticEntity.setPurchasesSupplyPrice(purchasesSupplyPrice);
            statisticEntity.setPurchasesTaxAmount(purchasesTaxAmount);
            statisticEntity.setPurchasesTotalPrice(purchasesTotalPrice);

            inOutList.add(statisticEntity);

        }
        return inOutList;
    }

    /**
     * 합계들을 가져오는 메소드
     *
     * @param inOutList - 합계를 가져올 리스트
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<Long> findTotalList(List<StatisticEntity> inOutList) {
        List<Long> totalList = new ArrayList<>();

        long totalSalesCount = 0;
        long totalSalesSupplyPrice = 0;
        long totalSalesTaxAmount = 0;
        long totalSalesTotalPrice = 0;
        long totalPurchasesCount = 0;
        long totalPurchasesSupplyPrice = 0;
        long totalPurchasesTaxAmount = 0;
        long totalPurchasesTotalPrice = 0;

        for (int i = 0; i < inOutList.size(); i++) {
            totalSalesCount += inOutList.get(i).getSalesCount();
            totalSalesSupplyPrice += inOutList.get(i).getSalesSupplyPrice();
            totalSalesTaxAmount += inOutList.get(i).getSalesTaxAmount();
            totalSalesTotalPrice += inOutList.get(i).getSalesTotalPrice();
            totalPurchasesCount += inOutList.get(i).getPurchasesCount();
            totalPurchasesSupplyPrice += inOutList.get(i).getPurchasesSupplyPrice();
            totalPurchasesTaxAmount += inOutList.get(i).getPurchasesTaxAmount();
            totalPurchasesTotalPrice += inOutList.get(i).getPurchasesTotalPrice();
        }

        totalList.add(totalSalesCount);
        totalList.add(totalSalesSupplyPrice);
        totalList.add(totalSalesTaxAmount);
        totalList.add(totalSalesTotalPrice);
        totalList.add(totalPurchasesCount);
        totalList.add(totalPurchasesSupplyPrice);
        totalList.add(totalPurchasesTaxAmount);
        totalList.add(totalPurchasesTotalPrice);

        return totalList;
    }
}
