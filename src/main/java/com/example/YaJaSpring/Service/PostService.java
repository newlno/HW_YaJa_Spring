package com.example.YaJaSpring.Service;


import com.example.YaJaSpring.Dto.Request.PostRequestDto;
import com.example.YaJaSpring.Model.Post;
import com.example.YaJaSpring.Repository.PostRepository;
import com.example.YaJaSpring.Dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 트랜잭션 / 어노테이션 / 롤백 / 클라이언트 상태와 관계 없음 / 서비스단에서 끝나기 때문에
    // 캐싱 / 캐쉬화 강의 내용 관련 / 쿼리 재요청이 아닌 이미 가지고 있는 데이터에서 긁어올수있음
    //

    // 글 작성할때 쓰는 서비스
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return ResponseDto.success(post + " 생성 완료");
    }

    // 글 전체 조회할때 쓰는 서비스
    public ResponseDto<?> getAllPost() {
        List<Post> post = postRepository.findAll();
        return ResponseDto.success(post);
    }

    // 글 개별 조회할때 쓰는 서비스
    public ResponseDto<?> getPost(Long id) {
        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        return ResponseDto.success(post);
    }

    // 글 수정할때 쓰는 서비스
    public ResponseDto<?> update(Long id, PostRequestDto requestDto) {
        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        Post post1 = post.get();
        post1.update(requestDto);
        postRepository.save(post1);
        return ResponseDto.success(post1);
    }

    // 글 삭제할때 쓰는 서비스
    public ResponseDto<?> delete(Long id) {
        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        postRepository.deleteById(id);
        return ResponseDto.success(id + " 번 ID 게시글을 삭제했습니다.");
    }

}
