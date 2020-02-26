
package com.archivsoft.service;

import com.archivsoft.domain.entity.StatisticEntity;
import com.archivsoft.domain.repository.CompanyInRepository;
import com.archivsoft.domain.repository.SalesRepository;
import com.archivsoft.dto.SearchConditionDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 업체별 미수,미지급 현황 목록을 출력,조회
 * @author 강병수
 * @version 1.0
 * @since 2020.02.20
 */
@Service
@AllArgsConstructor
public class CompanyInService {

    /**
     * CompanyInRepository 사용하기 위한 변수 선언
     */
    private CompanyInRepository companyInRepository;

    /**
     * 업체별 미수 현황 목록 조회
     * @param year 조회하기 위한 연도를 담고 있는 객체
     * @history
     *     2020.02.20 최초생성
     */
//    public List<StatisticEntity> findCompanyInCollectionMoneyList(String year){
//        return companyInRepository.findCompanyInCollectionMoney(year);
//    }

    /**
     * 업체별 미지급 현황 목록 조회
     * @param year 조회하기 위한 연도를 담고 있는 객체
     * @history
     *     2020.02.20 최초생성
     */
//    public List<StatisticEntity> findCompanyInPaymentMoneyList(String year){
//        return companyInRepository.findCompanyInPaymentMoney(year);
//    }

}