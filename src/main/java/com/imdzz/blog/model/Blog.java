package com.imdzz.blog.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
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
