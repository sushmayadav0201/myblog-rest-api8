package com.myblogrestapi8.repository;

import com.myblogrestapi8.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
