package com.imdzz.blog.dto;

import com.imdzz.blog.model.Blog;
import lombok.Data;

import java.util.Date;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/9/26 15:03
 */
@Data
public class BlogDTO {
    String serialno;  // 主键
    String title;   // 标题
    String subTitle;  // 副标题/摘要
    String content;   // 正文
    String classification;  // 类别
    int readNum;  // 阅读数
    String createDate;  // 创建时间
    String updateDate;  // 更新时间

    public BlogDTO(Blog blog){
        this.serialno = blog.getSerialno();
        this.title = blog.getTitle();
        this.subTitle = blog.getSubTitle();
        this.content = "";
        this.classification = blog.getClassification();
        this.readNum = blog.getReadNum();
        this.createDate = blog.getCreateDate();
        this.updateDate = blog.getUpdateDate();
    }
}
