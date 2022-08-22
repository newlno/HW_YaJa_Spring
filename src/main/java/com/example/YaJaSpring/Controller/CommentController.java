package com.example.YaJaSpring.Controller;


import com.example.YaJaSpring.Dto.Request.CommentRequestDto;
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
    public ResponseDto<?> commentCreate(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 댓글 수정하기
    @PutMapping("/api/comment/{commentId}")
    public ResponseDto<?> update(@PathVariable Long commentId,
                                 @RequestBody CommentRequestDto requestDto) {
        return commentService.update(commentId, requestDto);
    }

    // 댓글 삭제하기
    @DeleteMapping("/api/comment/{commentId}")
    public ResponseDto<?> delete(@PathVariable Long commentId) {
        return commentService.delete(commentId);
    }


}
