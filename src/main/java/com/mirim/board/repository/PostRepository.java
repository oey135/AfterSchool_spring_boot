package com.mirim.board.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate; // 스프링 부트가 도와줘서 편하게 사용
    private final List<Map<String, Object>> posts = new ArrayList<>();
    private Long nextId = 1L;

    // 로우 맵퍼로 하나하나 변형
    private final RowMapper<Map<String, Object>> postRowMapper = (rs, rowNum) -> {
        Map<String, Object> post = new HashMap<>();
        post.put("id", rs.getLong("id"));
        post.put("title", rs.getString("title"));
        post.put("title", rs.getString("content"));
        return post;
    };

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> save(Map<String, Object> post) {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, (String) post.get("title"));
            ps.setString(2, (String) post.get("content"));
            return ps;
        }, keyHolder);

        post.put("id", keyHolder.getKey().longValue());
        return post;
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    public List<Map<String, Object>> findAll() {
        String sql = "select * from posts";
        jdbcTemplate.query(sql, postRowMapper);
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public boolean deleteById(Long id) {
        Map<String, Object> post = findById(id);
        if(post == null) {
            return false;
        }
        posts.remove(post);
        return true;
    }

    public Map<String, Object> findById(Long id) {
        //ㄱㅔ시글 전체 : posts
        for(Map<String, Object> post : posts) {
            if(post.get("id").equals(id)) {
                return post;
            }
        }
        return null;
    }

    public long count() {
        return posts.size();
    }

    public List<Map<String, Object>> findByKeyword(String keyword) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 1. 전체 게시글을 순회하면서 keyword 포함하는 지 확인
        for (Map<String, Object> post : posts) {
            String title = (String) post.get("title");
            // 2. 포함하면 result에 게시글 정보 추가
            if(title != null && title.contains(keyword)) {
                result.add(post);
            }
        }
        return result;
    }
}
