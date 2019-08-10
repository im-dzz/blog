package com.imdzz.blog.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Blog {
	@Id
	int serialno; 
	String titleid;
	String title;
	String subTitle;
	String content;
	String createDate;
	String updateDate;
	
	public int getSerialno() {
		return serialno;
	}
	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}
	public String getTitleid() {
		return titleid;
	}
	public void setTitleid(String titleid) {
		this.titleid = titleid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
