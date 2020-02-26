package com.archivsoft.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeEntity {

    @CreatedDate
    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    private LocalDateTime regdt;

    @LastModifiedDate
    @Column(columnDefinition = "TEXT", nullable = false)
    private LocalDateTime chgdt;
}
