package com.example.YaJaSpring.Service;


import com.example.YaJaSpring.Dto.Request.PostRequestDto;
import com.example.YaJaSpring.Dto.response.AllPostResponseDto;
import com.example.YaJaSpring.Dto.response.CommentResponseDto;
import com.example.YaJaSpring.Dto.response.PostResponseDto;
import com.example.YaJaSpring.Model.Comment;
import com.example.YaJaSpring.Model.Post;
import com.example.YaJaSpring.Repository.CommentRepository;
import com.example.YaJaSpring.Repository.PostRepository;
import com.example.YaJaSpring.Dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor // 초기화 되지 않은 필드에 생성자 생성
@Service  // 비즈니스 로직을 담은 클래스를 빈으로 등록시키기 위해 사용
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 캐싱 / 캐쉬화 강의 내용 관련 / 쿼리 재요청이 아닌 이미 가지고 있는 데이터에서 긁어올수있음

    // 글 작성할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> createPost(PostRequestDto requestDto) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        postRepository.save(post);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    // 글 전체 조회할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> getAllPost() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            int comments = commentRepository.countAllByPost(post);
            AllPostResponseDto allPostResponseDto = AllPostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .commentsNum(comments)
                    .build();
            allPostResponseDtoList.add(allPostResponseDto);
        }
        return ResponseDto.success(allPostResponseDtoList);
    }

    // 글 개별 조회할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        // 위 isPresentPost 메소드 활용시 아래 코드는 필요없어짐
//        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
//        Optional<Post> post = postRepository.findById(id);
//        if (post.isEmpty()) {
//            ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
//        }
//        Post post1 = post.get();
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .comments(commentResponseDtoList)
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    // 글 수정할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> update(Long id, PostRequestDto requestDto) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
//        // 위 isPresentPost 메소드 활용시 아래 코드는 필요없어짐
//        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
//        Optional<Post> post = postRepository.findById(id);
//        if (post.isEmpty()) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
//        }
//        Post post1 = post.get();
        post.update(requestDto);
        postRepository.save(post);
        return ResponseDto.success(post);
    }

    // 글 삭제할때 쓰는 서비스
    // 1. 댓글도 삭제하도록 해야함
    // 1-1 케이스케이드 이용하기 (단방향)
    // 1-2 find 사용하여 찾아서 삭제하기 (단방향)
    // 1-3 post에 댓글을 추가하여 바로 삭제하기 (양방향)
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> delete(Long id) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        // 위 isPresentPost 메소드 활용시 아래 코드는 필요없어짐
        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
//        Optional<Post> post = postRepository.findById(id);
//        if (post.isEmpty()) {
//            ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
//        }
        postRepository.deleteById(id);
        return ResponseDto.success(id + " 번 ID 게시글을 삭제했습니다.");
    }


    // 게시글 찾기
    @Transactional(readOnly = true) // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public Post isPresentPost(Long id) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
        // orElse -> null & notnull 둘다 실행 / true 라면 설정한 값(Null) 반환
        // orElseGet -> null 만 실행
    }
}
