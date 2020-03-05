package com.khkim.book.spring.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우 필드(createDate, modifiedDate) 도 column 으로 인식한다
@EntityListeners(AuditingEntityListener.class)  //클래스에 Auditing 기능을 포함시킨다
public abstract class BaseTimeEntity {

    @CreatedDate    //Entity 가 생성되어 저장될때 시간이 자동저장
    private LocalDateTime createdDate;

    @LastModifiedDate //조회한 Entity 의 값을 변경하면 시간이 자동저장
    private LocalDateTime modifiedDate;
}
