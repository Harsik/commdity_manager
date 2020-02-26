package com.archivsoft.service;

import com.archivsoft.domain.entity.CategoryEntity;
import com.archivsoft.domain.repository.ServiceRepository;
import com.archivsoft.dto.ServiceDto;
import com.archivsoft.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 서비스명 : ServiceService
 * 서비스 관련 데이터 처리 메소드를 선언한 서비스
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Service
@AllArgsConstructor
public class ServiceService {

    /**
     * service 관련 repository 선언
     */
    private ServiceRepository serviceRepository;

    /**
     * 모든 데이터를 불러오는 메소드
     *
     * @param pageable
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Page<CategoryEntity> getAllServiceList(Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("cateIdx").descending());

        return serviceRepository.findByCateType(pageable, "S");
    }

    /**
     * 계정과목이 등록되어있는 데이터를 불러오는 메소드
     *
     * @param pageable
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Page<CategoryEntity> getServiceList(Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("cateIdx").descending());

        return serviceRepository.findByCateSectionIsNotNullAndCateCreditIsNotNullAndCateNatureIsNotNullAndCateSubjectIsNotNullAndCateType(pageable, "S");
    }

    /**
     * 등록페이지 구분을 선택하는 select 에 db에 존재하는 구분들을 넣기 위해 데이터를 불러오는 메소드, 수정 모달창에서도 사용
     *
     * @history 2020 02 18 최초 생성
     */
    public List<CategoryEntity> findCateSectionList() {
        return serviceRepository.findByCateSectionIsNotNullAndCateCreditIsNullAndCateNatureIsNullAndCateSubjectIsNull();
    }

    /**
     * 등록페이지 지급코드를 선택하는 select 에 db에 존재하는 지급코드들을 넣기 위해 불러오는 데이터를 메소드, 수정 모달창에서도 사용
     *
     * @history 2020 02 18 최초 생성
     */
    public List<CategoryEntity> findCateCreditList() {
        return serviceRepository.findByCateCreditIsNotNullAndCateSectionIsNullAndCateNatureIsNullAndCateSubjectIsNull();
    }

    /**
     * 등록페이지에 구분을 선택하는 select 가 선택될 경우 해당 구분으로 등록되어있는 성격들을 불러오는 메소드
     *
     * @param cateSection - 성격을 불러오기 위한 값
     * @history 2020 02 18 최초 생성
     */
    public List<CategoryEntity> findCateNatureList(String cateSection) {
        return serviceRepository.findByCateSectionAndCateNatureNotNullAndCateCreditIsNullAndCateSubjectIsNull(cateSection);
    }

    /**
     * 수정 모달창에서 등록되어있는 구분과 성격만 등록되어 있는 데이터를 불러오는 메소드
     *
     * @history 2020 02 18 최초 생성
     */
    public List<CategoryEntity> findCateNatureList() {
        return serviceRepository.findByCateSectionIsNotNullAndCateNatureIsNotNullAndCateCreditIsNullAndCateSubjectIsNull();
    }

    /**
     * 수정 모달창에서 계정과목까지 등록되어 있는 데이터를 불러오는 메소드
     *
     * @history 2020 02 18 최초 생성
     */
    public List<CategoryEntity> findCateSubjectList() {
        return serviceRepository.findByCateSectionIsNotNullAndCateCreditIsNotNullAndCateNatureIsNotNullAndCateSubjectIsNotNull();
    }

    /**
     * 구분 등록 메소드
     *
     * @param serviceDto - 등록할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean createCateSection(ServiceDto serviceDto) {
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateCreditIsNullAndCateNatureIsNullAndCateSubjectIsNull(serviceDto.getCateSection());
        if (!serviceEntity.isPresent()) {
            serviceRepository.save(serviceDto.toEntity());
        }
        return serviceEntity.isPresent();
    }

    /**
     * 지급코드 등록 메소드
     *
     * @param serviceDto - 등록할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean createCateCredit(ServiceDto serviceDto) {
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateCreditAndCateNatureIsNullAndCateSubjectIsNull(serviceDto.getCateCredit());
        if (!serviceEntity.isPresent()) {
            serviceRepository.save(serviceDto.toEntity());
        }
        return serviceEntity.isPresent();
    }

    /**
     * 성격 등록 메소드
     *
     * @param serviceDto - 등록할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean createCateNature(ServiceDto serviceDto) {
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateNatureAndCateCreditIsNullAndCateSubjectIsNull(serviceDto.getCateSection(), serviceDto.getCateNature());
        if (!serviceEntity.isPresent()) {
            serviceRepository.save(serviceDto.toEntity());
        }
        return serviceEntity.isPresent();
    }

    /**
     * 계정과목 등록 메소드
     *
     * @param serviceDto - 등록할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean createCateSubject(ServiceDto serviceDto) {
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateNatureAndCateCreditAndCateSubject(serviceDto.getCateSection(), serviceDto.getCateNature(),
                serviceDto.getCateCredit(), serviceDto.getCateSubject());
        if (!serviceEntity.isPresent()) {
            serviceRepository.save(serviceDto.toEntity());
        }
        return serviceEntity.isPresent();
    }

    /**
     * 구분 수정 메소드
     *
     * @param serviceDto - 수정할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean saveCateSection(ServiceDto serviceDto) {

        String beforeCateSection = serviceDto.getCateModelName();
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateCreditIsNullAndCateNatureIsNullAndCateSubjectIsNull(serviceDto.getCateSection());
        List<CategoryEntity> willChangeList = serviceRepository.findByCateSection(beforeCateSection);

        if (!serviceEntity.isPresent()) {
            for (CategoryEntity categoryEntity2 : willChangeList) {
                categoryEntity2.setCateSection(serviceDto.getCateSection());
            }
            serviceRepository.saveAll(willChangeList);
        }

        return serviceEntity.isPresent();
    }

    /**
     * 지급코드 수정 메소드
     *
     * @param serviceDto - 수정할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean saveCateCredit(ServiceDto serviceDto) {

        String beforeCateCredit = serviceDto.getCateModelName();
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateCreditAndCateNatureIsNullAndCateSubjectIsNull(serviceDto.getCateCredit());
        List<CategoryEntity> willChangeList = serviceRepository.findByCateCredit(beforeCateCredit);

        if (!serviceEntity.isPresent()) {
            for (CategoryEntity categoryEntity2 : willChangeList) {
                categoryEntity2.setCateCredit(serviceDto.getCateCredit());
            }
            serviceRepository.saveAll(willChangeList);
        }

        return serviceEntity.isPresent();
    }

    /**
     * 성격 수정 메소드
     *
     * @param serviceDto - 수정할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean saveCateNature(ServiceDto serviceDto) {

        String beforeCateNature = serviceDto.getCateModelName();
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateNatureAndCateCreditIsNullAndCateSubjectIsNull(serviceDto.getCateSection(), serviceDto.getCateNature());
        List<CategoryEntity> willChangeList = serviceRepository.findByCateSectionAndCateNature(serviceDto.getCateSection(), beforeCateNature);

        if (!serviceEntity.isPresent()) {
            for (CategoryEntity categoryEntity2 : willChangeList) {
                categoryEntity2.setCateNature(serviceDto.getCateNature());
            }

            serviceRepository.saveAll(willChangeList);
        }

        return serviceEntity.isPresent();
    }

    /**
     * 계정과목 수정 메소드
     *
     * @param serviceDto - 수정할 데이터를 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Boolean saveCateSubject(ServiceDto serviceDto) {

        CategoryEntity categoryEntity = serviceRepository.findById(serviceDto.getCateIdx()).orElseThrow(() -> new NotFoundException("Category not found with id : " + serviceDto.getCateIdx()));
        Optional<CategoryEntity> serviceEntity = serviceRepository.findByCateSectionAndCateNatureAndCateCreditAndCateSubject(serviceDto.getCateSection(), serviceDto.getCateNature(),
                serviceDto.getCateCredit(), serviceDto.getCateSubject());

        if (!serviceEntity.isPresent()) {
            categoryEntity.setCateSubject(serviceDto.getCateSubject());
            serviceRepository.save(categoryEntity);
        }

        return serviceEntity.isPresent();
    }

    /**
     * 구분 삭제 메소드
     *
     * @param id - 삭제할 데이터의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public void deleteCateSection(Long id) {
        CategoryEntity categoryEntity = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with id : " + id));
        String cateSection = categoryEntity.getCateSection();
        if (cateSection != null) {
            serviceRepository.deleteByCateSection(cateSection);
        }
    }

    /**
     * 지급코드 삭제 메소드
     *
     * @param id - 삭제할 데이터의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public void deleteCateCredit(Long id) {
        CategoryEntity categoryEntity = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with id : " + id));
        String cateCredit = categoryEntity.getCateCredit();
        if (cateCredit != null) {
            serviceRepository.deleteByCateCredit(cateCredit);
        }
    }

    /**
     * 성격 삭제 메소드
     *
     * @param id - 삭제할 데이터의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public void deleteCateNature(Long id) {
        CategoryEntity categoryEntity = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with id : " + id));

        String cateSection = categoryEntity.getCateSection();
        String cateNature = categoryEntity.getCateNature();

        if (cateSection != null && cateNature != null) {
            serviceRepository.deleteByCateSectionAndCateNature(cateSection, cateNature);
        }
    }

    /**
     * 계정과목 삭제 메소드
     *
     * @param id - 삭제할 데이터의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public void deleteCateSubject(Long id) {
        CategoryEntity categoryEntity = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found with id : " + id));
        serviceRepository.delete(categoryEntity);
    }
}
