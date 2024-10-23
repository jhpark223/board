package com.example.jhboard.comment;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.post.Post;
import com.example.jhboard.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto request, Authentication auth) {
        Comment createdComment = commentService.createComment(request, auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 게시글에 대한 댓글 조회
    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
