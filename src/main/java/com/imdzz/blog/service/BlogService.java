package com.imdzz.blog.service;

import com.imdzz.blog.dto.BlogDTO;
import com.imdzz.blog.enums.ErrorCodeEnum;
import com.imdzz.blog.exception.BlogException;
import com.imdzz.blog.model.Blog;
import com.imdzz.blog.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class BlogService {

	@Autowired
    private BlogRepository blogRepository;

	@Autowired
	RedisTemplate redisTemplate;
	
	public BlogDTO findBlogDTO(Integer id, String title) {
		if (id <= 0){
			throw new BlogException(ErrorCodeEnum.PARAM_ERROR);
		}
	    Blog blog = blogRepository.getOne(String.valueOf(id));
	    blog.setReadNum(blog.getReadNum() + 1);
	    blogRepository.save(blog);

		BlogDTO blogDTO = fillContent(blog);
		return blogDTO;
	}

	/**
	 * 此方法需优化，改为分页查询
	 * @return
	 */
	// TODO: 改为分页查询
	public List<Blog> findAllBlogs(){
		return blogRepository.findAll();
	}

	/**
	 * 读取日志正文
	 * @param blog 原始blog
	 * @return BlogDTO 原始对应的BlogDTO
	 * @throws IOException
	 */
	private BlogDTO fillContent(Blog blog) {
		BlogDTO blogDTO = new BlogDTO(blog);

		String contentStr = "";
		try {
			File file = new File(blog.getContent());
			FileInputStream fileIn = new FileInputStream(file);
			byte[] contentBytes = new byte[(int) file.length()];
			fileIn.read(contentBytes);
			contentStr = new String(contentBytes, StandardCharsets.UTF_8);
			// 解决图片路径问题
			contentStr = contentStr.replace("<img src='", "<img src='/");
			blogDTO.setContent(contentStr);

			fileIn.close();
		} catch(Exception e){
			log.error(e.getMessage(), e);
		}

		return blogDTO;
	}

	/**
	 * 查询出所有的分类
	 * @return classifications
	 */
	public List<String> findAllClassifications() {
		List<String> classifications = blogRepository.findDistinctClassification();
		return classifications;
	}

	public List<Blog> findBlogsByClassification(String classification){
		if(StringUtils.isBlank(classification)){
			throw new BlogException(ErrorCodeEnum.PARAM_ERROR);
		}
		List<Blog> blogs = blogRepository.findByClassification(classification);
		return blogs;
	}

//	public static void main(String[] args){
//		String a = "<p><img src='C:\\Users\\杜振宗\\Desktop\\日志\\前置机.assets\\1565943109273.png' alt='1565943109273' referrerPolicy='no-referrer' /></p><p><img src='前置机.assets/1565943400206.png' alt='1565943400206' referrerPolicy='no-referrer' /></p></div>\n";
//		//a.replace("<img src='", "<img src='/");
//		String[] s = a.split("<img src='");
//		String b = a.replace("<img src='", "<img src='/");
//		System.out.println(b);
//	}
}
