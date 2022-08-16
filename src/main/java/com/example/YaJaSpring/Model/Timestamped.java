package com.example.YaJaSpring.Model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass // 상속하면 뭐 해줌...컬럼으로 인식
@EntityListeners(AutoCloseable.class)
public abstract class Timestamped {


    @CreatedDate  // 생성시간
    LocalDateTime createdAt;


    @LastModifiedDate  // 마지막 수정시간
    LocalDateTime modifiedAt;
}