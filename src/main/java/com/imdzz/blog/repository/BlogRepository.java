package com.imdzz.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imdzz.blog.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findByTitleLike(String title);
}
