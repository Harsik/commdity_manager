package com.archivsoft.controller;


import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.service.InOutService;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 매출매입 Controller
 *
 * @author 류민송
 * @vesrion 1.0
 * @since 2020-02-19
 */
@Controller
@AllArgsConstructor
public class InOutController {

    /**
     * 매출매입 관련 서비스 선선
     */
    private InOutService inOutService;

    /**
     * 매출매입 목록 조회 페이지 이동 메소드
     *
     * @param request
     * @param model
     * @return url
     * @history
     *      2020-02-20 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @GetMapping("/inOutList")
    public String getInOutList(HttpServletRequest request, Model model) {

        String year = "2019";

        if (request.getParameter("year") != null && !request.getParameter("year").equals("")) {
            year = request.getParameter("year");
        }

        List<StatisticEntity> inOutList = inOutService.findInOutList(year);
        List<Long> totalList = inOutService.findTotalList(inOutList);
        List<String> yearList = inOutService.findYearList();

        model.addAttribute("inOutList", inOutList);
        model.addAttribute("totalList", totalList);
        model.addAttribute("yearList", yearList);
        model.addAttribute("nowYear", year);

        return "admin/adminInOutListFM";
    }

    /**
     * Db에 있는 연도 조회  메소드
     *
     * @return list
     * @history 2020-02-20 최초 생성
     */
    @ResponseBody
    @GetMapping("/getYearList")
    public List<String> findYearList() {
        return inOutService.findYearList();
    }

    /**
     * 매출매입 목록 다운로드 메소드
     *
     * @param request
     * @param response
     * @return void
     * @history 2020-02-20 최초 생성
     */
    @GetMapping("/inOutListDownLoad")
    public void excelDown(HttpServletRequest request, HttpServletResponse response) {

        String year = "2019";

        if (request.getParameter("year") != null && !request.getParameter("year").equals("")) {
            year = request.getParameter("year");
        }

        List<StatisticEntity> inOutList = inOutService.findInOutList(year);
        List<Long> totalList = inOutService.findTotalList(inOutList);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("매출매입 총괄표");
        String fileName = "inOutList.xlsx";

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(2);

        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("구분");

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("매출");

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("매입");

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 4));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));

        row = sheet.createRow(3);

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("건수");
        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("공급가액");
        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("VAT");
        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("합계");
        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("건수");
        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("공급가액");
        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("VAT");
        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("합계");

        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        font = workbook.createFont();
        font.setFontName("맑은 고딕");
        font.setBold(false);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

        for (int i = 0; i < inOutList.size(); i++) {
            row = sheet.createRow(4 + i);

            cell = row.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(i + 1 + "월");

            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getSalesCount());

            cell = row.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getSalesSupplyPrice());

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getSalesTaxAmount());

            cell = row.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getSalesTotalPrice());

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getPurchasesCount());

            cell = row.createCell(6);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getPurchasesSupplyPrice());

            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getPurchasesTaxAmount());

            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(inOutList.get(i).getPurchasesTotalPrice());
        }

        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        font = workbook.createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

        row = sheet.createRow(4 + inOutList.size());


        cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("계");

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(0));

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(1));

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(2));

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(3));

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(4));

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(5));

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(6));

        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(totalList.get(7));

        for (int i = 0; i < 8 + inOutList.size(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 512);
        }

        try {
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
