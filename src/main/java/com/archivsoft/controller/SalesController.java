package com.archivsoft.controller;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.dto.SearchConditionDto;
import com.archivsoft.service.SalesService;
import lombok.AllArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * 매출계산서 목록 조회, 매출계산서 수정, 등록
 * @author 강병수
 * @version 1.0
 * @since 2020.02.10
 */
@Controller
@AllArgsConstructor
public class SalesController {

    /**
     * SalesService를 사용하기 위한 변수 선언
     */
    private SalesService salesService;

    /**
     * 매출계산서 목록 조회
     * @param model 조회한 리스트를 view로 전달하기 위한 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @param scdto HTML에서 전달되는 검색조건을 받기 위한 객체
     * @return String
     * @history
     *     2020.02.10 최초생성
     *     2020.02.19 페이징변경
     */
    @GetMapping("/salesList")
    public String serviceList(Model model, HttpServletRequest request, SearchConditionDto scdto) {
        int limit = 10;
        int page = 1;

        if (request.getParameter("pageNum") != null && !request.getParameter("pageNum").equals("")) {
            page = Integer.parseInt(request.getParameter("pageNum"));
        }
        Page<StatisticEntity> salesList = salesService.findSalesList(page - 1, limit, scdto);
        List<Long> priceList = salesService.findPriceList(salesList, limit, scdto);

        model.addAttribute("priceList", priceList);
        model.addAttribute("searchCondition", scdto);
        model.addAttribute("salesList", salesList);
        model.addAttribute("pageNum",page);
        return "/admin/adminSalesListFM";

    }

    /**
     * 매출계산서 목록 엑셀 다운로드
     * @param response 요청에 대한 HTTP 응답을 나타내는 객체
     * @param scdto HTML에서 전달되는 검색조건을 받기 위한 객체
     * @history
     *     2020.02.12 최초생성
     */
    @RequestMapping(value = "/salesListExcelDownload")
    public void ExcelDownload(HttpServletResponse response, SearchConditionDto scdto) throws Exception {
        DownloadExcel(response, scdto);
    }

    /**
     * 매출계산서 목록 엑셀 업로드 팝업창 열기
     * @history
     *     2020.02.12 최초생성
     */
    @GetMapping("/salesListUpload")
    public String salesListUpload() {
        return "/admin/adminSalesListUploadSP";
    }

    /**
     * 매출계산서 정보 수정 팝업창 열기
     * @param model 조회한 리스트를 view로 전달하기 위한 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @history
     *     2020.02.12 최초생성
     */
    @GetMapping("/updateInfo")
    public String updateInfo(Model model, HttpServletRequest request) {
        String approveNum = request.getParameter("approveNum");
        StatisticEntity statis = salesService.findCollectionMoney(approveNum);

        model.addAttribute("statis", statis);
        return "/admin/adminCollectionMoneyRegSP";
    }

    /**
     * 매출계산서 정보 수정
     * @param model 조회한 값을 view로 전달하기 위한 객체
     * @param statisticEntity HTML에서 전달되는 값을 받기 위한 객체
     * @history
     *     2020.02.12 최초생성
     */
    @RequestMapping("/updateCollectionMoney")
    public String updateCollectionMoney(Model model, StatisticEntity statisticEntity) {
        StatisticEntity statis = salesService.findCollectionMoney(statisticEntity.getApproveNum());
        statis.setCollectionMoneyDt(statisticEntity.getCollectionMoneyDt());
        statis.setCollectionMoneyYN(statisticEntity.getCollectionMoneyYN());
        salesService.saveSalesInfo(statis);
        StatisticEntity statis2 = salesService.findCollectionMoney(statis.getApproveNum());

        model.addAttribute("statis", statis2);
        return "/admin/adminCollectionMoneyRegSP";
    }

    /**
     * 매출계산서 목록 엑셀 업로드
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @history
     *     2020.02.12 최초생성
     */
    @ResponseBody
    @RequestMapping(value = "/salesListExcelUpload")
    public String ExcelUpload(MultipartHttpServletRequest request) throws Exception {
        String msg = "업로드 성공";
        try {
            Iterator<String> iterator = request.getFileNames();
            MultipartFile file = null;
            while (iterator.hasNext()) {
                file = request.getFile(iterator.next());
                String originName = null;
                originName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");
                UploadExcel(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 매출계산서 목록 엑셀 업로드 실행
     * @param file HTML에서 전달되는 파일을 읽기 위한 객체
     * @history
     *     2020.02.12 최초생성
     *     2020.02.19 데이터 타입 변경
     */
    public void UploadExcel(MultipartFile file) {
        StatisticEntity statisticEntity = new StatisticEntity();
        String str = "";
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIdx = 3; rowIdx < sheet.getLastRowNum() + 1; rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                Cell cell = row.getCell(1);
                if (row != null && cell != null) {
                    cell = row.getCell(0);
                    str = cell.getStringCellValue();
                    if (!str.equals("") && str != null) {
                        statisticEntity.setRegDt(LocalDate.parse(cell.getStringCellValue()));
                    }
                    cell = row.getCell(1);
                    statisticEntity.setApproveNum(cell.getStringCellValue());
                    cell = row.getCell(2);
                    str = cell.getStringCellValue();
                    if (!str.equals("") && str != null) {
                        statisticEntity.setIssueDt(LocalDate.parse(cell.getStringCellValue()));
                    }
                    cell = row.getCell(3);
                    str = cell.getStringCellValue();
                    if (!str.equals("") && str != null) {
                        statisticEntity.setSendDt(LocalDate.parse(cell.getStringCellValue()));
                    }
                    cell = row.getCell(4);
                    str = cell.getStringCellValue();
                    statisticEntity.setBusinessNum(cell.getStringCellValue());
                    if (str.equals("862-86-00042")) {
                        statisticEntity.setSalePurchaseClassification("S");
                    } else {
                        statisticEntity.setSalePurchaseClassification("P");
                    }
                    cell = row.getCell(5);
                    statisticEntity.setSubBusinessNum(cell.getStringCellValue());
                    cell = row.getCell(6);
                    statisticEntity.setCompanyNM(cell.getStringCellValue());
                    cell = row.getCell(7);
                    statisticEntity.setCeoName(cell.getStringCellValue());
                    cell = row.getCell(8);
                    statisticEntity.setAddress(cell.getStringCellValue());
                    cell = row.getCell(9);
                    statisticEntity.setRecipientBusinessNum(cell.getStringCellValue());
                    cell = row.getCell(10);
                    statisticEntity.setRecipientSubBusinessNum(cell.getStringCellValue());
                    cell = row.getCell(11);
                    statisticEntity.setRecipientCompanyNM(cell.getStringCellValue());
                    cell = row.getCell(12);
                    statisticEntity.setRecipientCeoNM(cell.getStringCellValue());
                    cell = row.getCell(13);
                    statisticEntity.setRecipientAddress(cell.getStringCellValue());
                    cell = row.getCell(14);
                    statisticEntity.setTotalPrice(getCellValue(cell));
                    cell = row.getCell(15);
                    statisticEntity.setSupplyPrice(getCellValue(cell));
                    cell = row.getCell(16);
                    statisticEntity.setTaxAmount(getCellValue(cell));
                    cell = row.getCell(17);
                    statisticEntity.setInvoiceClassify(cell.getStringCellValue());
                    cell = row.getCell(18);
                    statisticEntity.setInvoiceType(cell.getStringCellValue());
                    cell = row.getCell(19);
                    statisticEntity.setIssueType(cell.getStringCellValue());
                    cell = row.getCell(20);
                    statisticEntity.setPriceNote(cell.getStringCellValue());
                    cell = row.getCell(21);
                    statisticEntity.setClassfication(cell.getStringCellValue());
                    cell = row.getCell(22);
                    statisticEntity.setEmail(cell.getStringCellValue());
                    cell = row.getCell(23);
                    statisticEntity.setRecipientEmail1(cell.getStringCellValue());
                    cell = row.getCell(24);
                    statisticEntity.setRecipientEmail2(cell.getStringCellValue());
                    cell = row.getCell(25);
                    str = cell.getStringCellValue();
                    if (!str.equals("") && str != null) {
                        statisticEntity.setItemDT(LocalDate.parse(cell.getStringCellValue()));
                    }
                    cell = row.getCell(26);
                    statisticEntity.setItemNM(cell.getStringCellValue());
                    cell = row.getCell(27);
                    statisticEntity.setItemSpec(cell.getStringCellValue());
                    cell = row.getCell(28);
                    statisticEntity.setItemQuantity(cell.getStringCellValue());
                    cell = row.getCell(29);
                    statisticEntity.setItemUnitPrice(getCellValue(cell));
                    cell = row.getCell(30);
                    statisticEntity.setItemSupplyPrice(getCellValue(cell));
                    cell = row.getCell(31);
                    statisticEntity.setItemTaxPrice(getCellValue(cell));
                    cell = row.getCell(32);
                    statisticEntity.setItemNote(cell.getStringCellValue());
                    cell = row.getCell(35);
                    statisticEntity.setNote(cell.getStringCellValue());
                    salesService.createSalesInfo(statisticEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cell 값을 entity타입(int)에 맞게 변경
     * @param cell 확인하려는 갑이 저장되어있는 객체
     * @history
     *     2020.02.19 최초생성
     */
    public int getCellValue(Cell cell){
        int num = 0;
        CellType cellType = cell.getCellType();

        if (cellType.equals(CellType.STRING)) {
            String str = cell.getStringCellValue();
            if(!str.equals("") && str != null){
                num = Integer.parseInt(str.replace(",",""));
            }
        } else if (cellType.equals(CellType.NUMERIC)) {
            num = (int)cell.getNumericCellValue();
        }
        return num;
    }

    /**
     * 매출계산서 목록 엑셀 다운로드 실행
     * @param response 요청에 대한 HTTP 응답을 나타내는 객체
     * @param scdto HTML에서 전달되는 검색조건을 받기 위한 객체
     * @history
     *     2020.02.12 최초생성
     */
    public void DownloadExcel(HttpServletResponse response, SearchConditionDto scdto) {
        Workbook workbook = new XSSFWorkbook();
        int page = 1;
        int limit = 10;
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(currentDate.getTime());
        Sheet sheet = workbook.createSheet("매출계산서");
        String fileName = "매출계산서 목록_"+today+".xlsx";

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        cellStyle.setFont(font);

        Page<StatisticEntity> salesList = salesService.findSalesList(page - 1, limit, scdto);
        StringBuffer stb = new StringBuffer();
        stb.append("작성일자,승인번호,발급일자,전송일자,공급자사업자등록번호,총사업장번호,상호,대표자명,주소,공급받는자사업자등록번호,");
        stb.append("총사업장번호,상호,대표자명,주소,합계금액,공급가액,세액,전자세금계산서분류,전자세금계산서종류,발급유형,");
        stb.append("비고,영수/청구 구분,공급자 이메일,공급받는자 이메일1,공급받는자 이메일2,품목일자,품목명,품목규격,품목수량,");
        stb.append("품목단가,품목공급가액,품목세액,품목비고,수금여부,수금일자,지급여부,지급일,비고,매출/매입 구분");
        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        String[] cellHead = stb.toString().split(",");
        for (int cellIdx = 0; cellIdx < cellHead.length; cellIdx++) {
            cell = row.createCell(cellIdx);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cellHead[cellIdx]);
        }
        for (int pageIdx = 0; pageIdx < salesList.getTotalPages(); pageIdx++) {
            Page<StatisticEntity> salesTotList = salesService.findSalesList(pageIdx, limit, scdto);
            for (int listNum = 0; listNum < salesTotList.getNumberOfElements(); listNum++) {
                StatisticEntity statis = salesTotList.getContent().get(listNum);
                row = sheet.createRow(pageIdx * 10 + listNum + 3);
                cell = row.createCell(0);
                cell.setCellValue(String.valueOf(statis.getRegDt()));
                cell = row.createCell(1);
                cell.setCellValue(statis.getApproveNum());
                cell = row.createCell(2);
                cell.setCellValue(String.valueOf(statis.getIssueDt()));
                cell = row.createCell(3);
                cell.setCellValue(String.valueOf(statis.getSendDt()));
                cell = row.createCell(4);
                cell.setCellValue(statis.getBusinessNum());
                cell = row.createCell(5);
                cell.setCellValue(statis.getSubBusinessNum());
                cell = row.createCell(6);
                cell.setCellValue(statis.getCompanyNM());
                cell = row.createCell(7);
                cell.setCellValue(statis.getCeoName());
                cell = row.createCell(8);
                cell.setCellValue(statis.getAddress());
                cell = row.createCell(9);
                cell.setCellValue(statis.getRecipientBusinessNum());
                cell = row.createCell(10);
                cell.setCellValue(statis.getRecipientSubBusinessNum());
                cell = row.createCell(11);
                cell.setCellValue(statis.getRecipientCompanyNM());
                cell = row.createCell(12);
                cell.setCellValue(statis.getRecipientCeoNM());
                cell = row.createCell(13);
                cell.setCellValue(statis.getRecipientAddress());
                cell = row.createCell(14);
                cell.setCellValue(statis.getTotalPrice());
                cell = row.createCell(15);
                cell.setCellValue(statis.getSupplyPrice());
                cell = row.createCell(16);
                cell.setCellValue(statis.getTaxAmount());
                cell = row.createCell(17);
                cell.setCellValue(statis.getInvoiceClassify());
                cell = row.createCell(18);
                cell.setCellValue(statis.getInvoiceType());
                cell = row.createCell(19);
                cell.setCellValue(statis.getIssueType());
                cell = row.createCell(20);
                cell.setCellValue(statis.getPriceNote());
                cell = row.createCell(21);
                cell.setCellValue(statis.getClassfication());
                cell = row.createCell(22);
                cell.setCellValue(statis.getEmail());
                cell = row.createCell(23);
                cell.setCellValue(statis.getRecipientEmail1());
                cell = row.createCell(24);
                cell.setCellValue(statis.getRecipientEmail2());
                cell = row.createCell(25);
                cell.setCellValue(String.valueOf(statis.getItemDT()));
                cell = row.createCell(26);
                cell.setCellValue(statis.getItemNM());
                cell = row.createCell(27);
                cell.setCellValue(statis.getItemSpec());
                cell = row.createCell(28);
                cell.setCellValue(statis.getItemQuantity());
                cell = row.createCell(29);
                cell.setCellValue(statis.getItemUnitPrice());
                cell = row.createCell(30);
                cell.setCellValue(statis.getItemSupplyPrice());
                cell = row.createCell(31);
                cell.setCellValue(statis.getItemTaxPrice());
                cell = row.createCell(32);
                cell.setCellValue(statis.getItemNote());
                cell = row.createCell(33);
                cell.setCellValue(statis.getCollectionMoneyYN());
                cell = row.createCell(34);
                cell.setCellValue(String.valueOf(statis.getCollectionMoneyDt()));
                cell = row.createCell(35);
                cell.setCellValue(statis.getPaymentYN());
                cell = row.createCell(36);
                cell.setCellValue(String.valueOf(statis.getPaymentDt()));
                cell = row.createCell(37);
                cell.setCellValue(statis.getNote());
                cell = row.createCell(38);
                cell.setCellValue(statis.getSalePurchaseClassification());
            }
        }
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
