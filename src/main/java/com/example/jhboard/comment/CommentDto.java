package com.example.jhboard.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String content;
    private Long postId;

    private Long id;
    private String displayName;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.displayName = comment.getDisplayName();
        this.content = comment.getContent();
    }
}
