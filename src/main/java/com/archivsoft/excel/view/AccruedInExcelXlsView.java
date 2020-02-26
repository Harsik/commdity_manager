/**
 * ========================================================
 *
 * @FileName : AccruedInExcelXlsView.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.excel.view;

import com.archivsoft.dto.AccruedInDto;
import com.archivsoft.excel.component.ExcelWriter;
import com.archivsoft.excel.constant.ExcelConstant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * AbstractXlsView를 이용하여 엑셀 파일 뷰를 만드는 클래스
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-21
 */
@Component("accruedInExcelXlsView")
public class AccruedInExcelXlsView extends AbstractXlsView {

    /**
     * AbstractXlsView클래스의 엑셀 문서 생성 메소드를 불러오는 메소드
     *
     * @param model
     * @param workbook
     * @param request
     * @param response
     * @history 2020 02 21 최초 생성
     */
    @Override
    public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + model.get("fileName") + ".xls" + "\"");
        Sheet sheet = workbook.createSheet();
        createHead(sheet);
        createBody(sheet, (int) model.get("year"), (List<AccruedInDto>) model.get("body"));
    }

    /**
     * 엑셀 문서의 구분, 합계와 같은 헤더를 생성하는 메소드
     *
     * @param sheet
     * @history 2020 02 21 최초 생성
     */
    public void createHead(Sheet sheet) {
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9));

        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("구분");
        row0.createCell(1).setCellValue("기수금(A)");
        row0.createCell(5).setCellValue("미수금(B)");
        row0.createCell(9).setCellValue("합계(C=A+B)");

        Row row1 = sheet.createRow(1);
        row1.createCell(1).setCellValue("건수");
        row1.createCell(2).setCellValue("공급가액");
        row1.createCell(3).setCellValue("VAT");
        row1.createCell(4).setCellValue("합계");
        row1.createCell(5).setCellValue("건수");
        row1.createCell(6).setCellValue("공급가액");
        row1.createCell(7).setCellValue("VAT");
        row1.createCell(8).setCellValue("합계");
    }

    /**
     * 엑셀 문서의 바디를 생성하는 메소드
     *
     * @param sheet
     * @param year
     * @param accruedInDtos
     * @history 2020 02 21 최초 생성
     */
    public void createBody(Sheet sheet, int year, List<AccruedInDto> accruedInDtos) {
        createRow(sheet, "전년도 누계", accruedInDtos.get(0), 0 + 2);
        for (int i = 1; i < 13; i++) {
            createRow(sheet, i + "월", accruedInDtos.get(i), i + 2);
        }
        createRow(sheet, year + "년 계", accruedInDtos.get(13), 13 + 2);
        createRow(sheet, year + "년 누계", accruedInDtos.get(14), 14 + 2);
    }

    /**
     * 엑셀 문서의 바디의 행을 생성하는 메소드
     *
     * @param sheet
     * @param rowHead
     * @param accruedInDto
     * @param rowNum
     * @history 2020 02 21 최초 생성
     */
    public void createRow(Sheet sheet, String rowHead, AccruedInDto accruedInDto, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(rowHead);
        row.createCell(1).setCellValue(accruedInDto.getRecInCount());
        row.createCell(2).setCellValue(accruedInDto.getRecInSupplyPrice());
        row.createCell(3).setCellValue(accruedInDto.getRecInTaxAmount());
        row.createCell(4).setCellValue(accruedInDto.getRecInSupplyPrice() + accruedInDto.getRecInTaxAmount());
        row.createCell(5).setCellValue(accruedInDto.getAccInCount());
        row.createCell(6).setCellValue(accruedInDto.getAccInSupplyPrice());
        row.createCell(7).setCellValue(accruedInDto.getAccInTaxAmount());
        row.createCell(8).setCellValue(accruedInDto.getAccInSupplyPrice() + accruedInDto.getAccInTaxAmount());
        row.createCell(9).setCellValue(accruedInDto.getRecInSupplyPrice() + accruedInDto.getRecInTaxAmount() + accruedInDto.getAccInSupplyPrice() + accruedInDto.getAccInTaxAmount());
    }

}
