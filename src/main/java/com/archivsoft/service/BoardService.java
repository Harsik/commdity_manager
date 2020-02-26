
package com.archivsoft.service;

import com.archivsoft.domain.entity.BoardEntity;
import com.archivsoft.domain.entity.ProductEntity;
import com.archivsoft.domain.entity.UserinfoEntity;
import com.archivsoft.domain.repository.BoardRepository;
import com.archivsoft.domain.repository.CategoryRepository;
import com.archivsoft.domain.repository.ProductRepository;
import com.archivsoft.domain.repository.UserinfoRepository;
import com.archivsoft.dto.BoardDto;
import com.archivsoft.dto.UserinfoDto;
import com.archivsoft.dto.getBoardListDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;
/**
 * 게시판 리스트를 출력하고 조회, 수정, 삭제하는 기능
 *
 * @author 김주찬
 * @version 1.0
 * @since 2020-02-19
 */
@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;
    private ProductRepository proRepository;
    private UserinfoRepository userinfoRepository;

    /**
     * 검색조건에 따른 게시판 결과 리스트 페이징 처리하여 출력
     */
    @Transient
    public Page<BoardEntity> getSearchBoardList(String productItem, String boardUsePlace, String boardUserName, Pageable pageable) {
        Page<BoardEntity> getSearchResult = null;
        /*상품명, 사용위치, 사용자이름에 따른 검색결과 처리 */
        if (productItem != "") {
            /*(상품명) 검색 조건에 맞는 유저엔티티 생성 및 productIdx만 리스트로 추출 (해당 productItem은 @Transient 선언되어있어 find함수를 통해서 결과를 찾지못함)*/
            List<ProductEntity> productEntity = proRepository.findAllByProductItemContaining(productItem);
            List<Long> getProductItemList = new ArrayList<>();
            for (int i = 0; i < productEntity.size(); i++) {
                getProductItemList.add(i, productEntity.get(i).getProductIdx());
            }
            /*추출한 productIdx에만 해당하는 게시판 리스트 출력 및 페이징 처리*/
            getSearchResult = boardRepository.findByProductIdxIn(getProductItemList, pageable);
        } else if (boardUsePlace != "") {
            /*(사용위치) 검색 조건에 맞는 게시판 리스트 출력*/
            getSearchResult = boardRepository.findAllByBoardUsePlaceContaining(boardUsePlace, pageable);
        } else if (boardUserName != "") {
            /*(사용자명) 검색 조건에 맞는 유저엔티티 생성 및 해당 userIdx만 리스트로 추출 (productItem과 동일)*/
            List<UserinfoEntity> userinfoEntities = userinfoRepository.findAllByUserNameContaining(boardUserName);
            List<Long> getUserNameList = new ArrayList<>();
            for (int i = 0; i < userinfoEntities.size(); i++) {
                getUserNameList.add(i, userinfoEntities.get(i).getUserIdx());
            }
            getSearchResult = boardRepository.findByBoardUserIdxIn(getUserNameList, pageable);
        }
        return getSearchResult;
    }

    /**
     * 게시판 정보 페이징 처리및 출력
     */
    @Transactional
    public Page<BoardEntity> setBoardListPaging(Pageable pageable) {
        /*페이징 처리 출력*/
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("boardIdx").ascending());
        Page<BoardEntity> getBoardEntity = boardRepository.findAll(pageable);
        return getBoardEntity;
    }

    /**
     * 등록할 수 있는 상품 리스트 가져옴
     */
    @Transactional
    public List<ProductEntity> getMianList() {
        /*상품 사용여부 : "N"이면서 상품 상태값이 : "Y"인 등록 가능한 상품 리스트 출력*/
        return proRepository.findAllByProductUseYNAndProductStatus("N", "Y");
    }

    /**
     * 모든 사용자 리스트 가져옴
     */
    @Transactional
    public List<UserinfoEntity> getUserList() {
        return userinfoRepository.findAll();
    }

    /**
     * Board 등록
     */
    @Transactional
    public void saveBoardList(BoardEntity boardEntity) {
        /*사용자가 등록할 productIdxList 가져옴*/
        String[] getProductIdxList = boardEntity.getProductIdList().split(",");
        /*게시판 등록정보에 있는 사용자 아이디값 기준으로 사용자 정보 추출*/
        UserinfoEntity userinfoEntity = userinfoRepository.findByUserIdx(boardEntity.getBoardUserIdx());
        /*productIdx에 해당하는 상품 정보 Board에 저장*/
        for (int i = 0; i < getProductIdxList.length; i++) {
            Long getPIdx = Long.parseLong(getProductIdxList[i]);
            ProductEntity productEntity = proRepository.findById(getPIdx).orElse(null);
            BoardEntity board = BoardEntity.builder()
                    .boardIdx(boardEntity.getBoardIdx())
                    .boardCarryinDt(boardEntity.getBoardCarryinDt())
                    .boardCarryoutDt(boardEntity.getBoardCarryoutDt())
                    .boardCarryYN(boardEntity.getBoardCarryYN())
                    .boardUsePlace(boardEntity.getBoardUsePlace())
                    .boardUserIdx(boardEntity.getBoardUserIdx())
                    .productIdx(getPIdx)
                    .userName(userinfoEntity.getUserName())
                    .productItem(productEntity.getProductItem())
                    .build();
            productEntity.setProductUseYN("Y");
            /*고장없이 사용 가능 : "Y" , 고장 사용 불가능 : "N"*/
            productEntity.setProductStatus("Y");
            boardRepository.save(board);
        }
    }

    /**
     * Board 수정
     */
    @Transactional
    public void updateBoardList(BoardDto findBoardList) {
        /*사용자 아이디값을 통해 기존 등록된 상품 리스트와 새로 선택한 상품 (수정될)리스트 아이디값 가져옴*/
        List<BoardEntity> findProductIdList = boardRepository.findAllByBoardUserIdx(findBoardList.getBoardUserIdx());
        List<String> findSelectedPIdx = Arrays.asList(findBoardList.getProductIdList().split(","));
        /*기존 등록된 ProductList 삭제*/
        for (int j = 0; j < findProductIdList.size(); j++) {
            Long getPIdx = findProductIdList.get(j).getProductIdx();
            BoardEntity boardEntity = boardRepository.findByProductIdx(getPIdx);
            boardRepository.delete(boardEntity);
            ProductEntity productEntity = proRepository.findById(getPIdx).orElse(null);
            productEntity.setProductUseYN("N");
        }
        /*새로 등록할 ProductList 상품 등록 및 상태값 = "Y" 처리*/
        for (int i = 0; i < findSelectedPIdx.size(); i++) {
            /*사용자가 선택한 상품 리스트 저장 변수*/
            Long SelectedPIdx = Long.parseLong(findSelectedPIdx.get(i));
            BoardDto boardDto = findBoardList.builder()
                    .boardCarryinDt(findBoardList.getBoardCarryinDt())
                    .boardCarryoutDt(findBoardList.getBoardCarryoutDt())
                    .boardUserIdx(findBoardList.getBoardUserIdx())
                    .boardIdx(findBoardList.getBoardIdx())
                    .boardCarryYN(findBoardList.getBoardCarryYN())
                    .boardUsePlace(findBoardList.getBoardUsePlace())
                    .productIdx(SelectedPIdx)
                    .build();
            /*빌드한 boardDto 기반으로 엔티티화하여 저장*/
            boardRepository.save(boardDto.toEntity());
            /*저장한 상품리스트 사용여부 "Y"처리*/
            ProductEntity productEntity = proRepository.findById(SelectedPIdx).orElse(null);
            productEntity.setProductUseYN("Y");
        }
    }

    /**
     * Board 정보 삭제
     */
    @Transactional
    public void deleteBoardList(Long boardUserIdx) {
        /*사용자 아이디 값을 통해서 지울 BoardIdx, ProductIdx 리스트 가져옴*/
        List<BoardEntity> getBoardIdxAndProductIdx = boardRepository.findAllByBoardUserIdx(boardUserIdx);
        /*BoardIdx에 해당하는 Board 정보 삭제 및 ProductIdx에 해당하는 상품 상태값(productUseYN) = "N" 처리*/
        for (int i = 0; i < getBoardIdxAndProductIdx.size(); i++) {
            BoardEntity deleteBoardEntityList = boardRepository.findById(getBoardIdxAndProductIdx.get(i).getBoardIdx()).orElse(null);
            ProductEntity deleteProductEntityList = proRepository.findById(getBoardIdxAndProductIdx.get(i).getProductIdx()).orElse(null);
            deleteProductEntityList.setProductUseYN("N");
            boardRepository.delete(deleteBoardEntityList);
        }
    }

    /**
     * 해당 boardIx에 일치하는 Board 정보 불러옴
     */
    @Transactional
    public BoardEntity getFindBoardUserIdx(Long boardIdx) throws Exception {
        /*해당 boardIdx 값과 일치하는 Board 테이블 정보 불러옴*/
        Optional<BoardEntity> getBoardEntity = boardRepository.findById(boardIdx);
        BoardEntity boardEntitys = new BoardEntity();
        /*getBoardEntity 엔티티 값 존재 유무 확인*/
        if (getBoardEntity.isPresent()) {
            boardEntitys = getBoardEntity.get();
        } else {
            throw new Exception();
        }
        return boardEntitys;
    }

    /**
     * 사용자가 선택한 productIdxList 가져옴
     */
    @Transactional
    public String getProductIdxList(Long userId) {
        /*userId에 해당하는 유저의 등록된 상품 리스트 가져옴*/
        List<BoardEntity> findProductIdxList = boardRepository.findAllByBoardUserIdx(userId);
        String getProductIdxList = "";
        /*등록할 productIdxList ","로 구분*/
        for (int i = 0; i < findProductIdxList.size(); i++) {
            getProductIdxList += findProductIdxList.get(i).getProductIdx().toString();
            /*마지막 리스트값에 "," 추가 안하기위한 조건문*/
            if (i < findProductIdxList.size() - 1) {
                getProductIdxList = getProductIdxList + ',';
            }
        }
        return getProductIdxList;
    }

    /**
     * 사용자가 등록한 상품 제외 나머지 상품 리스트로 가져옴
     */
    @Transactional
    public String getOtherProductIdxList(Long userId) {
        /*사용자외에 등록된 나머지 상품 리스트로 가져옴*/
        List<BoardEntity> findOtherProductIdxList = boardRepository.findAllByBoardUserIdxNot(userId);
        String otherProductIdx = "";
        /*등록된 나머지 상품 리스트 ","로 상품 아이디값 구분*/
        for (int i = 0; i < findOtherProductIdxList.size(); i++) {
            otherProductIdx += findOtherProductIdxList.get(i).getProductIdx().toString();
            if (i < findOtherProductIdxList.size() - 1) {
                otherProductIdx = otherProductIdx + ',';
            }
        }
        return otherProductIdx;
    }

    /**
     * 사용자가 이미 등록한 상품 리스트 가져옴
     */
    @Transactional
    public List<ProductEntity> getRegistedProductList(Long userId) {
        /**/
        List<ProductEntity> getProductList = proRepository.findAllByProductUseYN("N");
        List<BoardEntity> getBoardList = boardRepository.findAllByBoardUserIdx(userId);
        /**/
        List<Long> toTalList = new ArrayList<>();
        List<Long> pList = new ArrayList<>();
        List<Long> bList = new ArrayList<>();
        /*s*/
        for (int i = 0; i < getBoardList.size(); i++) {
            bList.add(i, getBoardList.get(i).getProductIdx());
        }
        for (int i = 0; i < getProductList.size(); i++) {
            pList.add(i, getProductList.get(i).getProductIdx());
        }
        toTalList.addAll(pList);
        toTalList.addAll(bList);
        List<ProductEntity> getTotalProductList = proRepository.findAllByProductIdxIn(toTalList);

        return getTotalProductList;
    }
}