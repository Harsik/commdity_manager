package com.archivsoft.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@Table(name = "boards")
public class MainEntity extends TimeEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 10, nullable = false)
//    private String writer;
//
//    @Column(length = 100, nullable = false)
//    private String title;
//
//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String content;
//
//    @Builder
//    public MainEntity(Long id, String title, String content, String writer){
//        this.id = id;
//        this.writer = writer;
//        this.title = title;
//        this.content = content;
//    }
}
