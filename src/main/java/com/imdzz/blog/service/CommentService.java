package com.imdzz.blog.service;

import com.imdzz.blog.dto.CommentDTO;
import com.imdzz.blog.enums.ErrorCodeEnum;
import com.imdzz.blog.exception.BlogException;
import com.imdzz.blog.model.Comment;
import com.imdzz.blog.repository.CommentRepository;
import com.imdzz.blog.util.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/10 16:29
 */
@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    /**
     * 添加评论
     * @param commentDTO
     */
    public void saveComment(CommentDTO commentDTO){
        if (StringUtils.isBlank(commentDTO.getContent())){
            throw new BlogException(ErrorCodeEnum.COMMENT_CAN_NOT_BE_NULL);
        }
        Subject user = SecurityUtils.getSubject();

        Comment comment = new Comment();
        comment.setBlogId(commentDTO.getBlogId());
        comment.setUsername(user.getPrincipal().toString());
        comment.setContent(commentDTO.getContent());
        comment.setCreateDate(DateFormatter.getNowDateStr());

        commentRepository.save(comment);
    }

    /**
     * 寻找某篇博客的所有评论
     * @param blogId
     * @return
     */
    public List<Comment> findCommentsByBlogId(String blogId){
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        log.info("评论的数量是：{}", comments.size());
        // 因为thyemleaf模板问题，如果comments为空，那么页面上th:each所在那个评论列表div也就没有了，会影响新评论在div中的添加
        if (comments.size() == 0){
            Comment comment = new Comment();
            comment.setBlogId(blogId);
            comment.setUsername("系统管理员");
            comment.setContent("快来评论吧");
            comments.add(comment);
        }
        return comments;
    }
}
