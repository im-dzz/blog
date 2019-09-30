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
	 * @return void
	 * @throws Exception
	 */
	public void upload() throws Exception {
	    // 存放日志文件的路径
		File filePath = new File(tempPath);
		if (!filePath.isDirectory()) {
			throw new Exception("wrong path!");
		}

		// 读取所有的文件并将文本内容存入数据库
        for (String name : filePath.list()) {
        	try{
				logger.info("开始处理：" + name);
				String destPath = blogPath + "/" + name;
				File tempFile = new File(tempPath + "/" + name);
				File destFile = new File(destPath);
				tempFile.renameTo(destFile);

				if(destFile.isDirectory()){
					logger.info("{}是存放图片的目录，跳过", destFile.getName());
					continue;
				}

				// 若是文件，就需要加入到数据库中
				Blog blog = new Blog();
				// 取消后缀
				String title = name.split("\\.")[0];
				blog.setTitle(title);
				blog.setContent(destPath);
				// 规定文件名格式：classification：xxxxxx，如果没有写名分类则会自动归类到Others
				String classification = title.split("：")[0];
				if (title.equals(classification)){
					classification = "Others";
				}
				blog.setClassification(classification);
				blog.setCreateDate(new Date());
				blog.setUpdateDate(new Date());
				blogRepository.save(blog);
			} catch(Exception e){
        		logger.info(e.getMessage(), e);
			}
        }
	}
	
}
