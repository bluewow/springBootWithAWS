package com.khkim.book.spring.domain.posts;

import com.khkim.book.spring.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter             //클래스 내의 모든 필드 의 Getter 메소드 자동생성
@NoArgsConstructor  //default 생성자 자동추가
@Entity             //테이블과 링크될 클래스를 나타냄 매칭 ex) SalesManager.java => sales_manager table
public class Posts extends BaseTimeEntity {

    @Id             //해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 의 생성 규칙 (auto_increment 가 된다)
    private Long id;    //Long 타입의 Auto_increment 추천(bitint). 주민등록번호나 비즈니스상 유니크키는 FK 맺을때 문제가 종종 발생한다

    @Column(length = 500, nullable = false) //테이블 컬럼. 선언하지 않더라도 컬럼이 된다. 추가 변경시 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    //빌더 패턴클래스 생성. 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함.
    //어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있다.
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

/*
* [정리]
* 1. domain 의 역활
*   - business logic 처리
*   - 각 객체가 본인의 이벤트를 가지고 있다.
*     기존 Dao 를 사용하던 방식은 service 영역에 모든 로직이 포함되어 객체를 단순 데이터 덩어리로 사용한다
*
* 2. setter metohod 의 부재
*   - 언제 어디서 변해햐 하는지 코드상에서 구분할수없어 차후 기능 변경시 복잡해 진다?
*   - 목적과 의도를 나타낼수있는 메소드 추가
*       public void cancelOrder() {
*           this.status = false;
*       }
*
* 3. setter 없이 값을 채워 DB 에 삽입하는 방법
*   - 생성자를 통한 DB 삽입
*   - 값 변경이 필요시 해당 이벤트에 맞는 public 메소드를 호출하여 변경
* *
* */
