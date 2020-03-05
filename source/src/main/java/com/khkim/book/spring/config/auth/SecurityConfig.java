package com.khkim.book.spring.config.auth;

import com.khkim.book.spring.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
*   Security 설정코드
* */
@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security 설정들을 활성화 시켜준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                   //h2-console 화면을 사용하기 위해 해당 옵션 disable
                .headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션 disable
                .and()
                    .authorizeRequests()    //url 별 권한 설정하는 옵션
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    //권한 대상 지정 "/" 등 지정된 url 들은 permitAll 옵션을 통해 열람 권한을 부여한다
                    .anyRequest().authenticated()   //설정된 값 이외의 url. authenticated 을 추가하여 나머지 url 들을 모두 인증된 사용자에게만 허용한다
                .and()
                    .logout()
                    .logoutSuccessUrl("/")  //로그아웃 성공시 / 주소로 변경
                .and()
                    .oauth2Login()  //OAuth2 로그인 설정 진입점
                    .userInfoEndpoint() //로그인이후 사용자정보
                    .userService(customOAuth2UserService);  //로그인 성공후 후속 userService 인터페이스의 구현체 등록

    }
}
