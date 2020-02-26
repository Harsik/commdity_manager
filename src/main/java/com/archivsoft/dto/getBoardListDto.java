package com.archivsoft.dto;

import com.archivsoft.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class getBoardListDto {

    private Long boardIdx;
    private String userName;
    private String productItem;
    private LocalDate boardCarryoutDt;
    private LocalDate boardCarryinDt;
    private String boardUsePlace;
    private String boardCarryYN;


    /*public BoardEntity toEntity(){
        BoardEntity build = BoardEntity.builder()


                .boardCarryoutDt(boardCarryoutDt)
                .boardCarryinDt(boardCarryinDt)
                .boardUsePlace(boardUsePlace)
                .boardCarryYN(boardCarryYN)
                .build();

                return build;
    }*/

    @Builder
    public getBoardListDto(Long boardIdx, String userName, String productItem, LocalDate boardCarryoutDt, LocalDate boardCarryinDt,String boardUsePlace, String boardCarryYN)
    {
        this.boardIdx = boardIdx;
        this.userName = userName;
        this.productItem = productItem;
        this.boardCarryoutDt = boardCarryoutDt;
        this.boardCarryinDt = boardCarryinDt;
        this.boardUsePlace = boardUsePlace;
        this.boardCarryYN = boardCarryYN;

    }

}
