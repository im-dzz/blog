package com.imdzz.blog.controller;

import java.util.List;

import com.imdzz.blog.dto.Message;
import com.imdzz.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.imdzz.blog.model.Blog;
import com.imdzz.blog.service.BlogService;

@RestController
@RequestMapping("blogs")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CommentService commentService;
	@Autowired
	RedisTemplate redisTemplate;

	private Logger logger = LoggerFactory.getLogger(BlogController.class);

	/**
	 * 访问主页，记录访客
	 * @param model
	 * @return
	 */
	@GetMapping()
	public ModelAndView home(Model model) {
		if (!redisTemplate.hasKey("visitorNum")){
			redisTemplate.opsForValue().set("visitorNum", 0);
		}
		int visitorNum = (int) redisTemplate.opsForValue().get("visitorNum");
		logger.info("redis:visitorNum={}", visitorNum);
		redisTemplate.opsForValue().set("visitorNum", ++visitorNum);

		List<Blog> blogs = blogService.findAllBlogs();
		List<String> classifications = blogService.findAllClassifications();
		model.addAttribute("blogs", blogs);
		model.addAttribute("classifications", classifications);
		model.addAttribute("visitorNum", visitorNum);
		// 当页面上需要form表单时，打开页面时必须传入一个对象，否则会找不到
		model.addAttribute("message", new Message());
		logger.info("列表大小是:{};分类数量为：{}", blogs.size(), classifications.size());
		return new ModelAndView("index.html", "Blog", model);
	}

    /**
     * 查看某篇博客
     * @param blogId
     * @param model
     * @return
     */
	@GetMapping("findById/{serialno}")
	public ModelAndView findBlog(@PathVariable("serialno") int blogId, Model model) {
		model.addAttribute("blog", blogService.findBlogDTO(blogId, ""));
		model.addAttribute("comments", commentService.findCommentsByBlogId(String.valueOf(blogId)));
		return new ModelAndView("blog.html", "Blog", model);
	}

    /**
     * 查看某个分类的所有博客
     * @param classification
     * @param model
     * @return
     */
    @GetMapping("findByClassification/{classification}")
    public ModelAndView findBlogByClassification(@PathVariable("classification") String classification, Model model) {
        if (!redisTemplate.hasKey("visitorNum")){
            redisTemplate.opsForValue().set("visitorNum", 0);
        }
        int visitorNum = (int) redisTemplate.opsForValue().get("visitorNum");
        logger.info("redis:visitorNum={}", visitorNum);

        model.addAttribute("blogs", blogService.findBlogsByClassification(classification));
        model.addAttribute("classifications", blogService.findAllClassifications());
        model.addAttribute("visitorNum", visitorNum);
        return new ModelAndView("index.html", "Blog", model);
    }
}
