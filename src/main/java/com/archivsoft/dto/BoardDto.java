package com.archivsoft.dto;

import com.archivsoft.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private String productIdList;
    private Long boardIdx;
    private Long productIdx;
    private Long boardUserIdx;
    private LocalDate boardCarryoutDt;
    private LocalDate boardCarryinDt;
    private String boardUsePlace;
    private String boardCarryYN;

    public BoardEntity toEntity(){
        BoardEntity build = BoardEntity.builder()

                .boardIdx(boardIdx)
                .productIdx(productIdx)
                .boardUserIdx(boardUserIdx)
                .boardCarryoutDt(boardCarryoutDt)
                .boardCarryinDt(boardCarryinDt)
                .boardUsePlace(boardUsePlace)
                .boardCarryYN(boardCarryYN)
                .build();

        return build;
    }

    @Builder
    public BoardDto(Long boardIdx,Long productIdx, Long boardUserIdx,LocalDate boardCarryoutDt, LocalDate boardCarryinDt,String boardUsePlace,String boardCarryYN,String productIdList)
    {
        this.boardIdx = boardIdx;
        this.productIdx = productIdx;
        this.boardUserIdx = boardUserIdx;
        this.boardCarryoutDt = boardCarryoutDt;
        this.boardCarryinDt = boardCarryinDt;
        this.boardUsePlace = boardUsePlace;
        this.boardCarryYN = boardCarryYN;

    }

}
