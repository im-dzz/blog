package com.imdzz.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/12 17:27
 */
@Data
@Entity
public class Comment {
    @Id
    int serialno;
    String blogId;
    String username;
    String content;
    String createDate;
}
