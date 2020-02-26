package com.archivsoft.dto;

import com.archivsoft.domain.entity.MainEntity;
import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//public class MainDto {
//
//    private Long id;
//    private String writer;
//    private String title;
//    private String content;
//    private LocalDateTime createdDate;
//    private LocalDateTime modifiedDate;
//
//    public MainEntity toEntity(){
//        MainEntity build = MainEntity.builder()
//                .id(id)
//                .writer(writer)
//                .title(title)
//                .content(content)
//                .build();
//        return build;
//    }
//
//    @Builder
//    public MainDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate){
//        this.id = id;
//        this.writer = writer;
//        this.title = title;
//        this.content = content;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
//}
