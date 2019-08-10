package com.imdzz.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.service.BlogService;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	BlogService blogService;
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping()
	public ModelAndView home(Model model) {
		logger.info("home接口：");
		List<Blog> blogs = blogService.findAllBlogs();
		model.addAttribute("blogs", blogs);
		logger.info("列表大小是:" + blogs.size());
		return new ModelAndView("index.html", "Blog", model);
	}
	
	
}
