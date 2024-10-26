package com.example.jhboard.post;

import com.example.jhboard.comment.Comment;
import com.example.jhboard.comment.CommentRepository;
import com.example.jhboard.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 홈 화면: 게시글 목록 표시
    @GetMapping("/home")
    public String home(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts); //서버에서 뷰로 데이터 전달
        return "home"; // home.html로 이동
    }

    // 게시글 작성 페이지 표시
    @GetMapping("/post/new")
    public String newPost(Model model) {
        return "post_form"; // post_form.html로 이동
    }

    // 게시글 detail 페이지 표시
    @GetMapping("/post/{id}")
    public String detail(Model model) {

        return "post_detail"; // post_detail.html로 이동
    }

    // 게시글 수정 페이지 표시
    @GetMapping("/post/edit/{id}")
    public String editPost(@PathVariable Long id) {
        return "edit"; // edit.html로 이동
    }
}
