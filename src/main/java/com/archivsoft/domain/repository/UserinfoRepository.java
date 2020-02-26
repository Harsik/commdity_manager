package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.UserinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * 파일명 : BoardRepository
 * <BR>
 * <PRE>.
 * 특정 조건 기반 유저정보 조회에 필요한 함수명 선언
 * </PRE>
 * <BR>
 * <p>Copyright(c) 2020 by National Archives & Records Service
 * All Rights reserved. </p>
 *
 * @author 류민송
 * @version 1.0
 * 최종 수정일 : 2020.02.17
 * 수정 내용 : 특정 조건 기반 유저정보 조회에 필요한 함수명 선언
 */
public interface UserinfoRepository extends JpaRepository<UserinfoEntity, Long> {
    //2020년 2월 17일 유저네임 가져오기 위해서 추가
    UserinfoEntity findByUserIdx(Long userIdx);
    List<UserinfoEntity> findAllByUserNameContaining(String userName);
}