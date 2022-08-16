package com.example.YaJaSpring.Model;


import com.example.YaJaSpring.Dto.Request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor // 필드에 쓴 모든 생성자를 만들어줌
@NoArgsConstructor // 기본 생성자를 생성해준다. / new 머시기 안해도 괜찮음
@Getter // 접근제한 디폴트 public / AccessLevel.private 이런식으로 별도 설정 가능
@Entity // 엔티티 설정
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id  // PK값
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;


    // 생성시 사용
    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    // 수정시 사용
    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
