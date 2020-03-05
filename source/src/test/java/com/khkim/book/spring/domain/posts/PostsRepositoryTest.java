package com.khkim.book.spring.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //H2 database 를 자동 실행해준다
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    //Junit 에서 산위 테스트가 끝날때마다 수행되는 메소드를 지정
    //여기선 테스트용 데이터 베이스를 제거한다
    @After
    public void cleanup() {

        postsRepository.deleteAll();
    }

    @Test
    public void load() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //테이블 posts id 값이 있다면 update 가 없다면 insert 가 실행된다
        postsRepository.save(Posts.builder()
                            .title(title)
                            .content(content)
                            .author("dogseen@gmail.com")
                            .build());

        //when
        //테이블 posts 의 모든 데이터를 조회한다
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntityUpload() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>> createDate = " + posts.getCreatedDate() + ", modifiedDate= " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}


/*
* [정리]
* 실제 실행된 쿼리의 형태를 확인하기 위한방법
*   - src/main/resouces 아래에 application.properties 파일 생성
*   - spring.jpa.show_sql=true 옵션 추가
*
* h2 문법 to MySql 문법
*   - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect 옵션 추가
*
*
* */