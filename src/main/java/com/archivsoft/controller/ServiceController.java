package com.archivsoft.controller;

import com.archivsoft.domain.entity.CategoryEntity;
import com.archivsoft.dto.ServiceDto;
import com.archivsoft.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 클래스명 - ServiceController
 * 서비스 관련 페이지 이동 및 서비스의 등록 및 삭제 등의 작업을 처리
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Controller
@AllArgsConstructor
public class ServiceController {

    private ServiceService serService;

    /**
     * 최초에 서비스 목록 페이지로 이동시켜주는 메소드
     *
     * @return url
     * @history 2020 02 18 최초 생성
     */
    @GetMapping("/serviceList")
    public String defaultPage() {
        return "redirect:/serviceListIndex?isAll=false";
    }

    /**
     * 서비스 목록을 조회에 화면에 등록하는 메소드
     *
     * @param isAll    - 전체를 조회할지 말지를 판별
     * @param model
     * @param pageable
     * @param request
     * @return url
     * @history
     *      2020 02 18 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @GetMapping("/serviceListIndex")
    public String getServiceList(@RequestParam("isAll") boolean isAll, Model model, Pageable pageable, HttpServletRequest request) {

        int page = 1;

        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        Page<CategoryEntity> serviceList;

        if (!isAll) {
            serviceList = serService.getServiceList(pageable);
            model.addAttribute("serviceList", serviceList);
            model.addAttribute("isAll", isAll);
        } else {
            serviceList = serService.getAllServiceList(pageable);
            model.addAttribute("serviceList", serviceList);
            model.addAttribute("isAll", isAll);
        }

        model.addAttribute("nowPage", page);

        return "admin/adminServiceListCP";
    }

    /**
     * 서비스 등록 페이지로 이동하는 메소드
     *
     * @return url
     * @history
     *      2020 02 18 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @GetMapping("/serviceRegForm")
    public String serviceRegForm() {
        return "admin/adminServiceRegSM";
    }

    /**
     * 등록 페이지에서 구분을 선택하는 select 에 넣기 위한 구분들을 가져오는 메소드, 수정 모달창에서도 사용
     *
     * @return List
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/getCateSection")
    public List<CategoryEntity> findCateSectionList() {
        return serService.findCateSectionList();
    }

    /**
     * 등록 페이지에서 지급코드를 선택하는 select 에 넣기 위한 지급코드들을 가져오는 메소드, 수정 모달창에서도 사용
     *
     * @return List
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/getCateCredit")
    public List<CategoryEntity> findCateCreditList() {
        return serService.findCateCreditList();
    }

    /**
     * 성격 수정 모달창에서 데이터를 불러오는 메소드
     *
     * @return List
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/getCateNature")
    public List<CategoryEntity> findCateNatureList() {
        return serService.findCateNatureList();
    }

    /**
     * 계정과목 수정 모달창에서 데이터를 불러오는 메소드
     *
     * @return list
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @GetMapping("/getCateSubject")
    public List<CategoryEntity> findCateSubjectList() {
        return serService.findCateSubjectList();
    }

    /**
     * 계정과목 입력 줄 구분 선택 시 해당 구분에 등록되어 있는 성격 리스트 가져옴
     *
     * @param cateSection - 성격을 불러올 구분
     * @return list
     * @history 최초 생성
     */
    @ResponseBody
    @GetMapping("/getCateNatureList")
    public List<CategoryEntity> getCategoryMiddleList(@RequestParam("cateSection") String cateSection) {
        return serService.findCateNatureList(cateSection);
    }

    /**
     * 구분 등록 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 등록할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateSectionReg")
    public Boolean createCateSection(@RequestBody ServiceDto serviceDto) {
        return serService.createCateSection(serviceDto);
    }

    /**
     * 지급코드 등록 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 등록할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateCreditReg")
    public Boolean createCateCredit(@RequestBody ServiceDto serviceDto) {
        return serService.createCateCredit(serviceDto);
    }

    /**
     * 성격 등록 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 등록할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateNatureReg")
    public Boolean createCateNature(@RequestBody ServiceDto serviceDto) {
        return serService.createCateNature(serviceDto);
    }

    /**
     * 계정과목 등록 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 등록할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateSubjectReg")
    public Boolean createCateSubject(@RequestBody ServiceDto serviceDto) {
        return serService.createCateSubject(serviceDto);
    }

    /**
     * 구분 수정 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 수정할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateSectionModify")
    public Boolean saveCateSection(@RequestBody ServiceDto serviceDto) {
        return serService.saveCateSection(serviceDto);
    }


    /**
     * 지급코드 수정 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 수정할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateCreditModify")
    public Boolean saveCateCredit(@RequestBody ServiceDto serviceDto) {
        return serService.saveCateCredit(serviceDto);
    }

    /**
     * 성격 수정 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 수정할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateNatureModify")
    public Boolean saveCateNature(@RequestBody ServiceDto serviceDto) {
        return serService.saveCateNature(serviceDto);
    }

    /**
     * 계정과목 수정 과정에서 해당 값의 중복 여부를 리턴하는 메소드
     *
     * @param serviceDto - 수정할 데이터가 담겨 있는 객체
     * @return boolean
     * @history 2020 02 18 최초 생성
     */
    @ResponseBody
    @PostMapping("/cateSubjectModify")
    public Boolean saveCateSubject(@RequestBody ServiceDto serviceDto) {
        return serService.saveCateSubject(serviceDto);
    }

    /**
     * 데이터 삭제 메소드
     *
     * @param id    - 삭제할 데이터의 id
     * @param value - 삭제할 데이터의 종류 판별
     * @return url
     * @history 2020 02 18 최초 생성
     */
    @GetMapping("/cateDeleteService/{value}")
    public String deleteCateService(@RequestParam(value = "cateIdx") Long id, @PathVariable("value") String value) {

        switch (value) {
            case "section":
                serService.deleteCateSection(id);
                break;
            case "credit":
                serService.deleteCateCredit(id);
                break;
            case "nature":
                serService.deleteCateNature(id);
                break;
            case "subject":
                serService.deleteCateSubject(id);
                break;
        }
        return "redirect:/serviceList";
    }
}