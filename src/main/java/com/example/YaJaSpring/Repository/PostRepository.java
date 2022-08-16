package com.example.YaJaSpring.Repository;

import com.example.YaJaSpring.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

}
