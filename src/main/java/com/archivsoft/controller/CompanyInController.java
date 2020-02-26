
package com.archivsoft.controller;
import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.service.CompanyInService;
import lombok.AllArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 업체별 미수금,미지급 현황 조회
 @author 강병수
 @vesrion 1.0
 @since 2020-02-19

 */
@Controller
@AllArgsConstructor
public class CompanyInController {

    /**
     * CompanyInService를 사용하기 위한 변수 선언
     */
    private CompanyInService companyInService;

    /**
     * 업체별 미수금,미지급 현황 목록 조회
     * @param model 조회한 리스트를 view로 전달하기 위한 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @return String
     * @history
     *     2020.02.19 최초 생성
     *     2020.02.20 리스트 조회 추가
     */
    @GetMapping("/companyInList")
    public String findCompanyInList(Model model, HttpServletRequest request) {
        Calendar today = Calendar.getInstance();
        String year = String.valueOf(today.get(Calendar.YEAR));
        List<String> yearList = new ArrayList<>();
        for (int idx = today.get(Calendar.YEAR); idx > 2015; idx--) {
            yearList.add(String.valueOf(idx));
        }
        if (request.getParameter("searchYear") != null) {
            year = request.getParameter("searchYear");
        }
        String kinds = request.getParameter("searchMoney");
        int supplyPrice = 0;
        int taxAmount = 0;
        int totalPrice = 0;
        int salePurchaseMoney = 0;
        List<Integer> priceList = new ArrayList<>();
        List<StatisticEntity> companyInList = new ArrayList<>();
        if (kinds.equals("collection")) {
//            companyInList = companyInService.findCompanyInCollectionMoneyList(year);
        } else {
//            companyInList = companyInService.findCompanyInPaymentMoneyList(year);
        }
        for (int idx = 0; idx < companyInList.size(); idx++) {
            StatisticEntity statis = companyInList.get(idx);
            supplyPrice += statis.getSupplyPrice();
            taxAmount += statis.getTaxAmount();
            totalPrice += statis.getTotalPrice();
            salePurchaseMoney += statis.getSalePurchaseMoney();
        }
        priceList.add(supplyPrice);
        priceList.add(taxAmount);
        priceList.add(totalPrice);
        priceList.add(salePurchaseMoney);
        priceList.add(totalPrice-salePurchaseMoney);

        model.addAttribute("companyInList", companyInList);
        model.addAttribute("priceList",priceList);
        model.addAttribute("searchYear",year);
        model.addAttribute("yearList",yearList);
        model.addAttribute("kinds",kinds);
        return "/admin/adminCompanyInListFM";
    }

    /**
     * 업체별 미수금,미지급 현황 목록 엑셀 다운로드
     * @param response 요청에 대한 HTTP 응답을 나타내는 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @history
     *     2020.02.19 최초생성
     */
    @RequestMapping(value = "/companyInListExcelDownload")
    public void ExcelDownload(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String year = request.getParameter("searchYear");
        String kinds = request.getParameter("searchMoney");
        DownloadExcel(response, year,kinds);
    }

    /**
     * 매출계산서 목록 엑셀 다운로드 실행
     * @param response 요청에 대한 HTTP 응답을 나타내는 객체
     * @param year HTML에서 전달되는 검색조건을 받기 위한 객체
     * @param kinds HTML에서 전달되는 검색조건을 받기 위한 객체
     * @history
     *     2020.02.19 최초생성
     *     2020.02.20 셀 서식설정
     *     2020.02.21 파일이름 한글로 변경
     */
    public void DownloadExcel(HttpServletResponse response, String year, String kinds) {
        Workbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        cellStyle.setFont(font);

        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle2.setAlignment(HorizontalAlignment.CENTER);
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);

        String fileName = "";
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(currentDate.getTime());
        Sheet sheet;
        Row row;
        Cell cell;

        if (kinds.equals("collection")) {
            fileName = year+"년 업체별 미수금 현황_"+today+".xlsx";
            sheet = workbook.createSheet("("+year+")업체별 미수 현황");
            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellValue("■ "+year+"년 업체별 미수 현황");
        } else {
            fileName =  year+"년 업체별 미지급 현황_"+today+".xlsx";
            sheet = workbook.createSheet("("+year+")업체별 미지급 현황");
            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellValue("■ "+year+"년 업체별 미지급 현황");
        }
        sheet.addMergedRegion(new CellRangeAddress(3,4,1,1));
        sheet.addMergedRegion(new CellRangeAddress(3,4,2,2));
        sheet.addMergedRegion(new CellRangeAddress(3,3,3,5));
        sheet.addMergedRegion(new CellRangeAddress(3,4,6,6));
        sheet.addMergedRegion(new CellRangeAddress(3,4,7,7));
        sheet.addMergedRegion(new CellRangeAddress(3,4,8,8));
        sheet.addMergedRegion(new CellRangeAddress(5,5,1,2));

        row = sheet.createRow(3);
        cell = row.createCell(1);
        cell.setCellValue("사업자번호");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellValue("업체명");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellValue("매출");
        cell.setCellStyle(cellStyle);
        if (kinds.equals("collection")) {
            cell = row.createCell(6);
            cell.setCellValue("기수금");
            cell.setCellStyle(cellStyle);
            cell = row.createCell(7);
            cell.setCellValue("미수금");
            cell.setCellStyle(cellStyle);
        } else {
            cell = row.createCell(6);
            cell.setCellValue("기지급");
            cell.setCellStyle(cellStyle);
            cell = row.createCell(7);
            cell.setCellValue("미지급");
            cell.setCellStyle(cellStyle);
        }
        cell = row.createCell(8);
        cell.setCellValue("비고");
        cell.setCellStyle(cellStyle);
        row = sheet.createRow(4);
        cell = row.createCell(3);
        cell.setCellValue("공급가액");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(4);
        cell.setCellValue("부가세");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(5);
        cell.setCellValue("합계");
        cell.setCellStyle(cellStyle);

        int supplyPrice = 0;
        int taxAmount = 0;
        int totalPrice = 0;
        int salePurchaseMoney = 0;
        List<StatisticEntity> companyInList = new ArrayList<>();
        if (kinds.equals("collection")) {
//            companyInList = companyInService.findCompanyInCollectionMoneyList(year);
        } else {
//            companyInList = companyInService.findCompanyInPaymentMoneyList(year);
        }
        for (int rowIdx = 0; rowIdx < companyInList.size(); rowIdx++) {
            StatisticEntity statis = companyInList.get(rowIdx);
            row = sheet.createRow(rowIdx+6);

            if (kinds.equals("collection")) {
                cell = row.createCell(1);
                cell.setCellValue(statis.getRecipientBusinessNum());
                cell = row.createCell(2);
                cell.setCellValue(statis.getRecipientCompanyNM());
            } else {
                cell = row.createCell(1);
                cell.setCellValue(statis.getBusinessNum());
                cell = row.createCell(2);
                cell.setCellValue(statis.getCompanyNM());
            }
            cell = row.createCell(3);
            cell.setCellValue(statis.getSupplyPrice());
            cell = row.createCell(4);
            cell.setCellValue(statis.getTaxAmount());
            cell = row.createCell(5);
            cell.setCellValue(statis.getTotalPrice());
            cell = row.createCell(6);
            cell.setCellValue(statis.getSalePurchaseMoney());
            cell = row.createCell(7);
            cell.setCellValue(statis.getTotalPrice()-statis.getSalePurchaseMoney());
            supplyPrice += statis.getSupplyPrice();
            taxAmount += statis.getTaxAmount();
            totalPrice += statis.getTotalPrice();
            salePurchaseMoney += statis.getSalePurchaseMoney();
            cell = row.createCell(8);
            cell.setCellValue(statis.getNote());
        }
        row = sheet.createRow(5);
        cell = row.createCell(1);
        cell.setCellValue("합계");
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(3);
        cell.setCellValue(supplyPrice);
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(4);
        cell.setCellValue(taxAmount);
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(5);
        cell.setCellValue(totalPrice);
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(6);
        cell.setCellValue(salePurchaseMoney);
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(7);
        cell.setCellValue(totalPrice-salePurchaseMoney);
        cell.setCellStyle(cellStyle2);
        cell = row.createCell(8);
        cell.setCellStyle(cellStyle2);
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            fileName = fileName.replaceAll("\\+","%20");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; fileName=" + fileName);
            response.setContentType("application/vnd.ms-excel");
            ServletOutputStream sos = response.getOutputStream();
            workbook.write(sos);
            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
