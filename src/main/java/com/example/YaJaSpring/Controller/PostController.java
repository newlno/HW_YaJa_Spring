package com.example.YaJaSpring.Controller;


import com.example.YaJaSpring.Dto.Request.PostRequestDto;
import com.example.YaJaSpring.Service.PostService;
import com.example.YaJaSpring.Dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor // 초기화 대신 해주는 친구 / 생성자
@RestController // 객체를 JSON XML 형식으로 반환해주는 친구
public class PostController {

    private final PostService postService;

//    //@RequiredArgsConstructor 쓰면 필요없어짐
//    @Autowired
//    public PostController(PostService postService) {
//        this.postService = postService;
//    }

    // 글 작성하기
    @PostMapping("/api/post")
    public ResponseDto<?> PostCreate(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 글 조회하기 (전체)
    @GetMapping("/api/post")
    public ResponseDto<?> getAllPost() {
        return postService.getAllPost();
    }

    // 글 조회하기 (개별)
    @GetMapping("/api/post/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 글 수정하기
    @PutMapping("/api/post/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    // 글 삭제하기
    @DeleteMapping("/api/post/{id}")
    public ResponseDto<?> delete(@PathVariable Long id) {
        return postService.delete(id);
    }


}
