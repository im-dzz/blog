package com.imdzz.blog.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Blog {
	@Id
	int serialno; 
	String titleid;
	String title;
	String subTitle;
	String content;
	String createDate;
	String updateDate;
	
}
