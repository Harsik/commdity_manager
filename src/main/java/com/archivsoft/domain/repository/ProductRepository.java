
package com.archivsoft.domain.repository;

import com.archivsoft.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 상품 목록을 출력하고 조회,등록,삭제,수정을 하기 위한 함수를 선언
 * @author 강병수
 * @version 1.0
 * @since 2020.02.03
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByProductIdx(Long productIdx);

    Page<ProductEntity> findAllByOrderByRegdtAsc(Pageable pageable);

    List<ProductEntity> findAllByProductItemContaining(String productItem);

    List<ProductEntity> findAllByProductUseYNAndProductStatus(String productUseYN, String productStatus);

    List<ProductEntity> findAllByProductUseYN(String productUseYn);

    List<ProductEntity> findAllByProductIdxIn(List<Long> toTalList);

}