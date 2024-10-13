package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PostDto getPost(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return new PostDto(post); // DTO로 변환하여 반환
    }
}
