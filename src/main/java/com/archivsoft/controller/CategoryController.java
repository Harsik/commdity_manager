/**
 * ========================================================
 *
 * @FileName : CategoryController.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */

package com.archivsoft.controller;


import com.archivsoft.domain.entity.CategoryEntity;
import com.archivsoft.dto.CategoryDto;
import com.archivsoft.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 카테고리 리스트를 출력하고 제조사, 제품명, 모델명 카테고리를 조회, 수정, 삭제 요청에 응답한다.
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class CategoryController {

    /**
     * CategoryService를 사용하기 위한 변수 선언
     */
    private CategoryService cateService;

    /**
     * 페이지 경로를 가지지 않은 주소로 접근시 경로를 가지도록 리다이렉트 하기 위한 메소드
     *
     * @param model
     * @return "redirect:/cateList/1?isAll=false"
     * @url "/cateList"
     * @history 2020 02 19 최초 생성
     */
    @GetMapping("/cateList")
    public String findCateList(Model model) {
        return "redirect:/admin/cateList/1?isAll=false";
    }

    /**
     * 페이지 값과 전체보기, 일반보기 상태 값에 맞는 리스트를 조회
     *
     * @param page
     * @param isAll
     * @param model
     * @return "admin/adminCategoryListFM"
     * @url "/cateList/{page}"
     * @history 2020 02 18 최초 생성
     */
    @GetMapping("/cateList/{page}")
    public String findCateList(@PathVariable int page, @RequestParam(value = "isAll", defaultValue = "false") Boolean isAll, Model model) {
        model.addAttribute("categoryList", cateService.findCateList(isAll, page - 1));
        model.addAttribute("cateMainList", findCateMainList());
        model.addAttribute("isAll", isAll);

        return "admin/adminCategoryListFM";
    }

    /**
     * 모든 제조사명 카테고리 리스트를 조회
     *
     * @return List<String>
     * @url "/cateMain"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateMain")
    public List<String> findCateMainList() {
        return cateService.findCateMainList();
    }

    /**
     * 모든 제조사 카테고리 리스트를 조회
     *
     * @url "/cateMainList"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateMainList")
    public List<CategoryEntity> findCategoryMainList() {
        return cateService.findCategoryMainList();
    }

    /**
     * 모든 제품명 카테고리 리스트를 조회
     *
     * @url "/cateMidList"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateMidList")
    public List<CategoryEntity> findCategoryMiddleList() {
        return cateService.findCategoryMiddleList();
    }

    /**
     * 모든 모델명 카테고리 리스트를 조회
     *
     * @url "/cateSubList"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateSubList")
    public List<CategoryEntity> findCategorySubList() {
        return cateService.findCategorySubList();
    }

    /**
     * 제조사에 해당하는 제품명 카테고리 리스트를 조회
     *
     * @url "/cateMiddleList"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateMiddleList")
    public List<String> findCategoryMiddleListByCompany(@RequestParam("company") String company) {
        return cateService.findCategoryMiddleListByCompany(company);
    }

    /**
     * 카테고리 아이디에 해당하는 카테고리 정보를 조회
     *
     * @url "/cateDetail"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/cateDetail")
    public CategoryEntity findCategoryDetailById(@RequestParam(value = "Id") Long Id) {
        return cateService.findCategoryDetailById(Id);
    }

    /**
     * 제조사 카테고리를 수정
     *
     * @url "/cateSaveMain"
     * @history 2020 02 18 최초 생성
     */
    @PostMapping("/cateSaveMain")
    public String saveCategoryMain(@ModelAttribute CategoryDto categoryDto) {
        cateService.saveCategoryMain(categoryDto);
        return "redirect:/admin/cateList";
    }

    /**
     * 제품명 카테고리를 수정
     *
     * @url "/cateSaveMid"
     * @history 2020 02 18 최초 생성
     */
    @PostMapping("/cateSaveMid")
    public String saveCategoryMiddle(@ModelAttribute CategoryDto categoryDto) {
        cateService.saveCategoryMiddle(categoryDto);
        return "redirect:/admin/cateList";
    }

    /**
     * 모델명 카테고리를 수정
     *
     * @url "/cateSaveSub"
     * @history 2020 02 18 최초 생성
     */
    @PostMapping("/cateSaveSub")
    public String saveCategorySub(@ModelAttribute CategoryDto categoryDto) {
        cateService.saveCategorySub(categoryDto);
        return "redirect:/admin/cateList";
    }

    /**
     * 아이디에 해당하는 모델명 카테고리를 삭제
     *
     * @url "/cateDelete"
     * @history 2020 02 18 최초 생성
     */
    @GetMapping("/cateDelete")
    public String deleteCategoryByCateIdx(@RequestParam(value = "cateIdx") String Id) {
        cateService.deleteCategoryByCateIdx(Long.parseLong(Id));
        return "redirect:/admin/cateList";
    }

    /**
     * 제조사 카테고리를 등록
     *
     * @url "/cateRegMain"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateRegMain")
    public Boolean isCategoryMain(@RequestBody CategoryDto categoryDto) {
        return cateService.isCategoryMain(categoryDto);
    }

    /**
     * 제품명 카테고리를 등록
     *
     * @url "/cateRegMid"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateRegMid")
    public Boolean isCategoryMiddle(@RequestBody CategoryDto categoryDto) {
        return cateService.isCategoryMiddle(categoryDto);
    }

    /**
     * 모델명 카테고리를 등록
     *
     * @url "/cateRegSub"
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateRegSub")
    public Boolean isCategorySub(@RequestBody CategoryDto categoryDto) {
        return cateService.isCategorySub(categoryDto);
    }
}