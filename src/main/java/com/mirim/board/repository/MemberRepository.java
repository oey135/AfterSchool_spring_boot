package com.mirim.board.repository;

import com.mirim.board.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // loginId와 같은 회원이 잇는 지 boolean으로 나타냄
    boolean existsByLoginId(String loginId);

    // loginId로 Member 조회
    Optional<Member> findByLoginId(String loginId);

}
