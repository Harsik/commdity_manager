package com.archivsoft.controller;

import com.archivsoft.domain.entity.UserinfoEntity;
import com.archivsoft.dto.UserinfoDto;
import com.archivsoft.service.UserinfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 클래스명 - UserinfoController
 * 유저 정보 관련 페이지 유저의 등록 및 삭제 등의 작업을 처리
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class UserinfoController {

    /**
     * 메소드 호출을 위한 service 선언
     */
    private UserinfoService userService;

    /**
     * 유저 목록 조회 페이지로 이동하는 메소드
     *
     * @param request
     * @param model
     * @param pageable
     * @return url
     * @history
     *      2020 02 18 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @GetMapping("/userinfoList")
    public String getUserList(HttpServletRequest request, Model model, Pageable pageable) {

        int page = 1;

        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        Page<UserinfoEntity> UserList = userService.getPageList(pageable);

        model.addAttribute("nowPage", page);
        model.addAttribute("userList", UserList);

        return "admin/adminUserListFM";
    }

    /**
     * 유저 등록 페이지로 이동하는 메소드
     *
     * @return url
     * @history
     *      2020 02 18 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @GetMapping("/userinfoReg")
    public String boardRegForm() {
        return "admin/adminUserRegSM";
    }

    /**
     * 유저 정보 수정 및 조회 페이지로 이동하는 메소드
     *
     * @param no    - 조회할 유저 id
     * @param model
     * @return url
     * @history
     *      2020 02 18 최초 생성
     *      2020 02 21 return 경로 수정
     */
    @RequestMapping(value = "/userDetail/{no}")
    public String getUserDetail(@PathVariable("no") Long no, Model model) {

        UserinfoDto userinfoDto = userService.getUserInfo(no);
        model.addAttribute("userinfoDto", userinfoDto);

        return "admin/adminUserDetailCM";
    }

    /**
     * 유저 등록 및 수정을 처리하는 메소드
     *
     * @param userinfoDto - 데이터를 담고 있는 객체
     * @return url
     * @history 2020 02 18 최초 생성
     */
    @PostMapping("/userWrite")
    public String saveUser(UserinfoDto userinfoDto) {
        userService.saveUser(userinfoDto);
        return "redirect:/admin/userinfoList";
    }

    /**
     * 유저 삭제를 처리하는 메소드
     *
     * @param no - 삭제할 유저 id
     * @return url
     * @history 2020 02 18 최초 생성
     */
    @RequestMapping(value = "/userDelete/{no}")
    public String deleteUser(@PathVariable("no") Long no) {
        userService.deleteUser(no);
        return "redirect:/admin/userinfoList";
    }


}
