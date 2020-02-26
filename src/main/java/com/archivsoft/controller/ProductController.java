
package com.archivsoft.controller;

import com.archivsoft.domain.entity.ProductEntity;
import com.archivsoft.dto.ProductDto;
import com.archivsoft.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 상품 목록  조회, 상품 정보 수정, 등록, 삭제
 * @author 강병수
 * @version 1.0
 * @since 2020.02.03
 */
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class ProductController {

    /**
     * ProductService를 사용하기 위한 변수 선언
     */
    private ProductService proService;

    /**
     * 상품리스트 조회
     * @param model 조회한 리스트를 view로 전달하기 위한 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @return String
     * @history
     *     2020.02.03 최초생성
     *     2020.02.19 페이징 변경
     */
    @GetMapping("/productList")
    public String findProductList(Model model, HttpServletRequest request) {
        int limit = 10;
        int page = 1;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        Page<ProductEntity> productPagingList = proService.findProductList(page - 1, limit);

        model.addAttribute("productList", productPagingList);
        model.addAttribute("pageNum",page);
        return "admin/adminProductListFM";
    }

    /**
     * 상품 상세정보 조회
     * @param model 조회한 리스트를 view로 전달하기 위한 객체
     * @param request HTML에서 전달되는 값을 가져오기 위한 객체
     * @return String
     * @history
     *    2020.02.12 최초생성
     *    2020.02.20 attribute,및 변수명 수정
     */
    @GetMapping("/productReg")
    public String findProductDetail(Model model, HttpServletRequest request) {
        String service = request.getParameter("service");
        List<String> comList = proService.findCateCompanyList();
        String index = request.getParameter("productIdx");
        if (index != null) {
            Long productIdx = Long.parseLong(index);
            if (productIdx != 0) {
                ProductEntity productEntity = proService.findProductInfo(productIdx);
                String company=  productEntity.getProductCompany();
                String item =productEntity.getProductItem();
                List<String> itemList = proService.findCateItemList(company);
                List<String> modelList = proService.findCateModelNameList(company, item);
                model.addAttribute("services", "modify");
                model.addAttribute("proDetail", proService.findProductInfo(productIdx));
                model.addAttribute("itemList",itemList);
                model.addAttribute("modelList",modelList);
            }
        }
        model.addAttribute("service", service);
        model.addAttribute("comList", comList);
        return "admin/adminProductRegC";
    }

    /**
     * 상품 정보 등록,수정,삭제
     * @param service url에서 일부를 변수로 받기 위한 객체
     * @param productEntity HTML에서 전달되는 값을 받기 위한 객체
     * @param redirect 조회한 리스트를 view로 전달하기 위한 객체
     * @return String
     * @history
     *    2020.02.12 최초생성
     *    2020.02.18 detail 삭제
     *    2020.02.20 attribute,및 변수명 수정
     */
    @RequestMapping(value = "/products/{service}")
    public String setProductInfo(@PathVariable("service") String service, ProductEntity productEntity, RedirectAttributes redirect) {
        String url = "redirect:/admin/productReg";
        Long productIdx = null;
        String chk = service;

        switch (service) {
            case "regist":
                productIdx = proService.createProductInfo(productEntity);
                service = "modify";
                break;

            case "modify":
                productIdx = proService.saveProductInfo(productEntity);
                service = "modify";
                break;

            case "delete":
                productIdx = productEntity.getProductIdx();
                proService.deleteProductInfo(productIdx);
                url = "redirect:/admin/productList";
                break;

            case "cancel":
                productIdx = productEntity.getProductIdx();
                service = "modify";
                break;
        }

        if (!chk.equals("delete")) {
            productEntity = proService.findProductInfo(productIdx);
            String company=  productEntity.getProductCompany();
            String item =productEntity.getProductItem();
            List<String> comList = proService.findCateCompanyList();
            List<String> itemList = proService.findCateItemList(company);
            List<String> modelList = proService.findCateModelNameList(company, item);

            redirect.addFlashAttribute("comList",comList);
            redirect.addFlashAttribute("itemList",itemList);
            redirect.addFlashAttribute("modelList",modelList);
            redirect.addFlashAttribute("proDetail", productEntity);
            redirect.addFlashAttribute("services", service);
        }
        return url;
    }

    /**
     * 상품 정보 등록 및 수정시 필요한 selectBox 값을 설정
     * @param productDto HTML에서 전달되는 값을 받기 위한 객체
     * @return ModelAndView
     * @history
     *    2020.02.12 최초생성
     *    2020.02.20 변수명 변경
     */
    @PostMapping("/product/selectBoxSetting")
    public ModelAndView setSelectCategory(@RequestBody ProductDto productDto) throws Exception {
        ModelAndView mav = new ModelAndView();
        String company = productDto.getProductCompany();
        String item = productDto.getProductItem();
        List<String> comList = proService.findCateCompanyList();
        List<String> itemList = proService.findCateItemList(company);

        if (item != null) {
            List<String> modelList = proService.findCateModelNameList(company, item);

            mav.addObject("modelList", modelList);
        }
        mav.setViewName("jsonView");
        mav.addObject("comList", comList);
        mav.addObject("itemList", itemList);
        return mav;
    }

}