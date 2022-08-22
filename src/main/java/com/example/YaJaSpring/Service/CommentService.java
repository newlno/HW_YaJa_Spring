package com.example.YaJaSpring.Service;

import com.example.YaJaSpring.Dto.Request.CommentRequestDto;
import com.example.YaJaSpring.Dto.response.CommentResponseDto;
import com.example.YaJaSpring.Dto.response.ResponseDto;
import com.example.YaJaSpring.Model.Comment;
import com.example.YaJaSpring.Model.Post;
import com.example.YaJaSpring.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor // 초기화 되지 않은 필드에 생성자 생성
@Service  // 비즈니스 로직을 담은 클래스를 빈으로 등록시키기 위해 사용
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    // 댓글 작성할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> createComment(CommentRequestDto requestDto) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Post post = postService.isPresentPost(requestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        Comment comment = Comment.builder()
                .post(post)
                .content(requestDto.getContent())
                .build();
        // 포스트에 리스트 코멘트 추가하기 / 저장

        commentRepository.save(comment);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .postId(comment.getPost().getId())
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .build()
        );
    }

    // 댓글 수정할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> update(Long commentId, CommentRequestDto requestDto) {
        Post post = postService.isPresentPost(requestDto.getPostId());
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 ID 입니다.");
        }
//        // 위 isPresentComment 메소드 활용시 아래 코드는 필요없어짐
//        // Optional 은 null 값이 올 수 있도록 값을 감싸는 Wrapper 클래스
//        Optional<Comment> comment = commentRepository.findById(commentId); // FindById로 찾아오기
//        if (comment.isEmpty()) {  // 없으면 fail 처리해줌
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 ID 입니다.");
//        }
//        Comment comment1 = comment.get(); // FindById의 값을 새로운 코멘트로 선언
        comment.update(requestDto); // 리퀘스트 데이터로 수정
        commentRepository.save(comment); // JPA 기능 save 이용하여 저장 (덮어쓰기)
        return ResponseDto.success(comment);
    }

    // 댓글 삭제할때 쓰는 서비스
    @Transactional // 선언적 트랜잭션, 중간에 에러나면 없던 일로 처리해줌
    public ResponseDto<?> delete(Long id) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Comment comment = isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 ID 입니다.");
        }
        commentRepository.deleteById(id); // JPA 기능 delete 이용하여 삭제 /
        return ResponseDto.success(id + "번 댓글을 삭제했습니다.");
    }


    // 댓글 찾기
    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        // isPresent 메소드는 Boolean 타입으로 Optional 객체가 값을 가지고 있다면 true 없다면 false
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
        // orElse -> null & notnull 둘다 실행 / true 라면 설정한 값(Null) 반환
        // orElseGet -> null 만 실행
    }
}
