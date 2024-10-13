package com.example.jhboard.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime created;
    private int viewCount;


    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getMember().getDisplayName(); // Member에서 필요한 정보만 가져옴
        this.created = post.getCreated();
        this.viewCount = post.getViewCount();
    }
}
