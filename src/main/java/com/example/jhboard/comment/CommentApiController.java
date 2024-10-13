package com.example.jhboard.comment;

import com.example.jhboard.member.CustomUser;
import com.example.jhboard.post.Post;
import com.example.jhboard.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentApiController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // 댓글 작성 API
    @PostMapping("/create")
    public Comment createComment(@RequestBody CommentDto request, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setDisplayName(user.displayName); // 로그인한 사용자의 displayName 설정
        comment.setContent(request.getContent());
        comment.setPost(post); // postId 대신 Post 객체를 설정

        return commentRepository.save(comment);
    }
}