package com.example.YaJaSpring.Controller;


import com.example.YaJaSpring.Dto.Request.PostRequestDto;
import com.example.YaJaSpring.Dto.response.ResponseDto;
import com.example.YaJaSpring.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 초기화 대신 해주는 친구 / 생성자
@RestController // 객체를 JSON XML 형식으로 반환해주는 친구
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성하기
    @PostMapping("/api/comment")
    public ResponseDto<?> PostCreate(@RequestBody PostRequestDto requestDto) {
        return commentService.createcomment(requestDto);
    }

    // 댓글 수정하기
    @PutMapping("/api/comment/{id}")
    public ResponseDto<?> update(@PathVariable Long id,
                                 @RequestBody PostRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }

    // 댓글 삭제하기
    @DeleteMapping("/api/comment/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        return commentService.delete(id);
    }


}
