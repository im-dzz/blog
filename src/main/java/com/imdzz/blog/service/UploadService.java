package com.imdzz.blog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.repository.BlogRepository;
import com.imdzz.blog.util.DateFormatter;

@Service
public class UploadService {
	private Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Autowired
	DateFormatter dateFormatter;
	
	@Autowired
	BlogRepository blogRepository;
	
	private static final String FILE_PATH = "E:\\javaWork\\blog\\src\\main\\resources\\blogs";

	/**
	 * 读取指定目录下的博客并给单引号和双引号前面加上反斜杠，以便插入数据库
	 * @return true or false
	 * @throws Exception
	 */
	public boolean upload() throws Exception {
		File filePath = new File(FILE_PATH);
		if (!filePath.isDirectory()) {
			throw new Exception("wrong path!");
		}

		String[] fileList = filePath.list();
        for (String s : fileList) {
            File file = new File(FILE_PATH + "/" + s);
            String fileName = file.getName();

            long fileLength = file.length();
            byte[] contentBytes = new byte[(int) fileLength];
            String contentStr = "";
            try {
                FileInputStream fileIn = new FileInputStream(file);
                fileIn.read(contentBytes);
                contentStr = new String(contentBytes, StandardCharsets.UTF_8);
                contentStr = contentStr.replaceAll("'", "\\\\'").replace("\"", "\\\\\"");
                fileIn.close();
                file.delete();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            Blog blog = new Blog();
            blog.setTitle(fileName);
            blog.setContent(contentStr);
            // 规定文件名格式：classification: xxxxxxx
            blog.setClassification(fileName.split(":")[0]);
            blog.setCreateDate(new Date());
            blog.setUpdateDate(new Date());
            blogRepository.save(blog);
        }
		
		return true;
	}
	
}
