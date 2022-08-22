package com.example.YaJaSpring.Model;


import com.example.YaJaSpring.Dto.Request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder  // 빌더패턴 사용하기 위한 어노테이션
@AllArgsConstructor // 필드에 쓴 모든 생성자를 만들어줌
@NoArgsConstructor // 기본 생성자를 생성해준다. / new 머시기 안해도 괜찮음
@Getter // 접근제한 디폴트 public / AccessLevel.private 이런식으로 별도 설정 가능
@Entity // 엔티티 설정
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id  // PK값
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)  // 부모삭제시 자식도 함께 삭제됨 / 단방향
    @ManyToOne(fetch = FetchType.LAZY)  // 게시글 한개는 댓글 여러개를 가질 수 있다.
    private Post post;

    // 생성시 사용
    public Comment(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    // 수정시 사용
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
