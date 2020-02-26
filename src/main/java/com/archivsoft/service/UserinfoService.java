package com.archivsoft.service;

import com.archivsoft.domain.entity.UserinfoEntity;
import com.archivsoft.domain.repository.UserinfoRepository;
import com.archivsoft.dto.UserinfoDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 서비스명 : UserinfoService
 * <p>
 * 유저 정보 관련 데이터 처리 메소드를 선언한 서비스
 *
 * @author Archivsfot
 * @vesrion 1.0
 * @since 2020-02-18
 */
@Service
@AllArgsConstructor
public class UserinfoService {

    /**
     * repository 사용을 위한 선언
     */
    private UserinfoRepository userRepository;

    /**
     * 유저 목록을 page 형태로 조회하는 메소드
     *
     * @param pageable
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Page<UserinfoEntity> getPageList(Pageable pageable) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("userIdx").descending());

        return userRepository.findAll(pageable);
    }

    /**
     * 유저의 등록 및 수정을 처리하는 메소드
     *
     * @param userinfoDto - 저장할 유저정보들 담고 있는 객체
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public Long saveUser(UserinfoDto userinfoDto) {
        return userRepository.save(userinfoDto.toEntity()).getUserIdx();
    }

    /**
     * id 값에 따른 유저의 상세 정보를 조회하는 메소드
     *
     * @param id - 조회할 유저의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public UserinfoDto getUserInfo(Long id) {

        Optional<UserinfoEntity> userinfoEntityWrapper = userRepository.findById(id);
        UserinfoEntity userinfoEntity = userinfoEntityWrapper.get();

        UserinfoDto userinfoDto = UserinfoDto.builder()
                .userIdx(userinfoEntity.getUserIdx())
                .userName(userinfoEntity.getUserName())
                .userPosition(userinfoEntity.getUserPosition())
                .userRegdt(userinfoEntity.getUserRegdt())
                .userUseYN(userinfoEntity.getUserUseYN())
                .regdt(userinfoEntity.getRegdt())
                .chgdt(userinfoEntity.getChgdt())
                .build();

        return userinfoDto;
    }

    /**
     * 해당 id 를 가진 유저를 삭제하는 메소드
     *
     * @param id - 삭제할 유저의 id
     * @history 2020 02 18 최초 생성
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
