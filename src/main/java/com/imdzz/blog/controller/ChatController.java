package com.imdzz.blog.controller;

import com.imdzz.blog.dto.Message;
import com.imdzz.blog.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/10 16:28
 */
@RestController
@RequestMapping("chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    private Logger logger = LoggerFactory.getLogger(ChatController.class);

    @PostMapping("/send")
    public String sendMsg(String msg) {
        //logger.info("{}说：{}。{}", msg.getSender(), msg.getMsg(), msg.getDate());
        logger.info(msg);
        return "ok";
    }
}
