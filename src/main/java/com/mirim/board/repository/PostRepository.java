package com.mirim.board.repository;

import com.mirim.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 인터페이스가 됨.. -> 알아서 빈으로 등록해서 클래스 안만들거임.
// 상속, 제네릭 사용 -> JpaRepository<어떤 엔티티 쓸거? 클래스 지정, PK 타입>
public interface PostRepository extends JpaRepository<Post, Long> {

    // 다 삭제됨 - CRUD는 기본적으로 구현되어있음.
    // 하나 남은 메소드 - 키워드로 제목 검색하기 <- 기본적으로 생성 ㄴㄴ
    // Jpa는 쿼리 메소드를 지원 : 쿼리를 나열해 메소드를 만들면 해당 기능을 제공! 그래서 이름 틀리면 안됨

    // 오타 주의
    List<Post> findByTitleContaining(String keyword);
}
