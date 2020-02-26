/**
 * ========================================================
 *
 * @FileName : UnpaidInController.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 21.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 21.    홍성관    1.0    최초생성
 * =========================================================
 */

package com.archivsoft.controller;

import com.archivsoft.dto.AccruedInDto;
import com.archivsoft.excel.constant.ExcelConstant;
import com.archivsoft.excel.view.AccruedInExcelXlsView;
import com.archivsoft.excel.view.UnpaidInExcelXlsView;
import com.archivsoft.service.AccruedInService;
import com.archivsoft.service.UnpaidInService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 미지급 현황 리스트를 출력하거나 해당 년도의 엑셀 파일을 다운로드 할 수 있다.
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-25
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class UnpaidInController {

    /**
     * UnpaidInService를 사용하기 위한 변수 선언
     */
    private UnpaidInService unpaidInService;

    /**
     * 페이지 경로를 가지지 않은 주소로 접근시 경로를 가지도록 리다이렉트 하기 위한 메소드
     *
     * @param model
     * @return "redirect:/admin/unpaidInList/1"
     * @url "/unpaidInList"
     * @history 2020 02 25 최초 생성
     */
    @GetMapping("/unpaidInList")
    public String findUnpaidInList(Model model) {
        return "redirect:/admin/unpaidInList/"+ LocalDate.now().getYear();
    }

    /**
     * 해당 년도의 미지급 현황 리스트를 화면에 출력하는 메소드
     *
     * @param model
     * @param year
     * @return "admin/adminUnpaidInListFM"
     * @url "/unpaidInLis/{year}t"
     * @history
     * 2020 02 25 최초 생성
     */
    @GetMapping("/unpaidInList/{year}")
    public String findUnpaidInList(@PathVariable int year, Model model) {
        List<AccruedInDto> unpaidInList = unpaidInService.findUnpaidInList(year);

        model.addAttribute("unpaidInList", unpaidInList);
        model.addAttribute("thisYear", LocalDate.now().getYear());
        model.addAttribute("pathYear", year);

        return "admin/adminUnpaidInListFM";
    }

    /**
     * 해당 년도의 미지급 현황 리스트를 엑셀로 다운로드하는 메소드
     *
     * @param year
     * @param model
     * @history
     * 2020 02 25 최초 생성
     */
    @ResponseBody
    @GetMapping("/unpaidInList/writerExcel/{year}")
    public View getWriterExcel(@PathVariable int year, Map<String,Object> model) {
        model.put(ExcelConstant.FILE_NAME, "unpaidInList");
        model.put(ExcelConstant.BODY, unpaidInService.findUnpaidInList(year));
        return new UnpaidInExcelXlsView();
    }
}
