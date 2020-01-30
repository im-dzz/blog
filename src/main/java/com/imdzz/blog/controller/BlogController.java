package com.imdzz.blog.controller;

import java.util.List;

import com.imdzz.blog.constant.GlobalConstant;
import com.imdzz.blog.dto.Message;
import com.imdzz.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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

@Api(value = "博客操作控制器")
@RestController
@RequestMapping("blogs")
@Slf4j
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CommentService commentService;
	@Autowired
	RedisTemplate redisTemplate;

	/**
	 * 访问主页，记录访客
	 * @param model
	 * @return
	 */
    @ApiOperation(value = "返回所有博客列表", notes = "访问“/”时自动调用")
	@GetMapping()
	public ModelAndView home(Model model) {
		if (!redisTemplate.hasKey(GlobalConstant.VISITOR_NUM)){
			redisTemplate.opsForValue().set(GlobalConstant.VISITOR_NUM, 0);
		}
		int visitorNum = (int) redisTemplate.opsForValue().get(GlobalConstant.VISITOR_NUM);
		log.info("当前累计访问人数：{}", visitorNum);
		redisTemplate.opsForValue().set(GlobalConstant.VISITOR_NUM, ++visitorNum);

		List<Blog> blogs = blogService.findAllBlogs();
		List<String> classifications = blogService.findAllClassifications();
		model.addAttribute("blogs", blogs);
		model.addAttribute("classifications", classifications);
		model.addAttribute("visitorNum", visitorNum);
		// 当页面上需要form表单时，打开页面时必须传入一个对象，否则会找不到
		model.addAttribute("message", new Message());
		log.info("列表大小是:{};分类数量为：{}", blogs.size(), classifications.size());
		return new ModelAndView("index.html", "Blog", model);
    }

    /**
     * 查看某篇博客
     * @param blogId
     * @param model
     * @return
     */
    @ApiOperation(value = "根据id获取博客", notes = "")
    @GetMapping("findById/{serialno}")
	public ModelAndView findBlog(@ApiParam(value = "博客Id" , required=true ) @PathVariable("serialno") int blogId, Model model) {
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
        if (!redisTemplate.hasKey(GlobalConstant.VISITOR_NUM)){
            redisTemplate.opsForValue().set(GlobalConstant.VISITOR_NUM, 0);
        }
        int visitorNum = (int) redisTemplate.opsForValue().get(GlobalConstant.VISITOR_NUM);
        log.info("redis:网站点击量是{}", visitorNum);

        model.addAttribute("blogs", blogService.findBlogsByClassification(classification));
        model.addAttribute("classifications", blogService.findAllClassifications());
		model.addAttribute("visitorNum", visitorNum);
		return new ModelAndView("index.html", "Blog", model);
    }
}
