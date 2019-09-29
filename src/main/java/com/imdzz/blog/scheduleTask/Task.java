package com.imdzz.blog.scheduleTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imdzz.blog.service.UploadService;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableScheduling
@Component
public class Task {
	
	private Logger logger = LoggerFactory.getLogger(Task.class);
	
	@Autowired
	private UploadService uploadService;
	
	/***
	 * 每天0点自动上传html格式的博客到数据库
	 */
	@Scheduled(cron = "0 0 * * * ?")
	public void uploadBlog(){
		logger.info("开始上传博客");
		try {
			uploadService.upload();
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("上传博客完成");
	}

}
