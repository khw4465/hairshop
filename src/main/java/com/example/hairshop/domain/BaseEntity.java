package com.example.hairshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    //== 생성 일시 ==//
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    //== 최근 수정 일시 ==//
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
