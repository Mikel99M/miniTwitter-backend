package com.example.minitwitter.repository;

import com.example.minitwitter.domain.Like;
import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndPost(User user, Post post);
}
