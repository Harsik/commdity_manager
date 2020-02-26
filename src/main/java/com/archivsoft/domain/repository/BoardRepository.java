package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.*;
import com.archivsoft.dto.BoardDto;
import com.archivsoft.dto.getBoardListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
/**
 * 게시판 리스트 수정 삭제 조회 출력기능
 *
 * @author 김주찬
 * @version 1.0
 * @since 2020-02-19
 */
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findAllByBoardUserIdxNot(Long boardUserIdx);

    List<BoardEntity> findAllByBoardUserIdx(Long userIdx);

    BoardEntity findByProductIdx(Long productIdx);

    Page<BoardEntity> findAllByBoardUsePlaceContaining(String boardUserPlace, Pageable pageable);

    Page<BoardEntity> findByProductIdxIn(List<Long> getRegProductIdxList, Pageable pageable);

    Page<BoardEntity> findByBoardUserIdxIn(List<Long> userIdx, Pageable pageable);
}





