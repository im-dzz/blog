package com.imdzz.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.service.BlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController {
	@Autowired
	BlogService blogService;
	
	Logger logger = LoggerFactory.getLogger(BlogController.class);
	
	@GetMapping()
	public ModelAndView home(Model model) {
		logger.info("blogs接口：");
		List<Blog> blogs = blogService.findAllBlogs();
		model.addAttribute("blogs", blogs);
		logger.info("列表大小是:" + blogs.size());
		return new ModelAndView("index.html", "Blog", model);
	}
	
	@GetMapping("{serialno}")
	public ModelAndView findBlog(@PathVariable("serialno") int id, Model model) {
		logger.info("blogs/{serialno}:{}", id);
		model.addAttribute("blog", blogService.findBlog(id, "").get(0));
		return new ModelAndView("blog.html", "Blog", model);
	}
}
