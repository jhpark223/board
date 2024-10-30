package com.example.jhboard.post;

// PostSearchController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostSearchController {

    @Autowired
    private PostService postService;

    // 게시글 검색
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam(required = false) String keyword) {
        List<PostDto> searchResults = postService.searchPosts(keyword);
        return ResponseEntity.ok(searchResults);
    }
}