package com.mirim.board;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity // 테이블과 연결된 객체라는 것을 표시하는 것
@Table(name = "posts") // 실제 어떤 테이블과 연결할 지 명시해줌
public class Post {
    // table의 컬럼과 객체 멤버 하나하나가 이름이 같을 경우 어노테이션 생략. 지금 그 상황
    // 필드명과 멤버명이 다르거나? 부가 옵션(not null 등)을 줄 경우 @Column 작성해야함
    // snake 케이스와 카멜 케이스 조합은 굳이 어노테이션 안 써도 됌

    @Id // PK에는 이 어노테이션을 달아줘야함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 알아서 만들어주는 값을 표현, auto_increment설정 했으니까
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="view_count", nullable = false)
    private Long viewCount;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public Post() {} // JPA는 알아서 채워넣기 전에 우선 빈 상태로 만든다. 필수

    public Post(String title, String content) { // 글 추가 저장 시 우리의 편의성을 위해
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0L;
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
