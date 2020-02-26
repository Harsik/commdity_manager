/**
 * ========================================================
 *
 * @FileName : CategoryRepository.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.domain.repository;

import java.util.List;
import java.util.Optional;

import com.archivsoft.domain.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 리스트를 출력하고 제조사, 제품명, 모델명 카테고리를 조회, 수정, 삭제하는 기능
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * 모든 카테고리를 등록일자 순으로 찾는 메소드
     */
    Page<CategoryEntity> findAllByCateTypeOrderByRegdtDesc(String cateType, Pageable pageable);

    /**
     * 모든 카테고리를 모델명이 널이 아닌 조건으로 등록일자 순으로 찾는 메소드
     */
    Page<CategoryEntity> findAllByCateModelNameNotNullAndCateTypeOrderByRegdtDesc(String cateType, Pageable pageable);

    /**
     * 모든 카테고리를 제조사명이 널이 아니고 제품명이 널이며 모델명이 널인 조건으로 찾는 메소드
     */
    List<CategoryEntity> findAllByCateCompanyNotNullAndCateItemIsNullAndCateModelNameIsNullAndCateType(String cateType);

    /**
     * 모든 카테고리를 제조사명이 널이 아니고 제품명이 널이 아니며 모델명이 널인 조건으로 찾는 메소드
     */
    List<CategoryEntity> findAllByCateCompanyNotNullAndCateItemNotNullAndCateModelNameIsNullAndCateType(String cateType);

    /**
     * 모든 카테고리를 제조사명이 널이 아니고 제품명이 널이 아니며 모델명이 널이 아닌 조건으로 찾는 메소드
     */
    List<CategoryEntity> findAllByCateCompanyNotNullAndCateItemNotNullAndCateModelNameNotNullAndCateType(String cateType);

    /**
     * 모든 카테고리를 특정 제조사명으로 제품명이 널이 아니며 모델명이 널인 조건으로 찾는 메소드
     */
    List<CategoryEntity> findAllByCateCompanyAndCateItemNotNullAndCateModelNameIsNullAndCateType(String cateCompany, String cateType);

    /**
     * 카테고리를 특정 제조사명으로 제품명이 널이며 모델명이 널인 조건으로 찾는 메소드
     */
    Optional<CategoryEntity> findByCateCompanyAndCateItemNullAndCateModelNameNullAndCateType(String cateCompany, String cateType);

    /**
     * 카테고리를 특정 제조사명과 제품명으로 모델명이 널인 조건으로 찾는 메소드
     */
    Optional<CategoryEntity> findByCateCompanyAndCateItemAndCateModelNameNullAndCateType(String cateCompany, String cateItem, String cateType);

    /**
     * 카테고리를 특정 제조사명과 제품명과 모델명으로 찾는 메소드
     */
    Optional<CategoryEntity> findByCateCompanyAndCateItemAndCateModelNameAndCateType(String cateCompany, String cateItem, String cateModelName, String cateType);

    /**
     * 카테고리를 특정 제조사명과 제품명으로 모델명이 널이 아닌 조건으로 찾는 메소드
     */
    List<CategoryEntity> findAllByCateCompanyAndCateItemAndCateModelNameNotNullAndCateType(String cateCompnay, String cateItem,String cateType);

}
