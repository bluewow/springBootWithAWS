package com.khkim.book.spring.web.dto;

import com.khkim.book.spring.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //생성자를 사용하여 값을 초기화 해주고 있다
    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}

/*
* [정리]
* Entity 클래스와 유사한 형태임에도 DTO 클래스를 추가한 이유
* - Entity 클래스는 DB 와 연결된 클래스이고(Critical) 이는 변경되면 여러 클래스에 영향을 끼치게 된다
* - [View] 를 위한 클래스라 자주 변동이 있다.
*
* */