package com.example.jhboard.post;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {
    List<PostDocument> findByTitleContainingOrContentContaining(String title, String content);
}
