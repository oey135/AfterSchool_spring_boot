package com.mirim.board.service;

import com.mirim.board.Notifier;
import com.mirim.board.Post;
import com.mirim.board.repository.PostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Notifier notifier;

    public PostService(PostRepository postRepository, Notifier notifier) { // 생성자 주입
        this.postRepository = postRepository;
        this.notifier = notifier;
    }


    // Optional : null일 수도 아닐 수도
    public Post getPost(Long id) {
        // orElse : 없으면 null리턴
        return postRepository.findById(id).orElse(null);
    }
    
    public Post createPost(String title, String content) {
        Post post = new Post(title, content);
        postRepository.save(post);

        //이메일 발송 (로그로 대체)
        notifier.send(title + " 게시글이 등록되었습니다.");

        return post;
    }

    public Post updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) {
            return null;
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post); // pk를 보고 이미 있으면 업뎃해줌
        return post;
    }

    public boolean deletePost(long id) {
        Post post = postRepository.findById(id).orElse(null);

        if(post == null) {
            return false;
        }

        postRepository.deleteById(id);
        return true;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public long getPostCount() {
        return postRepository.count();
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }
}
