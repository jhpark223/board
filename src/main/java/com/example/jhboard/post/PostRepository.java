package com.example.jhboard.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 현재 구현한 Elasticsearch 검색이 아닌, JPA로 검색하는 메서드 ( 현재 사용하지 않음 )
    @Query("SELECT p FROM Post p WHERE p.title LIKE %?1% OR p.content LIKE %?1%")
    List<Post> searchPosts(String text);
}

