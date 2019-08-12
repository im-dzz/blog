package com.imdzz.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Blog {
	@Id
	int serialno; 
	String title;
	String subTitle;
	String content;
	String classification;
	Date createDate;
	Date updateDate;
	
}
