package com.imdzz.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imdzz.blog.dto.CommentDTO;
import com.imdzz.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/send")
    public String sendMsg(@RequestParam String data) {
        //log.info("{}说：{}。{}", msg.getSender(), msg.getMsg(), msg.getDate());
        CommentDTO commentDTO = JSONObject.parseObject(data, CommentDTO.class);
        commentService.saveComment(commentDTO);
        return "ok";
    }

}
