package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostApiController {

    @Autowired
    private PostService postService;

    // 게시글 작성
    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post, Authentication auth) { //RequestBody를 통해 json 데이터를 Post 객체로 알아서 변환
        Post createdPost = postService.createPost(post, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 게시글 조회
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id, Authentication auth) {
        PostDto postDto = postService.getPost(id, auth);
        return ResponseEntity.ok(postDto);
    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto, Authentication auth) {
        PostDto updatedPostDto = postService.updatePost(id, postDto, auth);
        return ResponseEntity.ok(updatedPostDto);
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, Authentication auth) {
        postService.deletePost(id, auth);
        return ResponseEntity.ok().body("Post deleted successfully");
    }
}
