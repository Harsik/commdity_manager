package com.archivsoft.controller;

import com.archivsoft.dto.MemberDto;
import com.archivsoft.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    // 로그인 페이지
    @GetMapping("/user/login")
    public String loginPage() {
//        return "/admin/adminBoardList";
        return "/admin/login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/applyloginPage")
    public String applyAdminPage() {
        return "/admin/loginPage";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "/";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "/index";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MemberDto memberDto) {

        InetAddress local;

        try{
            local = InetAddress.getLocalHost();
            String ip = local.getHostAddress();
            memberDto.setIp(ip);

            // Super Admin = 0, Basic Admin = 1
            memberDto.setGrade("1");
            memberDto.setApproveYn("N");
            memberDto.setUseYn("Y");
        } catch (UnknownHostException e){
            e.printStackTrace();
        }

        memberService.joinUser(memberDto);
        return "redirect:/user/login";
    }


}
