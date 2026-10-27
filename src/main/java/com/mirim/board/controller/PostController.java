package com.mirim.board.controller;

import com.mirim.board.dto.PostRequest;
import com.mirim.board.dto.PostResponse;
import com.mirim.board.login.SessionConst;
import com.mirim.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 근데 클래스는 인스턴스화 해야하잖아?? 근데 우린 안했어. 그럼 누가?? - 스프링부트가!

@RestController
@RequestMapping("/posts")
public class PostController {
    // private final Notifier notifier;
    // private SmsNotifier notifier = new SmsNotifier(); // 강결합, 하지만 객체지향은 낮은 결합도를 지향
    // @RequestParam : 주소에서 ?로 쿼리파라미터를 받아옴!
    // - required : 필수 여부 옵션, 기본값은 true
    private final PostService postService;

    public PostController(PostService postService) { // 왜 오류나나? postcontroller가 bin 파일이 필요한데, bin이 없으요
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(required = false) String keyword) {
        if(keyword != null) {
            List<PostResponse> posts = postService.searchPosts(keyword);
            return ResponseEntity.ok(posts);
        }
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // @PathVariable : 주소에서 값을 하나 가져올 수 있음
    // @ResponseEntity<> :
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        // 게시글 번호가 0보다 작거나 같으면 잘못된 입력임
        if(id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 번호는 1이상이어야 합니다.");
        }

        PostResponse post = postService.getPost(id);

        return ResponseEntity.ok(post);
    }

    @GetMapping("/count")
    public String getPostCount() {
        long postCount = postService.getPostCount();
        return "게시글 개수 : "+postCount+"개";
    }

    // @RequesBody : 요청 데이터 받아오기
    // createPost()처럼 무언가를 생성하는 코드의 경우, 201(created)로 응답받는 것이 좀 더 직관적
    @PostMapping// 브라우저에서는 get이외의 요청이 불가ㅜ
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest request, @SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long loginMemberId) {
        // Map은 논리적 오류를 잡기 힘들어서 실무에서는 클래스로 만들어 사용 -> 그렇기에 프론트와 협업하기 위해 API문서화가 필요한 것
        // 필수 키를 검사하지 않고 있음
        String title = request.getTitle();
        String content = (request.getContent());

        PostResponse response = postService.createPost(title, content, loginMemberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePosts(@PathVariable long id, @Valid @RequestBody PostRequest request) {
        if(id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 번호는 1 이상이여야 합니다.");
        }

        String title = request.getTitle();
        String content = request.getContent();

        PostResponse response = postService.updatePost(id, title, content);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER_ID) Long loginMemberId) {
        postService.deletePost(id, loginMemberId);
        return ResponseEntity.noContent().build();
    }

}
