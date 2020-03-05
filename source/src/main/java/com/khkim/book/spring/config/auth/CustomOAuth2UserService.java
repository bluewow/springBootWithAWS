package com.khkim.book.spring.config.auth;

/*
*   구글로그인 이후 가져온 사용자의 정보(email, name, picture) 를 기반으로 가입 및 정보수정, 세션저장 등의 기능지원)
*
* */

import com.khkim.book.spring.config.auth.dto.OAuthAttributes;
import com.khkim.book.spring.config.auth.dto.SessionUser;
import com.khkim.book.spring.domain.user.User;
import com.khkim.book.spring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/*
*   구글 로그인 이후 가져온 사용자의 정보 등을 기반으로 가입 및 정보수정, 세션 저장 기능을 지원한다
* */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스를 구분하는 코드
        //이후 네이버 로그인 연동시에 네이버인지 구글인지 구분한다
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();    //필드값(Primary Key) 와 같은 의미

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        //Session 에 사용자 정보를 저장하기 위한 Dto 클래스
        //왜 User 클래스를 쓰지않고 새로만드는 이유
        //-------->User 클래스에 직렬화를 구현하지 않았다는 의미의 에러가 발생
        // 직렬화를 넣어도 되지만 User 클래스는 Entity 이기 때문에(언제 다른 Entity 와 관계가 형성될지 모른다)
        // 직렬화란
        //  - 1편 (https://woowabros.github.io/experience/2017/10/17/java-serialize.html)
        //  - 2편 (https://woowabros.github.io/experience/2017/10/17/java-serialize2.html)
        // 직렬화란?
        // 자바 시스템 내부에서 사용되는 Object 또는 Data를 외부의 자바 시스템에서도 사용할 수 있도록 byte 형태로 변환하는 기술

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    //정보 업데이트 되었을 때를 대비하여 update 기능 추가
    //사용자의 이름이나 프로필 사진이 변경되면 User Entity 도 반영된다
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}