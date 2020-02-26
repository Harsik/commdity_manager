/**
 * ========================================================
 *
 * @FileName : PurchaseService.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.PurchaseRepository;
import com.archivsoft.dto.PurchaseDto;
import com.archivsoft.dto.PurchaseRequest;
import com.archivsoft.excel.component.ExcelReader;
import com.archivsoft.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 매출 매입 중 매입 계산서 리스트를 출력하고 입력되는 조건에 맞는 데이터를 불러와
 * 화면에 표시하거나 엑셀 다운로드 업로드가 가능하도록 한다.
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Service
@AllArgsConstructor
public class PurchaseService {

    /** ExcelReader를 사용하기 위한 변수 선언 */
    @Autowired
    private ExcelReader excelReader;

    /** PurchaseRepository 사용하기 위한 변수 선언 */
    private PurchaseRepository purchaseRepository;

    /**
     * 엑셀파일로 업로드한 데이터를 저장하는 메소드
     *
     * @param approveNum
     * @param paymentYN
     * @param paymentDt
     * @history 2020 02 19 최초 생성
     */
    public void savePayment(String approveNum, String paymentYN, LocalDate paymentDt) {
        StatisticEntity statisticEntity = purchaseRepository.findById(approveNum).orElseThrow(() -> new NotFoundException("StatisticEntity not found with id : " + approveNum));
        statisticEntity.setPaymentYN(paymentYN);
        statisticEntity.setPaymentDt(paymentDt);
        purchaseRepository.save(statisticEntity);
    }
    /**
     * 엑셀파일로 업로드한 데이터를 저장하는 메소드
     *
     * @param multipartFile
     * @history 2020 02 19 최초 생성
     */
    public void savePurchaseList(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        purchaseRepository.saveAll(excelReader.readFileToList(multipartFile, PurchaseDto::toRow));
    }

    /**
     * 조건에 맞는 리스트를 엑셀파일로 다운받기 위한 데이터를 출력하는 메소드
     *
     * @param purchaseRequest
     * @history 2020 02 19 최초 생성
     */
    public List<StatisticEntity> findPurchaseList(PurchaseRequest purchaseRequest) {

        List<StatisticEntity> purchaseList;

        if (purchaseRequest.getPaymentYN().equals("All")) {
            purchaseRequest.setPaymentYN("");
        }

        if (purchaseRequest == null) {
            purchaseList = loadPurchaseList();
        } else if (purchaseRequest.getFromRegDt() != null && purchaseRequest.getToRegDt() != null && purchaseRequest.getFromIssueDt() != null && purchaseRequest.getToIssueDt() != null) {
            purchaseList = loadRegDtBetweenAndIssueDtBetweenPurchaseList(purchaseRequest);
        } else if (purchaseRequest.getFromIssueDt() != null && purchaseRequest.getToIssueDt() != null) {
            purchaseList = loadIssueDtBetweenPurchaseList(purchaseRequest);
        } else if (purchaseRequest.getFromRegDt() != null && purchaseRequest.getToRegDt() != null) {
            purchaseList = loadRegDtBetweenPurchaseList(purchaseRequest);
        } else {
            purchaseList = loadPurchaseList();
        }

        return purchaseList;
    }

    /**
     * 조건에 맞는 리스트를 화면상에 출력하기 위해 데이터를 반환하는 메소드
     *
     * @param page
     * @param purchaseRequest
     * @history 2020 02 19 최초 생성
     */
    public Page<StatisticEntity> findPurchaseList(int page, PurchaseRequest purchaseRequest) {

        int size = 5;
        Page<StatisticEntity> purchaseList;

        if (purchaseRequest.getPaymentYN().equals("All")) {
            purchaseRequest.setPaymentYN("");
        }

        if (purchaseRequest == null) {
            purchaseList = loadPurchaseList(page - 1, size);
        } else if (purchaseRequest.getFromRegDt() != null && purchaseRequest.getToRegDt() != null && purchaseRequest.getFromIssueDt() != null && purchaseRequest.getToIssueDt() != null) {
            purchaseList = loadRegDtBetweenAndIssueDtBetweenPurchaseList(purchaseRequest, page - 1, size);
        } else if (purchaseRequest.getFromIssueDt() != null && purchaseRequest.getToIssueDt() != null) {
            purchaseList = loadIssueDtBetweenPurchaseList(purchaseRequest, page - 1, size);
        } else if (purchaseRequest.getFromRegDt() != null && purchaseRequest.getToRegDt() != null) {
            purchaseList = loadRegDtBetweenPurchaseList(purchaseRequest, page - 1, size);
        } else {
            purchaseList = loadPurchaseList(page - 1, size);
        }

        return purchaseList;
    }

    /**
     * 시스템 시간 기준 해당 년도 1월 1일부터 12월 31일까지를 작성일 기준으로 리스트 출력
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> loadPurchaseList() {
        return purchaseRepository.findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(LocalDate.of(LocalDate.now().getYear(),01,01), LocalDate.of(LocalDate.now().getYear(),12,31), "", "", "P");
    }

    /**
     * 요청 받은 작성일 기준으로 상호명, 수금여부에 해당하는 리스트 출력
     *
     * @param purchaseRequest
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> loadRegDtBetweenPurchaseList(PurchaseRequest purchaseRequest) {
        return purchaseRepository.findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(purchaseRequest.getFromRegDt(), purchaseRequest.getToRegDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P");
    }

    /**
     * 요청 받은 발급일 기준으로 상호명, 수금여부에 해당하는 리스트 출력
     *
     * @param purchaseRequest
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> loadIssueDtBetweenPurchaseList(PurchaseRequest purchaseRequest) {
        return purchaseRepository.findAllByIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByIssueDtDesc(purchaseRequest.getFromIssueDt(), purchaseRequest.getToIssueDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P");
    }

    /**
     * 요청 받은 작성일, 발급일 기준으로 상호명, 수금여부에 해당하는 리스트 출력
     *
     * @param purchaseRequest
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<StatisticEntity> loadRegDtBetweenAndIssueDtBetweenPurchaseList(PurchaseRequest purchaseRequest) {
        return purchaseRepository.findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDescIssueDtDesc(purchaseRequest.getFromRegDt(), purchaseRequest.getToRegDt(), purchaseRequest.getFromIssueDt(), purchaseRequest.getToIssueDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P");
    }

   /**
    * 시스템 시간 기준 해당 년도 1월 1일부터 12월 31일까지를 작성일 기준으로 페이지 출력
     *
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<StatisticEntity> loadPurchaseList(int page, int size) {
        return purchaseRepository.findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(LocalDate.of(LocalDate.now().getYear(),01,01), LocalDate.of(LocalDate.now().getYear(),12,31), "", "", "P", PageRequest.of(page, size));
    }

    /**
     * 요청 받은 작성일 기준으로 상호명, 수금여부에 해당하는 페이지 출력
     *
     * @param purchaseRequest
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<StatisticEntity> loadRegDtBetweenPurchaseList(PurchaseRequest purchaseRequest, int page, int size) {
        return purchaseRepository.findAllByRegDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDesc(purchaseRequest.getFromRegDt(), purchaseRequest.getToRegDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P", PageRequest.of(page, size));
    }

    /**
     * 요청 받은 발급일 기준으로 상호명, 수금여부에 해당하는 페이지 출력
     *
     * @param purchaseRequest
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<StatisticEntity> loadIssueDtBetweenPurchaseList(PurchaseRequest purchaseRequest, int page, int size) {
        return purchaseRepository.findAllByIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByIssueDtDesc(purchaseRequest.getFromIssueDt(), purchaseRequest.getToIssueDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P", PageRequest.of(page, size));
    }

    /**
     * 요청 받은 작성일, 발급일 기준으로 상호명, 수금여부에 해당하는 페이지 출력
     *
     * @param purchaseRequest
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<StatisticEntity> loadRegDtBetweenAndIssueDtBetweenPurchaseList(PurchaseRequest purchaseRequest, int page, int size) {
        return purchaseRepository.findAllByRegDtBetweenAndIssueDtBetweenAndCompanyNMContainingAndPaymentYNContainingAndSalePurchaseClassificationOrderByRegDtDescIssueDtDesc(purchaseRequest.getFromRegDt(), purchaseRequest.getToRegDt(), purchaseRequest.getFromIssueDt(), purchaseRequest.getToIssueDt(), purchaseRequest.getCompanyNM(), purchaseRequest.getPaymentYN(), "P", PageRequest.of(page, size));
    }

}
