package com.archivsoft.dto;

import com.archivsoft.domain.entity.CategoryEntity;
import lombok.*;

/**
 * 파일명 : ServiceDto
 *
 * 서비스 데이터 교환 객체
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ServiceDto {

    private Long cateIdx;
    private String cateCompany;
    private String cateItem;
    private String cateModelName;
    private String cateUse;
    private String cateRegNM;
    private String cateType;
    private String cateCredit;
    private String cateSection;
    private String cateNature;
    private String cateSubject;

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

    @Builder
    public ServiceDto(Long cateIdx, String cateCompany, String cateItem, String cateModelName, String cateUse, String cateRegNM, String cateType, String cateCredit, String cateSection, String cateNature, String cateSubject) {
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
