package com.mirim.board;

import jakarta.persistence.*;


@Entity // 테이블과 연결된 객체라는 것을 표시하는 것
@Table(name = "posts") // 실제 어떤 테이블과 연결할 지 명시해줌
public class Post {
    // table의 컬럼과 객체 멤버 하나하나가 이름이 같을 경우 어노테이션 생략. 지금 그 상황

    @Id // PK에는 이 어노테이션을 달아줘야함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 알아서 만들어주는 값을 표현, auto_increment설정 했으니까
    private Long id;
    private String title;
    private String content;

    public Post() {} // JPA는 알아서 채워넣기 전에 우선 빈 상태로 만든다. 필수

    public Post(String title, String content) { // 글 추가 저장 시 우리의 편의성을 위해
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
