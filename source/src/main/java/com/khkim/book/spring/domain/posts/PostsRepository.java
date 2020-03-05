package com.khkim.book.spring.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    //SpringDataJpa 에서 제공하지 않는 메소드는 쿼리로 해결할 수 있다
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
/*
* [정리]
* - ibatis 나 MyBatis 등에서 Dao 라고 불리는 DB Layer 접근자
* - interface 로 생성 후 JpaRepository<Entity class, PK Type> 을 상속하면 기본적인 CRUD 메소드가 생성된다
* - getOne, findById, findAll, save, delete 등의 함수가 정의되어 있다
*
* 주의할 점
* - Entity class 와 파일위치가 다르면 안된다
*
*
* 규모가 있는 프로젝트
* - FK의 조인, 복잡한 조건등으로 인해 Entity 클래스만으로 처리가 힘들어 "조회용" 프레임워크를 추가 사용
*   조회 - querydsl, jooq, MyBatis 등
*   등록, 수정, 삭제 - SpingDataJpa
* */
