package com.imdzz.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imdzz.blog.model.Blog;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findByTitleLike(String title);

    @Query(value = "select distinct(classification) from blog", nativeQuery = true)
    List<String> findDistinctClassification();

    List<Blog> findByClassification(String classification);
}
