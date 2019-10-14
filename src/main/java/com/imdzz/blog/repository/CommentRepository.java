package com.imdzz.blog.repository;

import com.imdzz.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/14 10:15
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBlogId(int blogId);
}
