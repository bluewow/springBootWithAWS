package com.khkim.book.spring.web;

import com.khkim.book.spring.config.auth.LoginUser;
import com.khkim.book.spring.config.auth.dto.SessionUser;
import com.khkim.book.spring.service.posts.PostsService;
import com.khkim.book.spring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

//page 와 관련된 컨트롤러

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    //CustomOAuth2UserService 에서 로그인 성공시 SessionUser 에 저장된다. 즉 user 에서 값을 가져올수있다
    @GetMapping("/")  //머스테치 스타더가 view resolver 처리
    public String index(Model model, @LoginUser SessionUser user) {  //Model 서버템플릿 엔진(머스타치)에서 사용할 수 있는 객체 저장
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null)    //저장된 값이 없다면 화면에 로그인 버튼이 보이게되는 로직
            model.addAttribute("userName", user.getName());

        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/posts/save")
    public String postsSave() {

        return "posts-save";
    }
}
