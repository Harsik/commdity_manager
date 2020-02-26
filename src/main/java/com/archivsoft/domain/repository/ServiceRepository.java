package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 파일명 : ServiceRepository
 * 서비스 목록 조회 및 등록과 수정, 삭제 등을 처리하는 jpa 메소드들을 선언한 repository
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
public interface ServiceRepository extends JpaRepository<CategoryEntity, Long> {

    /**
     * 등록된 모든 데이터를 불러오는 메소드
     *
     * @param pageable
     * @param cateType - cateType 이 S 인것만 가져오도록 처리
     * @return page
     * @history 2020 02 18 최초 생성
     */
    Page<CategoryEntity> findByCateType(Pageable pageable, String cateType);

    /**
     * 모든 분류가 등록된 데이터만 불러오는 메소드
     *
     * @param pageable
     * @param cateType - cateType 이 S 인것만 가져오도록 처리
     * @return page
     * @history 2020 02 18 최초 생성
     */
    Page<CategoryEntity> findByCateSectionIsNotNullAndCateCreditIsNotNullAndCateNatureIsNotNullAndCateSubjectIsNotNullAndCateType(Pageable pageable, String cateType);

    /**
     * 구분 수정 시 수정 할 데이터를 불러오는 메소드
     *
     * @param cateSection - 수정 전 값으로 되어있는 데이터들을 가져오기 위한 수정 전 값
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSection(String cateSection);

    /**
     * 지급코드 수정 시 수정 할 데이터를 불러오는 메소드
     *
     * @param cateCredit - 수정 전 값으로 되어있는 데이터들을 가져오기 위한 수정 전 값
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateCredit(String cateCredit);

    /**
     * 성격 수정 시 수정 할 데이터를 불러오는 메소드
     *
     * @param cateSection - 수정 전 값으로 되어있는 데이터들을 가져오기 위한 수정 전 값
     * @param cateNature  - 수정 전 값으로 되어있는 데이터들을 가져오기 위한 수정 전 값
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSectionAndCateNature(String cateSection, String cateNature);

    /**
     * 구분 삭제 시 해당 구분을 가지고 있는 모든 데이터를 삭제하는 메소드
     *
     * @param cateSection - 수정 할 대상의 값
     * @history 2020 02 18 최초 생성
     */
    void deleteByCateSection(String cateSection);

    /**
     * 지급코드 삭제 시 해당 구분을 가지고 있는 모든 데이터를 삭제하는 메소드
     *
     * @param cateCredit - 수정 할 대상의 값
     * @history 2020 02 18 최초 생성
     */
    void deleteByCateCredit(String cateCredit);

    /**
     * 성격 삭제 시 해당 구분과  성격을 가지고 있는 모든 데이터를 삭제하는 메소드
     *
     * @param cateSection - 수정 할 대상의 값
     * @param cateNature
     * @history 2020 02 18 최초 생성
     */
    void deleteByCateSectionAndCateNature(String cateSection, String cateNature);

    /**
     * 등록페이지 select 에 db에 존재하는 구분들을 넣기 위해 불러오는 메소드, 수정 모달창에서도 사용
     *
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSectionIsNotNullAndCateCreditIsNullAndCateNatureIsNullAndCateSubjectIsNull();

    /**
     * 등록페이지 select 에 db에 존재하는 지급코드들을 넣기 위해 불러오는 메소드, 수정 모달창에서도 사용
     *
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateCreditIsNotNullAndCateSectionIsNullAndCateNatureIsNullAndCateSubjectIsNull();

    /**
     * 수정 모달창에서 구분과 성격만 등록되어 있는 데이터를 불러오는 메소드
     *
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSectionIsNotNullAndCateNatureIsNotNullAndCateCreditIsNullAndCateSubjectIsNull();

    /**
     * 수정 모달창에서 모든 분류가 등록되어 있는 데이터들을 불러오는 메소드
     *
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSectionIsNotNullAndCateCreditIsNotNullAndCateNatureIsNotNullAndCateSubjectIsNotNull();

    /**
     * 등록페이지에 구분 select 가 선택될 경우 해당  구분으로 등록되어있는 성격들을 불러오는 메소드
     *
     * @param cateSection - 성격들을 불러오기 위한 값
     * @return list
     * @history 2020 02 18 최초 생성
     */
    List<CategoryEntity> findByCateSectionAndCateNatureNotNullAndCateCreditIsNullAndCateSubjectIsNull(String cateSection);

    /**
     * 구분 등록 및 수정 시 해당 구분이 기존에 존재하는지 판별해주는 메소드
     *
     * @param cateSection - 기존의 존재 유무를 판단해야되는 값
     * @return Optional
     * @history 2020 02 18 최초 생성
     */
    Optional<CategoryEntity> findByCateSectionAndCateCreditIsNullAndCateNatureIsNullAndCateSubjectIsNull(String cateSection);

    /**
     * 지급코드 등록 및 수정 시 해당 지급코드가 기존에 존재하는지 판별해주는 메소드
     *
     * @param cateCredit - 기존의 존재 유무를 판단해야되는 값
     * @return Optional
     * @history 2020 02 18 최초 생성
     */
    Optional<CategoryEntity> findByCateCreditAndCateNatureIsNullAndCateSubjectIsNull(String cateCredit);

    /**
     * 성격 등록 및 수정 시 해당 성격이 기존에 존재하는지 판별해주는 메소드
     *
     * @param cateSection - 기존의 존재 유무를 판단해야되는 값
     * @param cateNature  - 기존의 존재 유무를 판단해야되는 값
     * @return Optional
     * @history 2020 02 18 최초 생성
     */
    Optional<CategoryEntity> findByCateSectionAndCateNatureAndCateCreditIsNullAndCateSubjectIsNull(String cateSection, String cateNature);

    /**
     * 계정과목 등록 및 수정 시 해당 계정과목이 기존에 존재하는지 판별해주는 메소드
     *
     * @param cateSection - 기존의 존재 유무를 판단해야되는 값
     * @param cateNature  - 기존의 존재 유무를 판단해야되는 값
     * @param cateCredit  - 기존의 존재 유무를 판단해야되는 값
     * @param cateSubject - 기존의 존재 유무를 판단해야되는 값
     * @return Optional
     * @history 2020 02 18 최초 생성
     */
    Optional<CategoryEntity> findByCateSectionAndCateNatureAndCateCreditAndCateSubject(String cateSection, String cateNature, String cateCredit, String cateSubject);
}
