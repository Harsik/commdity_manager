package com.archivsoft.controller;

import com.archivsoft.domain.entity.BoardEntity;
import com.archivsoft.domain.entity.ProductEntity;
import com.archivsoft.domain.entity.UserinfoEntity;
import com.archivsoft.dto.BoardDto;
import com.archivsoft.dto.getBoardListDto;
import com.archivsoft.service.BoardService;
import com.archivsoft.service.MainService;
import com.archivsoft.service.ProductService;
import com.archivsoft.service.UserinfoService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.hibernate.type.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * 게시판 리스트를 출력하고 조회, 수정, 삭제 요청에 응답한다.
 *
 * @author 김주찬
 * @version 1.0
 * @since 2020-02-19
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class BoardController {

    private BoardService boardService;

    /**
     * 게시판 등록
     */
    @PostMapping("/createBoard")
    public String getBoardReg(@RequestBody BoardEntity boardEntity) {
        /*Ajax로 전달받은 데이터 기반으로 게시판 정보 등록*/
        boardService.saveBoardList(boardEntity);
        return "redirect:/admin/adminBoardReg";
    }

    /**
     * 게시판 업데이트
     */
    @PostMapping("/updateBoard")
    public String updateBoardReg(@RequestBody BoardDto boardDto) {
        /*Ajax로 전달받은 데이터 기반으로 게시판 정보 수정*/
        boardService.updateBoardList(boardDto);
        return "redirect:/admin/adminBoardReg";
    }

    /**
     * 게시판 삭제
     */
    @RequestMapping("/deleteBoard")
    public String deleteBoard(HttpServletRequest request) {
        /*유저아이디값 기반으로 유저정보 삭제*/
        boardService.deleteBoardList(Long.parseLong(request.getParameter("boardUserIdx")));
        return "redirect:/admin/boardList";
    }

    /**
     * 게시판 정보 출력
     */
    @GetMapping("/boardList")
    public String findBoardList(HttpServletRequest request, Model model, Pageable pageable) {
        /*게시판 정보 리스트 출력 및 페이징 처리*/
        int page = 1;

        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        Page<BoardEntity> boardListPaging = boardService.setBoardListPaging(pageable);
        model.addAttribute("nowPage", page);
        model.addAttribute("boardList", boardListPaging);
        return "admin/adminBoardList";
    }

//    /**
//     * 게시판 정보 출력
//     */
//    @GetMapping("/admin/boardList")
//    public String findAdminBoardList(HttpServletRequest request, Model model, Pageable pageable) {
//        /*게시판 정보 리스트 출력 및 페이징 처리*/
//        int page = 1;
//
//        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
//            page = Integer.parseInt(request.getParameter("page"));
//        }
//
//        Page<BoardEntity> boardListPaging = boardService.setBoardListPaging(pageable);
//        model.addAttribute("nowPage", page);
//        model.addAttribute("boardList", boardListPaging);
//        return "/admin/adminBoardList";
//    }

    /**
     * 선택한 유저아이디값 기반으로 상세 유저정보 출력
     */
    @RequestMapping(value = "/getBoardDetail/{boardIdx}")
    public String findBoardDetail(@PathVariable("boardIdx") Long boardIdx, Model model) throws Exception {
        /*유저정보 리스트 와 선택한 게시판 정보 */
        List<UserinfoEntity> getUserList = boardService.getUserList();
        BoardEntity getFindBoardUserIdx = boardService.getFindBoardUserIdx(boardIdx);
        /*사용자가 등록한 상품 리스트*/
        String getProductIdList = boardService.getProductIdxList(getFindBoardUserIdx.getBoardUserIdx());
        /*등록된 상품외에 현재 등록 가능한 상품 리스트*/
        String getOtherProductIdList = boardService.getOtherProductIdxList(getFindBoardUserIdx.getBoardUserIdx());
        /*상세보기 클릭시 등록가능한 상품 리스트만 불러오기*/
        List productList = boardService.getRegistedProductList(getFindBoardUserIdx.getBoardUserIdx());

        model.addAttribute("btnUseYN", "Y");
        model.addAttribute("otherProductIdList", getOtherProductIdList);
        model.addAttribute("boardUserIdx", getFindBoardUserIdx.getBoardUserIdx());
        model.addAttribute("board", getFindBoardUserIdx);
        model.addAttribute("productList", productList);
        model.addAttribute("userInfoList", getUserList);
        model.addAttribute("productIdList", getProductIdList);
        return "admin/adminBoardReg";
    }

    /**
     * 등록 페이지에 필요한 유저정보 상품 정보 리스트 제공
     */
    @GetMapping("/getBoardRegList")
    public String findBoardRegInfo(Model model) {
        /*상품정보,유저정보 리스트 출력*/
        List<ProductEntity> getProductList = boardService.getMianList();
        List<UserinfoEntity> getUserList = boardService.getUserList();
        BoardEntity board = new BoardEntity();
        model.addAttribute("board", board);
        model.addAttribute("productList", getProductList);
        model.addAttribute("userInfoList", getUserList);
        return "admin/adminBoardReg";
    }

}
