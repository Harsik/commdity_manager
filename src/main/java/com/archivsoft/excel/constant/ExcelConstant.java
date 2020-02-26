/**
 * ========================================================
 *
 * @FileName : ExcelConstant.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.excel.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 엑셀 작업에 필요한 고정 변수를 제공하기 위한 클래스
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19 최초생성
 */
public abstract class ExcelConstant {

    /**
     * 파일명 변수
     */
    public static final String FILE_NAME = "fileName";

    /**
     * 헤드 변수
     */
    public static final String HEAD = "head";

    /**
     * 바디 변수
     */
    public static final String BODY = "body";

    /**
     * xls 변수
     */
    public static final String XLS = "xls";

    /**
     * xlsx 변수
     */
    public static final String XLSX = "xlsx";

    /**
     * xlsx-stream 변수
     */
    public static final String XLSX_STREAM = "xlsx-stream";

    /**
     * 구입리스트 헤더명 리스트
     */
    public static final List<String> PURCHASEHEAD = Arrays.asList(
            "작성일자", "승인번호", "발급일자", "전송일자", "공급자사업자등록번호"
            , "종사업장번호", "상호", "대표자명", "주소"
            , "공급받는자사업자등록번호", "종사업장번호", "상호", "대표자명"
            , "즈소", "합계금액", "공급가액", "세액", "전자세급계산서분류"
            , "전자세급계산서종류", "발급유형", "비고", "영수/청구 구분"
            , "공급자 이메일", "공급받는자 이메일1", "공급받는자 이메일2"
            , "품목일자", "품목명", "품목규격", "품목수량"
            , "품목단가", "품목공급가액", "품목세액", "품목비고"
    );
}
