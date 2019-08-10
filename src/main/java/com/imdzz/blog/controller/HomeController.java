package com.imdzz.blog.controller;

import java.util.List;

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
	
	@GetMapping()
	public ModelAndView home(Model model) {
		List<Blog> blogs = blogService.findAllBlogs();
		model.addAttribute("blogs", blogs);
		System.out.println("列表大小是:" + blogs.size());
		Blog blog = blogs.get(0);
		System.out.println(blog.getTitle());
		return new ModelAndView("index.html", "Blog", model);
	}
	
	
}
