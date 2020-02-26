
package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.SalesRepository;
import com.archivsoft.domain.repository.StatisticRepository;
import com.archivsoft.dto.SearchConditionDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 매출계산서 목록을 출력하고 조회, 등록, 수정
 * @author 강병수
 * @version 1.0
 * @since 2020.02.03
 */
@Service
@AllArgsConstructor
public class SalesService {

    /**
     * SalesRepository를 사용하기 위한 변수 선언
     */
    private SalesRepository salesRepository;

    /**
     * 매출계산서 등록
     * @param statisticEntity 등록할 데이터를 담고있는 객체
     * @history
     *     2020.02.12 최초생성
     */
    public String createSalesInfo(StatisticEntity statisticEntity) {
        return salesRepository.save(statisticEntity).getApproveNum();
    }

    /**
     * 매출계산서 수정
     * @param statisticEntity 수정할 데이터를 담고있는 객체
     * @history
     *     2020.02.12 최초생성
     */
    public String saveSalesInfo(StatisticEntity statisticEntity) {
        return salesRepository.save(statisticEntity).getApproveNum();
    }

    /**
     * 매출계산서 조회
     * @param approveNum 조회할 데이터의 키값
     * @history
     *     2020.02.12 최초생성
     */
    public StatisticEntity findCollectionMoney(String approveNum) {
        return salesRepository.findByApproveNum(approveNum);
    }

    /**
     * 매출계산서 목록 검색조건별 조회
     * @param scdto 조회하기 위한 검색조건을 담고 있는 객체
     * @param page 페이징 처리 후 가져올 목록의 페이지
     * @param limit 페이징 처리 시 한 페이지에 저장할 목록의 최댓값
     * @history
     *     2020.02.12 최초생성
     */
    public Page<StatisticEntity> findSalesList(int page, int limit, SearchConditionDto scdto) {
        Page<StatisticEntity> salesList;

        if (scdto.getCollectionMoneyYN() == null) {
            scdto.setCollectionMoneyYN("T");
        }
        if (scdto.getCompanyNM() == null) {
            scdto.setCompanyNM("");
        }
        if (scdto.getFirstRegDt() != null && scdto.getFirstIssueDt() != null && !scdto.getCompanyNM().equals("") && (scdto.getCollectionMoneyYN().equals("T"))) {
            salesList = salesRepository.findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndCollectionMoneyYNAndSalePurchaseClassificationOrderByRegDtAsc(scdto.getFirstRegDt(), scdto.getLastRegDt(), scdto.getFirstIssueDt(), scdto.getLastIssueDt(), scdto.getCompanyNM(), scdto.getCollectionMoneyYN(), "S", PageRequest.of(page, limit));
        } else if (scdto.getFirstRegDt() != null && scdto.getFirstIssueDt() == null && scdto.getCompanyNM().equals("") && scdto.getCollectionMoneyYN().equals("T")) {
            salesList = salesRepository.findAllByRegDtBetweenAndSalePurchaseClassificationOrderByRegDtAsc(scdto.getFirstRegDt(), scdto.getLastRegDt(), "S", PageRequest.of(page, limit));
        } else if (scdto.getFirstRegDt() == null && scdto.getFirstIssueDt() != null && scdto.getCompanyNM().equals("") && scdto.getCollectionMoneyYN().equals("T")) {
            salesList = salesRepository.findAllByIssueDtBetweenAndSalePurchaseClassificationOrderByRegDtAsc(scdto.getFirstRegDt(), scdto.getLastRegDt(), "S", PageRequest.of(page, limit));
        } else if (scdto.getFirstRegDt() == null && scdto.getFirstIssueDt() == null && !scdto.getCompanyNM().equals("") && scdto.getCollectionMoneyYN().equals("T")) {
            salesList = salesRepository.findAllByRecipientCompanyNMContainingAndSalePurchaseClassificationOrderByRegDtAsc(scdto.getCompanyNM(), "S", PageRequest.of(page, limit));
        } else if (scdto.getFirstRegDt() == null && scdto.getFirstIssueDt() == null && scdto.getCompanyNM().equals("") && !scdto.getCollectionMoneyYN().equals("T")) {
            salesList = salesRepository.findAllByCollectionMoneyYNEqualsAndSalePurchaseClassificationOrderByRegDtAsc(scdto.getCollectionMoneyYN(), "S", PageRequest.of(page, limit));
        } else {
            salesList = salesRepository.findAllBySalePurchaseClassificationOrderByRegDtAsc("S", PageRequest.of(page, limit));
        }
        return salesList;
    }

    /**
     * 검색 조건별 조회된 매출계산서 목록의 금액별 합계 계산
     * @param scdto 조회하기 위한 검색조건을 담고 있는 객체
     * @param statisticEntityList 계산에 필요한 데이터를 가지고 있는 목록
     * @param limit 페이징 처리 시 한 페이지에 저장할 목록의 최댓값
     * @history
     *     2020.02.12 최초생성
     */
    public List<Long> findPriceList(Page<StatisticEntity> statisticEntityList, int limit, SearchConditionDto scdto) {
        List<Long> priceList = new ArrayList<>();
        long tot = 0;
        long totalPrice = 0;
        long taxAmount = 0;
        long supplyPrice = 0;
        if (statisticEntityList.getSize() != 0) {
            for (int pageIdx = 0; pageIdx < statisticEntityList.getTotalPages(); pageIdx++) {
                Page<StatisticEntity> salesTotList = findSalesList(pageIdx, limit, scdto);
                for (int listNum = 0; listNum < salesTotList.getNumberOfElements(); listNum++) {
                    StatisticEntity statisticEntity = salesTotList.getContent().get(listNum);
                    supplyPrice += statisticEntity.getSupplyPrice();
                    taxAmount += statisticEntity.getTaxAmount();
                }
            }
            totalPrice = taxAmount + supplyPrice;
            tot = statisticEntityList.getTotalElements();
        }
        priceList.add(tot);
        priceList.add(totalPrice);
        priceList.add(supplyPrice);
        priceList.add(taxAmount);
        return priceList;
    }

}