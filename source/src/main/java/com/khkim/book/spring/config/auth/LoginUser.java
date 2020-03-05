package com.khkim.book.spring.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
*   indexController 에서 세션값을 가져오는 부분을 개선한다
*   (다른 컨트롤러와 메소드에서 세션값이 필요하면 반복적으로 호출하게되기 때문)
*
* */
//이 어노테이션이 생성될수있는 위치를 지정한다
//PARAMETER 로 지정했으니 메소드의 "파라미터"로 선언된 객체에서만 사용할 수 있다
//이 외에도 클래스 선언문에 쓸수있는 type 등도 있다
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    //이 파일을 어노테이션 클래스로 지정
    //LoginUser 라는 이름을 가진 어노테이션이 생성되었다
}
