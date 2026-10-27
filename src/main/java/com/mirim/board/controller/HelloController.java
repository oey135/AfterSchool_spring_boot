package com.mirim.board.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//@ 하는거 :  어노테이션
// controller는 메소드가 반환하는 문자열을 view로 인식한다!
// 그래서 현재 코드에서는 화면 오류가 난다!

// Rest :
// RestController는 반환된 값을 화면이 아니라 응답으로만 받아들인다.
// 응답한 데이터를 text, json등으로 바꿔준다
@RestController
public class HelloController {
    // 브라우저 -> 내장 톰캣 -> 교통 정리 담당 -> HelloController.hello()
    // 교통정리 담당 : dispatcherServlet
    // DispatcherServlet이 하는 일
    // - 주소를 보고 어느 메소드로 보낼 지 고른다
    // - 목적지가 없다면 404를 응답한다

    // CRUD : Create / *Read(GET)* / Update / Delete
    // 브라우저에서 주소팡으로 직접 요청할때는 GET 이외의 메서드는 보낼 수 없다.
    // 1. 게시글 작성하는 어떻게 테스트할까?
    // 2. RestController, Getmapping 뭐하는 애들일까?
    @Value("${my.message}")
    private String message;

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello")
    public String hello2() {
        return message;
//        throw new RuntimeException("일부러 에러를 냈습니다.");
    }

    // Map으로 반환 시 json으로 받음
    @GetMapping("/hello-map")
    public Map<String, Object> helloMap() {
        return Map.of("name", "김미림", "grade", 2);
    }

}
