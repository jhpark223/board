package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 게시글 작성
    public Post createPost(Post post, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findById(user.id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setMember(member);
        post.setViewCount(0); // 조회수 초기화
        return postRepository.save(post);
    }

    // 게시글 조회
    public PostDto getPost(Long id, Authentication auth) {
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
    public PostDto updatePost(Long id, PostDto postDto, Authentication auth) {
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
    public void deletePost(Long id, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!user.id.equals(post.getMember().getId())) {
            throw new RuntimeException("You are not the author of this post");
        }

        postRepository.delete(post);
    }
}
