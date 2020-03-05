package com.khkim.book.spring.web;

import com.khkim.book.spring.service.posts.PostsService;
import com.khkim.book.spring.web.dto.PostsResponseDto;
import com.khkim.book.spring.web.dto.PostsSaveRequestDto;
import com.khkim.book.spring.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor    //final 이 선언된 모든 필드를 인자값으로하는 생성자를 생성
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //@RequestBody Http 요청을 자바 객체로 변환
    @PostMapping("/api/v1/posts")   //등록
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        System.out.println(requestDto);
        return postsService.save(requestDto);
    }

    //@PathVariable Url 경로의 변수를 처리한다
    @PutMapping("/api/v1/posts/{id}") //수정
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")   //조회
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return  id;
    }
}
/*
* [정리]
*
*
* */