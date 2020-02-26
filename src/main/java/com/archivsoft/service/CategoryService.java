/**
 * ========================================================
 *
 * @FileName : CategoryService.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.service;

import com.archivsoft.domain.entity.CategoryEntity;
import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.CategoryRepository;
import com.archivsoft.dto.CategoryDto;
import com.archivsoft.dto.PurchaseRequest;
import com.archivsoft.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

/**
 * 카테고리 리스트를 출력하고 제조사, 제품명, 모델명 카테고리를 조회, 수정, 삭제하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Service
@AllArgsConstructor
public class CategoryService {

    /**
     * CategoryRepository를 사용하기 위한 변수 선언
     */
    private CategoryRepository cateRepository;

    /**
     * 페이지 값과 전체보기, 일반보기 상태 값에 맞는 리스트를 조회
     *
     * @param isAll
     * @param page
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<CategoryEntity> findCateList(Boolean isAll, int page) {
        int size = 5;
        Page<CategoryEntity> pageList;

        if (isAll) {
            pageList = findPurchaseList(page, size);
        } else {
            pageList = findCateModelNamePurchaseList(page, size);
        }

        return pageList;
    }

    /**
     * 모든 제조사 카테고리 리스트를 조회
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<String> findCateMainList() {
        List<CategoryEntity> categoryEntities = findCategoryMainList();
        List<String> mainList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            mainList.add(categoryEntity.getCateCompany());
        }
        return mainList;
    }

    /**
     * 제조사에 해당하는 제품명 카테고리 리스트를 조회
     *
     * @param company
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<String> findCategoryMiddleListByCompany(String company) {
        List<CategoryEntity> cateEntities = findCateMidiListByCompany(company);
        List<String> cateMiddleList = new ArrayList<>();

        for (CategoryEntity categoryEntity : cateEntities) {
            cateMiddleList.add(categoryEntity.getCateItem());
        }

        return cateMiddleList;
    }

    /**
     * 카테고리 아이디에 해당하는 카테고리 정보를 조회
     *
     * @param Id
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public CategoryEntity findCategoryDetailById(Long Id) {
        return cateRepository.findById(Id).orElseThrow(() -> new NotFoundException("Category not found with id : " + Id));
    }

    /**
     * 제조사 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public void saveCategoryMain(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = cateRepository.findById(categoryDto.getCateIdx()).orElseThrow(() -> new NotFoundException("Category not found with id : " + categoryDto.getCateIdx()));
        categoryEntity.setCateCompany(categoryDto.getCateCompany());
        cateRepository.save(categoryEntity);
    }

    /**
     * 제품명 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public void saveCategoryMiddle(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = cateRepository.findById(categoryDto.getCateIdx()).orElseThrow(() -> new NotFoundException("Category not found with id : " + categoryDto.getCateIdx()));
        categoryEntity.setCateCompany(categoryDto.getCateCompany());
        categoryEntity.setCateItem(categoryDto.getCateItem());
        cateRepository.save(categoryEntity);
    }

    /**
     * 모델명 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public void saveCategorySub(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = cateRepository.findById(categoryDto.getCateIdx()).orElseThrow(() -> new NotFoundException("Category not found with id : " + categoryDto.getCateIdx()));
        categoryEntity.setCateCompany(categoryDto.getCateCompany());
        categoryEntity.setCateItem(categoryDto.getCateItem());
        categoryEntity.setCateModelName(categoryDto.getCateModelName());
        cateRepository.save(categoryEntity);
    }

    /**
     * 아이디에 해당하는 모델명 카테고리를 삭제
     *
     * @param Id
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public void deleteCategoryByCateIdx(Long Id) {
        CategoryEntity categoryEntity = cateRepository.findById(Id).orElseThrow(() -> new NotFoundException("Category not found with id : " + Id));
        cateRepository.delete(categoryEntity);
    }

    /**
     * 제조사 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Boolean isCategoryMain(CategoryDto categoryDto) {
        Optional<CategoryEntity> categoryEntity = findCategoryByCompany(categoryDto.getCateCompany());
        if (!categoryEntity.isPresent()) {
            categoryDto.setCateUse("Y");
            categoryDto.setCateType("P");
            cateRepository.save(categoryDto.toEntity());
        }
        return categoryEntity.isPresent();
    }

    /**
     * 제품명 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Boolean isCategoryMiddle(CategoryDto categoryDto) {
        Optional<CategoryEntity> categoryEntity = findCategoryByCompanyAndItem(categoryDto.getCateCompany(), categoryDto.getCateItem());
        if (!categoryEntity.isPresent()) {
            categoryDto.setCateUse("Y");
            categoryDto.setCateType("P");
            cateRepository.save(categoryDto.toEntity());
        }
        return categoryEntity.isPresent();
    }

    /**
     * 모델명 카테고리를 수정
     *
     * @param categoryDto
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Boolean isCategorySub(CategoryDto categoryDto) {
        Optional<CategoryEntity> categoryEntity = findCategoryByCompanyAndItemAndModel(categoryDto.getCateCompany(), categoryDto.getCateItem(), categoryDto.getCateModelName());
        if (!categoryEntity.isPresent()) {
            categoryDto.setCateUse("Y");
            categoryDto.setCateType("P");
            cateRepository.save(categoryDto.toEntity());
        }
        return categoryEntity.isPresent();
    }

    /**
     * 모든 카테고리를 등록일자 순으로 찾는 메소드
     *
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<CategoryEntity> findPurchaseList(int page, int size) {
        return cateRepository.findAllByCateTypeOrderByRegdtDesc("P", PageRequest.of(page, size));
    }

    /**
     * 모든 카테고리를 모델명이 널이 아닌 조건으로 등록일자 순으로 찾는 메소드
     *
     * @param page
     * @param size
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Page<CategoryEntity> findCateModelNamePurchaseList(int page, int size) {
        return cateRepository.findAllByCateModelNameNotNullAndCateTypeOrderByRegdtDesc("P", PageRequest.of(page, size));
    }

    /**
     * 모든 카테고리를 특정 제조사명으로 제품명이 널이 아니며 모델명이 널인 조건으로 찾는 메소드
     *
     * @param company
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<CategoryEntity> findCateMidiListByCompany(String company) {
        return cateRepository.findAllByCateCompanyAndCateItemNotNullAndCateModelNameIsNullAndCateType(company, "P");
    }

    /**
     * 모든 제조사 카테고리 리스트를 조회
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<CategoryEntity> findCategoryMainList() {
        return cateRepository.findAllByCateCompanyNotNullAndCateItemIsNullAndCateModelNameIsNullAndCateType("P");
    }

    /**
     * 모든 제품명 카테고리 리스트를 조회
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<CategoryEntity> findCategoryMiddleList() {
        return cateRepository.findAllByCateCompanyNotNullAndCateItemNotNullAndCateModelNameIsNullAndCateType("P");
    }

    /**
     * 모든 모델명 카테고리 리스트를 조회
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public List<CategoryEntity> findCategorySubList() {
        return cateRepository.findAllByCateCompanyNotNullAndCateItemNotNullAndCateModelNameNotNullAndCateType("P");
    }

    /**
     * 카테고리를 특정 제조사명으로 제품명이 널이며 모델명이 널인 조건으로 찾는 메소드
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Optional<CategoryEntity> findCategoryByCompany(String cateCompany) {
        return cateRepository.findByCateCompanyAndCateItemNullAndCateModelNameNullAndCateType(cateCompany, "P");
    }

    /**
     * 카테고리를 특정 제조사명으로 제품명이 널이며 모델명이 널인 조건으로 찾는 메소드
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Optional<CategoryEntity> findCategoryByCompanyAndItem(String cateCompany, String cateItem) {
        return cateRepository.findByCateCompanyAndCateItemAndCateModelNameNullAndCateType(cateCompany, cateItem, "P");
    }

    /**
     * 카테고리를 특정 제조사명과 제품명과 모델명으로 찾는 메소드
     *
     * @history 2020 02 19 최초 생성
     */
    @Transactional
    public Optional<CategoryEntity> findCategoryByCompanyAndItemAndModel(String cateCompany, String cateItem, String cateModelName) {
        return cateRepository.findByCateCompanyAndCateItemAndCateModelNameAndCateType(cateCompany, cateItem, cateModelName, "P");
    }
}
