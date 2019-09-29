package com.imdzz.blog.service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.repository.BlogRepository;

@Service
public class UploadService {
	private Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	@Autowired
	private BlogRepository blogRepository;

	@Value("${temp_path}")
    private String tempPath = "";

	@Value("${spring.http.multipart.location}")
    private String blogPath;

	/**
	 * @return true or false
	 * @throws Exception
	 */
	public boolean upload() throws Exception {
	    // 存放日志文件的路径
		File filePath = new File(tempPath);
		if (!filePath.isDirectory()) {
			throw new Exception("wrong path!");
		}

		// 读取所有的文件并将文本内容存入数据库
        for (String name : filePath.list()) {
			logger.info("开始处理：" + name);
			String destPath = blogPath + "/" + name;
			File file = new File(tempPath + "/" + name);
			File destFile = new File(destPath);
			file.renameTo(destFile);

			if(destFile.isDirectory()){
				logger.info(destFile.getName() + "是一个目录，跳过");
				continue;
			}

			// 若是文件，就需要加入到数据库中
			Blog blog = new Blog();
			// 取消后缀
			blog.setTitle(name.split("\\.")[0]);
			blog.setContent(destPath);
            // 规定文件名格式：classification：xxxxxxx
            blog.setClassification(name.split("：")[0]);
            blog.setCreateDate(new Date());
            blog.setUpdateDate(new Date());
            blogRepository.save(blog);
        }
		
		return true;
	}
	
}
