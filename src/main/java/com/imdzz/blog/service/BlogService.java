package com.imdzz.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.repository.BlogRepository;

@Service
public class BlogService {

	@Autowired
	BlogRepository blogRepository;
	
	public List<Blog> findBlog(Integer id,String title) {
		List<Blog> blogs = new ArrayList<Blog>();
		if (id != null) {
			blogs.add(blogRepository.findById(id).get());
		} else {
			blogs.addAll(blogRepository.findByTitleLike(title));
		}
		
		return blogs;
	}
}
