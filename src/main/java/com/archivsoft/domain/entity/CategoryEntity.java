/**
 * ========================================================
 *
 * @FileName : CategoryEntity.java
 * Open Issues     : N/A
 * @LastModifyDate : 2020. 2. 18.
 * @LastModifier : 홍성관
 * @LastVersion : 1.0
 * Change History
 * 2020. 2. 18.    홍성관    1.0    최초생성
 * =========================================================
 */

package com.archivsoft.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 클래스명 : CategoryEntity
 * <BR>
 * <PRE>.
 * 카테고리 테이블과 직접 관련되며 카테고리 모든 속성 정보 및 생성자 작성 메소드를 가지고 있습니다.
 * </PRE>
 * <BR>
 * <p>Copyright(c) 2020 by National Archives & Records Service
 * All Rights reserved. </p>
 *
 * @author 홍성관
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends TimeEntity {

    /**
     * 일련번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cateIdx;

    /**
     * 회사명
     */
    @Column(length = 20)
    private String cateCompany;

    /**
     * 상품명
     */
    @Column(length = 30)
    private String cateItem;

    /**
     * 모델 이름
     */
    @Column(length = 50)
    private String cateModelName;

    /**
     * 사용 여부
     */
    @Column(length = 1)
    private String cateUse;

    /**
     * 제품 등록인
     */
    @Column(length = 30)
    private String cateRegNM;

    /**
     * 제품관련 - P, 서비스 관련 - S
     */
    @Column(length = 15, nullable = false)
    private String cateType;

    /**
     * 현금 Or 카드
     */
    @Column(length = 15)
    private String cateCredit;

    /**
     * 판관비 Or 매입
     */
    @Column(length = 15)
    private String cateSection;

    /**
     * 인건비, 경비, 부채 자산
     */
    @Column(length = 20)
    private String cateNature;

    /**
     * 문서 참고
     */
    @Column(length = 30)
    private String cateSubject;

    /**
     * 카테고리 엔티티 생성자
     */
    @Builder
    public CategoryEntity(Long cateIdx, String cateCompany, String cateItem, String cateModelName, String cateUse, String cateRegNM, String cateType, String cateCredit, String cateSection, String cateNature, String cateSubject) {
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

