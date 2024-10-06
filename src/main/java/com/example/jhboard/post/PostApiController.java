package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostApiController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 게시글 생성 API
    @PostMapping("/create")
    public Post createPost(@RequestBody Post post, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findById(user.id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setMember(member);
        post.setViewCount(0); // 조회수 초기화
        return postRepository.save(post);
    }
}
