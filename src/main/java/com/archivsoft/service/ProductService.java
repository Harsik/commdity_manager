package com.archivsoft.service;

import com.archivsoft.domain.entity.CategoryEntity;
import com.archivsoft.domain.entity.ProductEntity;
import com.archivsoft.domain.repository.CategoryRepository;
import com.archivsoft.domain.repository.ProductRepository;
import com.archivsoft.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 상품 목록을 출력하고 조회, 등록, 수정, 삭제한다.
 * @author 강병수
 * @version 1.0
 * @since 2020.02.03
 */
@Service
@AllArgsConstructor
public class ProductService {

    /**
     * ProductRepository를 사용하기 위한 변수 선언
     */
    private ProductRepository proRepository;
    /**
     * CategoryRepository를 사용하기 위한 변수 선언
     */
    private CategoryRepository categoryRepository;

    /**
     * 상품 정보 등록
     * @param productEntity 등록할 데이터를 담고있는 객체
     * @history
     *     2020.02.03 최초생성
     */
    public Long createProductInfo(ProductEntity productEntity) {
        return proRepository.save(productEntity).getProductIdx();
    }

    /**
     * 상품 정보 수정
     * @param productEntity 수정할 데이터를 담고있는 객체
     * @history
     *     2020.02.03 최초생성
     */
    public Long saveProductInfo(ProductEntity productEntity) {
        return proRepository.save(productEntity).getProductIdx();
    }

    /**
     * 상품 정보 삭제
     * @param productIdx 삭제할 데이터의 키값
     * @history
     *     2020.02.03 최초생성
     */
    public void deleteProductInfo(Long productIdx) {
        proRepository.deleteById(productIdx);
    }

    /**
     * 상품 상세정보 조회
     * @param productIdx 조회할 데이터의 키값
     * @history
     *     2020.02.03 최초생성
     */
    public ProductEntity findProductInfo(Long productIdx) {
        return proRepository.findByProductIdx(productIdx);
    }

    /**
     * 상품 목록 조회
     * @param page 페이징 처리 후 가져올 목록의 페이지
     * @param limit 페이징 처리 시 한 페이지에 저장할 목록의 최댓값
     * @history
     *     2020.02.03 최초생성
     *     2020.02.20 정렬순서 변경
     */
    @Transactional
    public Page<ProductEntity> findProductList(int page, int limit) {
        return proRepository.findAllByOrderByRegdtAsc(PageRequest.of(page, limit));
    }

    /**
     * 회사 카테고리 목록 조회
     * @history
     *     2020.02.03 최초생성
     */
    @Transactional
    public List<String> findCateCompanyList() {
        List<String> companyNameList = new ArrayList<String>();
        List<CategoryEntity> cateCompanyList = categoryRepository.findAllByCateCompanyNotNullAndCateItemIsNullAndCateModelNameIsNullAndCateType("P");
        for (CategoryEntity categoryEntity : cateCompanyList) {
            companyNameList.add(categoryEntity.getCateCompany());
        }
        return companyNameList;
    }

    /**
     * 품목 카테고리 목록 조회
     * @param company 목록을 조회하기 위한 키값
     * @history
     *     2020.02.03 최초생성
     */
    @Transactional
    public List<String> findCateItemList(String company) {
        List<String> itemNameList = new ArrayList<String>();
        List<CategoryEntity> cateItemList = categoryRepository.findAllByCateCompanyAndCateItemNotNullAndCateModelNameIsNullAndCateType(company,"P");
        for (CategoryEntity categoryEntity : cateItemList) {
            itemNameList.add(categoryEntity.getCateItem());
        }
        return itemNameList;
    }

    /**
     * 모델명 카테고리 목록 조회
     * @param company 목록을 조회하기 위한 키값
     * @param item  목록을 조회하기 위한 키값
     * @history
     *     2020.02.03 최초생성
     */
    @Transactional
    public List<String> findCateModelNameList(String company, String item) {
        List<String> modelNameList = new ArrayList<String>();
        List<CategoryEntity> cateModelNameList = categoryRepository.findAllByCateCompanyAndCateItemAndCateModelNameNotNullAndCateType(company, item,"P");
        for (CategoryEntity categoryEntity : cateModelNameList) {
            modelNameList.add(categoryEntity.getCateModelName());
        }
        return modelNameList;
    }
}