package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Post post;

    private List<Member> memberList;

    @BeforeEach
    public void before() {
        // given: 테스트에 사용할 게시글과 사용자 생성
        post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test Content");
        post.setViewCount(0);
        postRepository.saveAndFlush(post);

        memberList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            member.setUsername("user" + i);
            member.setDisplayName("User " + i);
            member.setPassword("password");
            memberRepository.save(member);
            memberList.add(member);
        }
    }

    @AfterEach
    public void after() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 동시에_100개의_게시글_조회_요청() throws InterruptedException {
        // given: 100개의 쓰레드를 생성하여 동시에 게시글 조회 요청을 할 준비
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            executorService.submit(() -> {
                try {
                    Member member = memberList.get(idx);

                    CustomUser customUser = new CustomUser(
                            member.getUsername(),
                            member.getPassword(),
                            new ArrayList<>()
                    );
                    customUser.id = member.getId();
                    customUser.displayName = member.getDisplayName();

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            customUser,
                            null,
                            customUser.getAuthorities()
                    );
                    // when: 사용자가 게시글을 조회함
                    postService.getPost(post.getId(), authentication);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then: 게시글의 조회수가 100으로 증가했는지 확인
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(100, updatedPost.getViewCount());
    }
}
