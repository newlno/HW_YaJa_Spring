package com.example.YaJaSpring.Model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass // 상속하면 뭐 해줌...컬럼으로 인식
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {


    @CreatedDate  // 생성시간
    private LocalDateTime createdAt;


    @LastModifiedDate  // 마지막 수정시간
    private LocalDateTime modifiedAt;
}