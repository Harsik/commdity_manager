package com.archivsoft.controller;

import com.archivsoft.domain.entity.BoardEntity;
import com.archivsoft.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 게시판 리스트를 출력하고 조회, 수정, 삭제 요청에 응답한다.
 *
 * @author 김주찬
 * @version 1.0
 * @since 2020-02-19
 */
@Controller
@AllArgsConstructor
public class MainController {

    private BoardService boardService;

    /**
     * 메인 페이지 게시판 정보 출력 및 검색
     */

    @RequestMapping(value = "/",method = {RequestMethod.POST,RequestMethod.GET})
    public String index(HttpServletRequest request, Model model, Pageable pageable) {
        /*Form 태그를 통해서 검색 조건 데이터 전달 받아 저장(상품명,사용위치,사용자)*/
        /*페이징에 필요한값, 페이징처리 및 담을 BoardEntity 선언 */
        int nowPage = 1;
        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            nowPage = Integer.parseInt(request.getParameter("page"));
        }
        String productItem = request.getParameter("productItem") == null ? "" : request.getParameter("productItem");
        String boardUsePlace = request.getParameter("boardUsePlace") == null ? "" : request.getParameter("boardUsePlace");
        String boardUserName = request.getParameter("boardUserName") == null ? "" : request.getParameter("boardUserName");
        /*검색 조건이 있을경우 해당 검색 조건 기반 게시판정보 출력*/
        if (productItem != "" || boardUsePlace != "" || boardUserName != "") {
            pageable = PageRequest.of(nowPage-1, 10, Sort.by("boardIdx").ascending());
            Page<BoardEntity> getSearchBoardList = boardService.getSearchBoardList(productItem, boardUsePlace, boardUserName, pageable);
            model.addAttribute("boardList", getSearchBoardList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("productItem", productItem);
            model.addAttribute("boardUsePlace", boardUsePlace);
            model.addAttribute("boardUserName", boardUserName);
        } else {
            Page<BoardEntity> boardListPaging = boardService.setBoardListPaging(pageable);
            model.addAttribute("boardList", boardListPaging);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("productItem", productItem);
            model.addAttribute("boardUsePlace", boardUsePlace);
            model.addAttribute("boardUserName", boardUserName);
        }
        return "index";
    }


}
