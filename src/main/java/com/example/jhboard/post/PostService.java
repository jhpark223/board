package com.example.jhboard.post;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.member.Member;
import com.example.jhboard.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostDocumentRepository postDocumentRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 게시글 작성
    public Post createPost(Post post, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = memberRepository.findById(user.id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setMember(member);
        post.setViewCount(0); // 조회수 초기화
        Post savedPost = postRepository.save(post);

        // Elasticsearch에 인덱싱
        indexPost(savedPost);

        return savedPost;
    }

    // Elasticsearch에 게시글 인덱싱
    private void indexPost(Post post) {
        PostDocument postDocument = new PostDocument();
        postDocument.setId(post.getId());
        postDocument.setTitle(post.getTitle());
        postDocument.setContent(post.getContent());
        postDocument.setAuthorName(post.getMember().getDisplayName());
        postDocument.setViewCount(post.getViewCount());
        postDocumentRepository.save(postDocument);
    }

    // 게시글 조회
    public PostDto getPost(Long id, Authentication auth) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Redis 키 생성: 사용자 ID와 게시글 ID를 기반으로 설정
        CustomUser user = (CustomUser) auth.getPrincipal();
        String redisKey = "viewed:post:" + id + ":user:" + user.id;

        // Redis에서 조회 여부 확인 및 조회수 증가
        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);
            indexPost(post);  // Elasticsearch에 조회수 업데이트

            // Redis에 30초 TTL 설정하여 조회 기록 저장
            redisTemplate.opsForValue().set(redisKey, "true", 30, TimeUnit.SECONDS);
        }

        PostDto dto = new PostDto(post);
        boolean isAuthor = user.id.equals(post.getMember().getId());
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
        Post updatedPost = postRepository.save(post);

        // Elasticsearch에 인덱싱
        indexPost(updatedPost);

        return new PostDto(updatedPost);
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

        // Elasticsearch에서 삭제
        postDocumentRepository.deleteById(id);
    }

    // 게시글 검색
    public List<PostDto> searchPosts(String keyword) {
        List<PostDocument> searchResults = postDocumentRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        return searchResults.stream()
                .map(postDocument -> {
                    PostDto postDto = new PostDto();
                    postDto.setId(postDocument.getId());
                    postDto.setTitle(postDocument.getTitle());
                    postDto.setContent(postDocument.getContent());
                    postDto.setAuthorName(postDocument.getAuthorName());
                    postDto.setViewCount(postDocument.getViewCount());
                    return postDto;
                }).collect(Collectors.toList());
    }
}
