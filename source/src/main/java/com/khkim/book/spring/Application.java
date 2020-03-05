package com.khkim.book.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/*
* [정리]
* spring 에서 bean 을 주입받는 방식
*   - @Autowired
*   - setter
*   - 생성자(권장)
*
* */


