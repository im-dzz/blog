package com.imdzz.blog.service;

import com.imdzz.blog.dto.CommentDTO;
import com.imdzz.blog.model.Comment;
import com.imdzz.blog.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public void saveComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setBlogId(commentDTO.getBlogId());
        comment.setUserId(commentDTO.getUserId());
        comment.setContent(commentDTO.getContent());
        comment.setCreateDate(new Date());
        commentRepository.save(comment);
    }
}
