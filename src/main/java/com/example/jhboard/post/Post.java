package com.example.jhboard.post;

import com.example.jhboard.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  //Eager : default , Lazy : 필요할때만
    @JoinColumn(
            name = "member_id", //member테이블을 가리키는 컬럼 -> foreign key
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member; //작성자 정보

    private int viewCount;

    @CreationTimestamp
    private LocalDateTime created;

    public Post() {
        // 기본 생성자
    }

}
