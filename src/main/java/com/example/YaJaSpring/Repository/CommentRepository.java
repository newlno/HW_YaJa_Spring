package com.example.YaJaSpring.Repository;

import com.example.YaJaSpring.Model.Comment;
import com.example.YaJaSpring.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    int countAllByPost(Post post);
}
