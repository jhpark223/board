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
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 게시글 작성
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findById(user.id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setMember(member);
        post.setViewCount(0); // 조회수 초기화
        return postRepository.save(post);
    }

    // 게시글 조회
    @GetMapping("/post/{id}")
    public PostDto getPost(@PathVariable Long id, Authentication auth) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 조회수 증가
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        PostDto dto = new PostDto(post);

        // 로그인한 사용자인지 확인 여부
        boolean isAuthor = false;
        CustomUser user = (CustomUser) auth.getPrincipal();
        if (user.id.equals(post.getMember().getId())) {
            isAuthor = true;
        }
        dto.setCheckAuth(isAuthor);

        return dto;
    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostDto postDto, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findById(user.id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));


        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);

        return new PostDto(post);
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}") //ResponseEntity 사용해봄
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof CustomUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        CustomUser user = (CustomUser) auth.getPrincipal();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!user.id.equals(post.getMember().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not the author of this post");
        }

        postRepository.delete(post);

        return ResponseEntity.ok().body("Post deleted successfully");
    }

}
