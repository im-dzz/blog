package com.imdzz.blog.service;

import com.imdzz.blog.dto.CommentDTO;
import com.imdzz.blog.model.Comment;
import com.imdzz.blog.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/10 16:29
 */
@Service
public class CommentService {
    Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    CommentRepository commentRepository;

    /**
     * 添加评论
     * @param commentDTO
     */
    public void saveComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setBlogId(commentDTO.getBlogId());
        comment.setUserId(commentDTO.getUserId());
        comment.setContent(commentDTO.getContent());
        comment.setCreateDate(new Date());
        commentRepository.save(comment);
    }

    /**
     * 寻找某篇博客的所有评论
     * @param blogId
     * @return
     */
    public List<Comment> findCommentsByBlogId(String blogId){
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        logger.info("评论的数量是：{}", comments.size());
        // 因为thyemleaf模板问题，如果comments为空，那么页面上th:each所在那个评论列表div也就没有了，会影响新评论在div中的添加
        if (comments.size() == 0){
            Comment comment = new Comment();
            comment.setBlogId(blogId);
            comment.setUserId("系统管理员");
            comment.setContent("快来评论吧");
            comments.add(comment);
        }
        return comments;
    }
}
