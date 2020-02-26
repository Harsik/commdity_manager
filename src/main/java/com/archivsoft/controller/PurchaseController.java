/**
 * ========================================================
 *
 * @FileName : PurchaseController.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 13.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 13.    홍성관    1.0    최초생성
 * =========================================================
 */

package com.archivsoft.controller;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.dto.PurchaseRequest;
import com.archivsoft.excel.constant.ExcelConstant;
import com.archivsoft.excel.view.PurchaseExcelXlsView;
import com.archivsoft.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 매출 매입 중 매입 계산서 리스트를 출력하고 입력되는 조건에 맞는 데이터를 불러오거나
 * 그 데이터로 엑셀 파일을 다운로드하거나 업로드 양식에 맞는 엑셀 파일을 업로드하여
 * 화면에 보여주는 등 다양한 요청에 대해 응답한다.
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class PurchaseController {

    /**
     * PurchaseService를 사용하기 위한 변수 선언
     */
    private PurchaseService purchaseService;

    /**
     * 페이지 경로를 가지지 않은 주소로 접근시 경로를 가지도록 리다이렉트 하기 위한 메소드
     *
     * @param model
     * @return "redirect:/purchaseList/1"
     * @url "/purchaseList"
     * @history 2020 02 19 최초 생성
     */
    @GetMapping("/purchaseList")
    public String findPurchaseList(PurchaseRequest purchaseRequest, Model model) {
        Page<StatisticEntity> purchaseList = purchaseService.findPurchaseList(1, purchaseRequest);
        int totalPrice = 0, supplyPrice = 0, taxAmount = 0;
        Map<String, Integer> totals = new HashMap();

        for (StatisticEntity statisticEntity : purchaseList) {
            totalPrice += statisticEntity.getTotalPrice();
            supplyPrice += statisticEntity.getSupplyPrice();
            taxAmount += statisticEntity.getTaxAmount();
        }

        totals.put("count", (int) purchaseList.getTotalElements());
        totals.put("totalPrice", totalPrice);
        totals.put("supplyPrice", supplyPrice);
        totals.put("taxAmount", taxAmount);

        model.addAttribute("purchaseList", purchaseList);
        model.addAttribute("totals", totals);
        model.addAttribute("page", 1);

        return "admin/adminPurchaseListFM";
    }

    /**
     * 페이지 값에 맞는 리스트와 전체 리스트 수, 합계금액, 공급가액, 세액 조회
     *
     * @param page
     * @param purchaseRequest
     * @param model
     * @return "admin/adminPurchaseListFM"
     * @url "/purchaseList/{page}"
     * @history 2020 02 19 최초 생성
     */
    @PostMapping("/purchaseList/{page}")
    public String findPurchaseList(@PathVariable int page, PurchaseRequest purchaseRequest, Model model) {
        Page<StatisticEntity> purchaseList = purchaseService.findPurchaseList(page, purchaseRequest);
        int totalPrice = 0, supplyPrice = 0, taxAmount = 0;
        Map<String, Integer> totals = new HashMap();

        for (StatisticEntity statisticEntity : purchaseList) {
            totalPrice += statisticEntity.getTotalPrice();
            supplyPrice += statisticEntity.getSupplyPrice();
            taxAmount += statisticEntity.getTaxAmount();
        }

        totals.put("count", (int) purchaseList.getTotalElements());
        totals.put("totalPrice", totalPrice);
        totals.put("supplyPrice", supplyPrice);
        totals.put("taxAmount", taxAmount);

        model.addAttribute("purchaseRequest", purchaseRequest);
        model.addAttribute("purchaseList", purchaseList);
        model.addAttribute("totals", totals);
        model.addAttribute("page", page);

        return "admin/adminPurchaseListFM";
    }

    /**
     * 엑셀 파일 값을 토대로 화면상에 리스트를 보여주는 메소드
     *
     * @param approveNum
     * @param paymentYN
     * @param paymentDt
     * @url "/savePurchase"
     * @history 2020 02 19 최초 생성
     */
    @ResponseBody
    @GetMapping("/savePurchase")
    public void savePayment(@RequestParam String approveNum, @RequestParam String paymentYN, @RequestParam String paymentDt) {
        purchaseService.savePayment(approveNum, paymentYN, LocalDate.parse(paymentDt));
    }

    /**
     * 검색 조건에 맞는 리스트 엑셀로 출력하는 메소드
     *
     * @param purchaseRequest
     * @param model
     * @url "/purchaseList/writerExcel"
     * @history 2020 02 19 최초 생성
     */
    @ResponseBody
    @PostMapping("/purchaseList/writerExcel")
    public View getWriterExcel(@ModelAttribute PurchaseRequest purchaseRequest, Map<String, Object> model) {
        model.put(ExcelConstant.FILE_NAME, "purchaseList");
        model.put(ExcelConstant.HEAD, ExcelConstant.PURCHASEHEAD);
        model.put(ExcelConstant.BODY, purchaseService.findPurchaseList(purchaseRequest));

        return new PurchaseExcelXlsView();
    }

    /**
     * 엑셀 파일 값을 토대로 화면상에 리스트를 보여주는 메소드
     *
     * @param multipartFile
     * @url "/purchaseList/readExcel"
     * @history 2020 02 19 최초 생성
     */
    @PostMapping("/purchaseList/readExcel")
    public String saveReadExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException, InvalidFormatException {
        purchaseService.savePurchaseList(multipartFile);

        return "redirect:/admin/purchaseList";
    }

}