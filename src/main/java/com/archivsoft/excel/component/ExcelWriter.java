/**
 * ========================================================
 *
 * @FileName : ExcelWriter.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.excel.component;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.excel.constant.ExcelConstant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 특정 양식의 객체로 엑셀 객체로 만듬
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
public class ExcelWriter {

    /**
     * 엑셀 워크북 객체
     */
    private Workbook workbook;

    /**
     * 모델 맵 객체
     */
    private Map<String, Object> model;

    /**
     * Http 서블릿 요청 객체
     */
    private HttpServletRequest request;

    /**
     * Http 서블릿 응답 객체
     */
    private HttpServletResponse response;

    /**
     * ExcelWriter 생성자 메소드
     *
     * @param workbook
     * @param model
     * @param request
     * @param response
     * @history 2020 02 19 최초 생성
     */
    public ExcelWriter(Workbook workbook, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        this.workbook = workbook;
        this.model = model;
        this.request = request;
        this.response = response;
    }

    /**
     * 엑셀 업로드 응답으로 클래스 변수를 사용해 응답속성 입력과 엑셀 시트를 제작하는 메소드
     *
     * @history 2020 02 19 최초 생성
     */
    public void create() {
        applyFileNameForRequest(mapToFileName());
        applyContentTypeForRequest();
//        this.workbook = (Workbook) model.get("workbook");
//        Sheet sheet = workbook.createSheet();
//        createHead(sheet, mapToHeadList());
//        createBody(sheet, mapToBodyList());
    }

    /**
     * 전달받은 모델 맵 객체에서 파일 명을 리턴함
     *
     * @history 2020 02 19 최초 생성
     */
    private String mapToFileName() {
        return (String) model.get(ExcelConstant.FILE_NAME);
    }

    /**
     * 전달받은 모델 맵 객체에서 엑셀 헤더 리스트를 리턴함
     *
     * @history 2020 02 19 최초 생성
     */
    private List<String> mapToHeadList() {
        return (List<String>) model.get(ExcelConstant.HEAD);
    }

    /**
     * 전달받은 모델 맵 객체에서 엑셀 바디 리스트를 리턴함
     *
     * @history 2020 02 19 최초 생성
     */
    private List<StatisticEntity> mapToBodyList() {
        return (List<StatisticEntity>) model.get(ExcelConstant.BODY);
    }

    /**
     * 클래스 내 http 응답 객체의 헤더에 파일명을 추가함
     *
     * @param fileName
     * @history 2020 02 19 최초 생성
     */
    private void applyFileNameForRequest(String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + appendFileExtension(fileName) + "\"");
    }

    /**
     * 파일명에 적합한 확장자를 추가하여 리턴함
     *
     * @param fileName
     * @history 2020 02 19 최초 생성
     */
    private String appendFileExtension(String fileName) {
        if (workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof HSSFWorkbook) {
            fileName += ".xls";
        }

        return fileName;
    }

    /**
     * http 응답 객체의 헤더에 적합한 컨텐트 타입을 추가함
     *
     * @history 2020 02 19 최초 생성
     */
    private void applyContentTypeForRequest() {
        if (workbook instanceof XSSFWorkbook || workbook instanceof SXSSFWorkbook) {
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        if (workbook instanceof HSSFWorkbook) {
            response.setHeader("Content-Type", "application/vnd.ms-excel");
        }
    }

    /**
     * 생성된 엑셀 시트의 헤더들을 생성함
     *
     * @param sheet
     * @param headList
     * @history 2020 02 19 최초 생성
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