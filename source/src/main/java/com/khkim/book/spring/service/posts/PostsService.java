package com.khkim.book.spring.service.posts;

import com.khkim.book.spring.domain.posts.Posts;
import com.khkim.book.spring.domain.posts.PostsRepository;
import com.khkim.book.spring.web.dto.PostsListResponseDto;
import com.khkim.book.spring.web.dto.PostsResponseDto;
import com.khkim.book.spring.web.dto.PostsSaveRequestDto;
import com.khkim.book.spring.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    //final 이 선언된 모든 필드를 인자값으로하는 생성자를 생성
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    //등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        // JPA 의 영속성 컨텍스트. 트랜젝션안에서 DB 데이터를 가져오면 영속성 컨텍스트가 유지된다
        // 이 상태에서 데이터를 변경하면, 트랜젝션 끝나는 시점에 변경분을 반영한다.
        // 즉, Entity 값만 변경하면 된다(dirty checking)
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    //조회
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    //조회
    @Transactional(readOnly = true) //트랜잭션 범위는 유지하되, 조회기능만 남겨두어 속도개선
    public List<PostsListResponseDto> findAllDesc() {
        //Posts 의 Stream 을 map 을 통해 PostsListResponseDto 변환후 List 로 반환하는 메소드
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //람다식 = .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList()); //collect는 스트림의 값들을 모아주는 기능을 한다. toMap, toSet, toList로 해당 스트림을 다시 컬렉션으로 바꿔준다.

        // Stream은 "데이터의 흐름이다" 자바 8부터 추가된 기능으로 "컬렉션, 배열등의 저장 요소를 하나씩 참조하며
        // 함수형 인터페이스(람다식)를 적용하여 반복적으로 처리할 수 있도록 해주는 기능"이다.

    }

    //삭제
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //entity or deleteById 를 사용하여 id 로 삭제가능
        postsRepository.delete(posts);
    }
}

/*
* [정리]
* Service 에선 Transaction 과 domain 순서를 설정한다
*
* @Transactional
*   - 단일 쿼리로는 해결할 수 없는 로직을 처리할 때 필요한 개념인 트랜잭션
*   - 2개 이상의 쿼리를 하나의 커넥션으로 묶어 DB 에 전송하고, 이 과정에서 에러 발생시 모든 과정을 Rollback 하는 기술
*
* Programmatic Transaction
* Declarative Transaction
*   - Spring 에서 주로 사용
*   - 어노테이션 or 설정파일을 이용해서 rule 을 정함
*
*
*
*
* */