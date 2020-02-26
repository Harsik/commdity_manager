/**
 * ========================================================
 *
 * @FileName : CategoryDto.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */
package com.archivsoft.dto;

import com.archivsoft.domain.entity.CategoryEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 카테고리 데이터 교환 객체
 *
 * @author 홍성관
 * @version 1.0
 * @since 2020-02-19
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // No request to Parameter 생성자
public class CategoryDto {

    /**
     * 일련번호
     */
    private Long cateIdx;

    /**
     * 회사명
     */
    private String cateCompany;

    /**
     * 상품명
     */
    private String cateItem;

    /**
     * 모델 이름
     */
    private String cateModelName;

    /**
     * 사용 여부
     */
    private String cateUse;

    /**
     * 제품 등록인
     */
    private String cateRegNM;

    /**
     * 제품관련 - P, 서비스 관련 - S
     */
    private String cateType;

    /**
     * 현금 Or 카드
     */
    private String cateCredit;

    /**
     * 판관비 Or 매입
     */
    private String cateSection;

    /**
     * 인건비, 경비, 부채 자산
     */
    private String cateNature;

    /**
     * 문서 참고
     */
    private String cateSubject;

    /**
     * 카테고리 엔티티 전환 메소드
     */
    public CategoryEntity toEntity() {
        CategoryEntity build = CategoryEntity.builder()
                .cateIdx(cateIdx)
                .cateCompany(cateCompany)
                .cateItem(cateItem)
                .cateModelName(cateModelName)
                .cateUse(cateUse)
                .cateRegNM(cateRegNM)
                .cateType(cateType)
                .cateCredit(cateCredit)
                .cateSection(cateSection)
                .cateNature(cateNature)
                .cateSubject(cateSubject)
                .build();
        return build;
    }

    /**
     * 카테고리 디티오 생성자
     */
    @Builder
    public CategoryDto(Long cateIdx, String cateCompany, String cateItem, String cateModelName, String cateUse, String cateRegNM, String cateType, String cateCredit, String cateSection, String cateNature, String cateSubject) {
        this.cateIdx = cateIdx;
        this.cateCompany = cateCompany;
        this.cateItem = cateItem;
        this.cateModelName = cateModelName;
        this.cateUse = cateUse;
        this.cateRegNM = cateRegNM;
        this.cateType = cateType;
        this.cateCredit = cateCredit;
        this.cateSection = cateSection;
        this.cateNature = cateNature;
        this.cateSubject = cateSubject;
    }
}
