package com.mirim.board.repository;

import com.mirim.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 인터페이스가 됨.. -> 알아서 빈으로 등록해서 클래스 안만들거임.
// 상속, 제네릭 사용 -> JpaRepository<어떤 엔티티 쓸거? 클래스 지정, PK 타입>
public interface PostRepository extends JpaRepository<Post, Long> {

    // 다 삭제됨 - CRUD는 기본적으로 구현되어있음.
    // 하나 남은 메소드 - 키워드로 제목 검색하기 <- 기본적으로 생성 ㄴㄴ
    // Jpa는 쿼리 메소드를 지원 : 쿼리를 나열해 메소드를 만들면 해당 기능을 제공! 그래서 이름 틀리면 안됨

    // 오타 주의 : 잘못된 쿼리 메소드는 서버 실행 X - 쿼리를 서버 실행 시 만들기 때문에
    // 키워드를 잘 매칭해 쓰면 JAP가 알아서 만들어 써줌

    // findBy(찾아라!) + Title(field aud) + Containing(like %%)
    // select * from posts where title ='';
    // List<Post> findByTitle(String keyword);

    // select * from posts where title like '%Title%';
    List<Post> findByTitleContaining(String keyword);

    // jpql, 엔티티를 대상으로 하는 쿼리 jpa에게 명령
    @Query("select p from Post p join fetch p.author")
    List<Post> findAllWithAuthor();
}
