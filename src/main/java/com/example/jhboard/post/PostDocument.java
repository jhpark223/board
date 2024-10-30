package com.example.jhboard.post;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "posts")
public class PostDocument {
    @Id
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private int viewCount;
}
