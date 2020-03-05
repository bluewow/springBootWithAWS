package com.khkim.book.spring.config.auth.dto;

import com.khkim.book.spring.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
*   인증된 사용자 정보만 필요하다 그외에 필요한 정보등은 없으니 name, email, picture 만 필드로 선언한다
* */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}