package com.example.jhboard.post;

import com.example.jhboard.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // JPA 관련된 테스트만 진행, 테스트 종료시 자동 롤백됨
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 데이터베이스 사용
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void savePostTest() {
        // given: 테스트에 필요한 데이터를 준비
        Member member = new Member();
        member.setUsername("testuser");
        member.setDisplayName("Test User");
        member.setPassword("password");

        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setViewCount(0);
        post.setMember(member);

        // when: PostRepository에 데이터 저장
        Post savedPost = postRepository.save(post);

        // then: 저장된 데이터 테스트
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Test Title");
        assertThat(savedPost.getContent()).isEqualTo("Test Content");
        assertThat(savedPost.getMember().getUsername()).isEqualTo("testuser");
    }
}
