package com.imdzz.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.imdzz.blog.service.BlogService;

@RestController
@RequestMapping("/blogs")
public class BlogController {
	@Autowired
	BlogService blogService;
	
	@GetMapping("{serialno}")
	public ModelAndView findBlog(@PathVariable("serialno") int id, Model model) {
		model.addAttribute("blog", blogService.findBlog(id, "").get(0));
		return new ModelAndView("blog.html", "Blog", model);
	}
}
