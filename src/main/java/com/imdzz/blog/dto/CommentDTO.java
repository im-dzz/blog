package com.imdzz.blog.dto;

import lombok.Data;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/14 10:25
 */
@Data
public class CommentDTO {
    String userId;
    String blogId;
    String content;


}
