package com.imdzz.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imdzz.blog.dto.CommentDTO;
import com.imdzz.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/10 16:28
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;


    @PostMapping("/send")
    public String sendMsg(@RequestParam String data) {
        //logger.info("{}说：{}。{}", msg.getSender(), msg.getMsg(), msg.getDate());
        logger.info("sendMsg入参:{}", data);
        CommentDTO commentDTO = JSONObject.parseObject(data, CommentDTO.class);
        commentService.saveComment(commentDTO);
        return "ok";
    }

}
