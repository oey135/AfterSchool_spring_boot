package com.mirim.board.dto;

import com.mirim.board.Member;

public class AuthorResponse {
    private final Long id;
    private final String name;

    private AuthorResponse(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static AuthorResponse from(Member member) {
        return new AuthorResponse(member.getId(), member.getName());
    }
}
