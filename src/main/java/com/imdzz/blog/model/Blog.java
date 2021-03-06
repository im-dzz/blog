package com.imdzz.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Blog {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	String serialno;  // 主键
	String title;   // 标题
	String subTitle;  // 副标题/摘要
	String content;   // 正文
	String classification;  // 类别
    int readNum;  // 阅读数
	String createDate;  // 创建时间
	String updateDate;  // 更新时间
	
}
