/**
 * ========================================================
 *
 * @FileName : ExcelReader.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.excel.component;

import com.archivsoft.excel.constant.ExcelConstant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 업로드 된 엑셀 파일을 특정 양식의 객체로 만듬
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Component
public class ExcelReader {

    /**
     * 업로드한 엑셀 파일을 받고 리스트로 출력하는 메소드
     *
     * @param multipartFile
     * @param rowFunc
     * @history 2020 02 19 최초 생성
     */
    public <T> List<T> readFileToList(MultipartFile multipartFile,
                                      Function<Row, T> rowFunc) throws IOException, InvalidFormatException {

        Workbook workbook = readWorkbook(multipartFile);
        Sheet sheet = workbook.getSheetAt(1);
        int rowCount = sheet.getPhysicalNumberOfRows();
        return IntStream
                .range(5, rowCount)
                .mapToObj(rowIndex -> rowFunc.apply(sheet.getRow(rowIndex)))
                .collect(Collectors.toList());
    }

    /**
     * 업로드한 엑셀 파일의 확장자를 확인하고 적절한 워크북 시트를 리턴함
     *
     * @param multipartFile
     * @history 2020 02 19 최초 생성
     */
    private Workbook readWorkbook(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        verifyFileExtension(multipartFile);
        return multipartFileToWorkbook(multipartFile);
    }

    /**
     * 업로드한 엑셀 파일의 확장자를 확인하고 올바른 엑셀 파일이 아닌지를 판별
     *
     * @param multipartFile
     * @history 2020 02 19 최초 생성
     */
    private void verifyFileExtension(MultipartFile multipartFile) throws InvalidFormatException {
        final String originalFilename = multipartFile.getOriginalFilename();
        if (!isExcelExtension(originalFilename)) {
            throw new InvalidFormatException("This file extension is not verify");
        }
    }

    /**
     * 업로드한 엑셀 파일의 확장자명을 리턴함
     *
     * @param fileName
     * @history 2020 02 19 최초 생성
     */
    private boolean isExcelExtension(String fileName) {
        return fileName.endsWith(ExcelConstant.XLS) || fileName.endsWith(ExcelConstant.XLSX);
    }

    /**
     * 파일명에서 xls 확장자를 가지고 있는지 확인함
     *
     * @param fileName
     * @history 2020 02 19 최초 생성
     */
    private boolean isExcelXls(String fileName) {
        return fileName.endsWith(ExcelConstant.XLS);
    }

    /**
     * 파일명에서 xlsx 확장자를 가지고 있는지 확인함
     *
     * @param fileName
     * @history 2020 02 19 최초 생성
     */
    private boolean isExcelXlsx(String fileName) {
        return fileName.endsWith(ExcelConstant.XLSX);
    }

    /**
     * 업로드한 엑셀 파일을 받고 확장자를 확인 후 적합한 워크북 객체를 리턴함
     *
     * @param multipartFile
     * @history 2020 02 19 최초 생성
     */
    private Workbook multipartFileToWorkbook(MultipartFile multipartFile)
            throws IOException {
        if (isExcelXls(multipartFile.getOriginalFilename())) {
            return new HSSFWorkbook(multipartFile.getInputStream());
        } else if (isExcelXlsx(multipartFile.getOriginalFilename())) {
            return new XSSFWorkbook(multipartFile.getInputStream());
        } else {
            return new HSSFWorkbook(multipartFile.getInputStream());
        }
    }
}