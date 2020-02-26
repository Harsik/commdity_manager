package com.archivsoft.domain.entity;
import com.archivsoft.dto.UserinfoDto;
import com.archivsoft.dto.getBoardListDto;
import lombok.*;
import org.hibernate.type.EntityType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
/**
 * 엔티티명 : BoardEntity
 * <BR>
 * <PRE>.
 * Board 테이블과 매핑 Entity
 * </PRE>
 * <BR>
 * <p>Copyright(c) 2020 by National Archives & Records Service
 * All Rights reserved. </p>
 *
 * @author 김주찬
 * @version 1.0
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
@NoArgsConstructor
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    @Column(length = 10, nullable = false)
    private Long productIdx;

    @Column(length = 10, nullable = false)
    private Long boardUserIdx;

    @Column(columnDefinition = "TEXT", nullable = false)
    private LocalDate boardCarryoutDt;

    @Column(columnDefinition = "TEXT", nullable = false)
    private LocalDate boardCarryinDt;

    @Column(length = 100, nullable = false)
    private String boardUsePlace;

    @Column(length = 100, nullable = false)
    private String boardCarryYN;

    @OneToOne
    @JoinColumn(name="boardUserIdx",insertable = false,updatable = false)
    UserinfoEntity userinfoEntity;

    @OneToOne
    @JoinColumn(name="productIdx", insertable = false,updatable = false)
    ProductEntity productEntity;

//    @ManyToMany
//    @JoinColumn(name="boardUserIdx",insertable = false,updatable = false)
//    @JoinColumn(name="productIdx", insertable = false,updatable = false)
//    Set<BoardEntity> test = new HashSet<>();


    //내부 엔티티에서만 선언 디비 테이블 컬럼으로는 선언하지 않음
    @Transient
    private String productIdList;

    @Transient
    private String productItem;

    @Transient
    private String userName;

    @Builder
    public BoardEntity(Long boardIdx,Long productIdx, Long boardUserIdx,LocalDate boardCarryoutDt,LocalDate boardCarryinDt,String boardUsePlace, String boardCarryYN,String productIdList,String productItem,String userName)
    {
        this.boardIdx = boardIdx;
        this.productIdx = productIdx;
        this.boardUserIdx = boardUserIdx;
        this.boardCarryoutDt = boardCarryoutDt;
        this.boardCarryinDt = boardCarryinDt;
        this.boardUsePlace = boardUsePlace;
        this.boardCarryYN = boardCarryYN;
        this.productIdList = productIdList;
        this.productItem = productItem;
        this.userName = userName;

    }
}
