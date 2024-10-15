package com.example.jhboard.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동추가
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime created;
    private int viewCount;
    private boolean checkAuth;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getMember().getDisplayName(); // Member에서 필요한 정보만 가져옴
        this.created = post.getCreated();
        this.viewCount = post.getViewCount();
    }


}
