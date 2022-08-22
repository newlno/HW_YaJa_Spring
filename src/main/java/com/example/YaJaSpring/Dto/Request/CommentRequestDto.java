package com.example.YaJaSpring.Dto.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Long postId;
    private String content;
}
