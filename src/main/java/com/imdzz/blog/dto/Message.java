package com.imdzz.blog.dto;

import lombok.Data;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/10 16:35
 */
@Data
public class Message {
    private String msg;
    private String date; //YYYY-MM-DD HH:mm:SS
    private String sender;
}
