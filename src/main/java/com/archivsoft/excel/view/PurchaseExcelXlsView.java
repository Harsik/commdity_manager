/**
 * ========================================================
 *
 * @FileName : PurchaseExcelXlsView.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 21.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 21.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.excel.view;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.dto.AccruedInDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * AbstractXlsView를 이용하여 엑셀 파일 뷰를 만드는 클래스
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-21
 */
@Component("purchaseExcelXlsView")
public class PurchaseExcelXlsView extends AbstractXlsView {

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
        createHead(sheet, (List<String>) model.get("head"));
        createBody(sheet, (List<StatisticEntity>) model.get("body"));
    }

    /**
     * 엑셀 문서의 구분, 합계와 같은 헤더를 생성하는 메소드
     *
     * @param sheet
     * @history 2020 02 21 최초 생성
     */
    private void createHead(Sheet sheet, List<String> headList) {
        int size = headList.size();
        Row row = sheet.createRow(0);

        for (int i = 0; i < size; i++) {
            row.createCell(i).setCellValue(headList.get(i));
        }
    }

    /**
     * 생성된 엑셀 시트의 바디들을 생성함
     *
     * @param sheet
     * @param bodyList
     * @history 2020 02 19 최초 생성
     */
    private void createBody(Sheet sheet, List<StatisticEntity> bodyList) {
        int rowSize = bodyList.size();
        for (int i = 0; i < rowSize; i++) {
            createRow(sheet, bodyList.get(i), i + 1);
        }
    }

    /**
     * 전달 받은 오브젝트 값이 널인지 확인하고 널일시 ""를 리턴함
     *
     * @param value
     * @history 2020 02 19 최초 생성
     */
    private Object isCell(Object value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    /**
     * 생성된 엑셀 시트의 바디에 해당하는 행들을 생성함
     *
     * @param sheet
     * @param cellList
     * @param rowNum
     * @history 2020 02 19 최초 생성
     */
    private void createRow(Sheet sheet, StatisticEntity cellList, int rowNum) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(isCell(cellList.getRegDt()).toString());
        row.createCell(1).setCellValue((String) isCell(cellList.getApproveNum()));
        row.createCell(2).setCellValue(isCell(cellList.getIssueDt()).toString());
        row.createCell(3).setCellValue(isCell(cellList.getSendDt()).toString());
        row.createCell(4).setCellValue((String) isCell(cellList.getBusinessNum()));
        row.createCell(5).setCellValue((String) isCell(cellList.getSubBusinessNum()));
        row.createCell(6).setCellValue((String) isCell(cellList.getCompanyNM()));
        row.createCell(7).setCellValue((String) isCell(cellList.getCeoName()));
        row.createCell(8).setCellValue((String) isCell(cellList.getAddress()));
        row.createCell(9).setCellValue((String) isCell(cellList.getRecipientBusinessNum()));
        row.createCell(10).setCellValue((String) isCell(cellList.getRecipientSubBusinessNum()));
        row.createCell(11).setCellValue((String) isCell(cellList.getRecipientCompanyNM()));
        row.createCell(12).setCellValue((String) isCell(cellList.getRecipientCeoNM()));
        row.createCell(13).setCellValue((String) isCell(cellList.getRecipientAddress()));
        row.createCell(14).setCellValue((int) isCell(cellList.getTotalPrice()));
        row.createCell(15).setCellValue((int) isCell(cellList.getSupplyPrice()));
        row.createCell(16).setCellValue((int) isCell(cellList.getTaxAmount()));
        row.createCell(17).setCellValue((String) isCell(cellList.getInvoiceClassify()));
        row.createCell(18).setCellValue((String) isCell(cellList.getInvoiceType()));
        row.createCell(19).setCellValue((String) isCell(cellList.getIssueType()));
        row.createCell(20).setCellValue((String) isCell(cellList.getPriceNote()));
        row.createCell(21).setCellValue((String) isCell(cellList.getClassfication()));
        row.createCell(22).setCellValue((String) isCell(cellList.getEmail()));
        row.createCell(23).setCellValue((String) isCell(cellList.getRecipientEmail1()));
        row.createCell(24).setCellValue((String) isCell(cellList.getRecipientEmail2()));
        row.createCell(25).setCellValue(isCell(cellList.getItemDT()).toString());
        row.createCell(26).setCellValue((String) isCell(cellList.getItemNM()));
        row.createCell(27).setCellValue((String) isCell(cellList.getItemSpec()));
        row.createCell(28).setCellValue((String) isCell(cellList.getItemQuantity()));
        row.createCell(29).setCellValue((int) isCell(cellList.getItemUnitPrice()));
        row.createCell(30).setCellValue((int) isCell(cellList.getItemSupplyPrice()));
        row.createCell(31).setCellValue((int) isCell(cellList.getItemTaxPrice()));
        row.createCell(32).setCellValue((String) isCell(cellList.getItemNote()));
    }
}
