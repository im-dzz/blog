package com.imdzz.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.imdzz.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/10/16 11:32
 */
@RestController
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    /**
     * 用户注册，默认为普通用户
     * @param data
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String regist(@RequestParam("data") String data) {
        JSONObject reqJson = JSONObject.parseObject(data);
        String username = reqJson.getString("username");
        String password = reqJson.getString("password");
        logger.info("用户注册：{},{}", username, password);
        userService.registUser(username, password);

        logger.info("用户注册成功");
        return "注册成功";
    }

    /**
     * 登录
     * @param data
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("data") String data) {
        JSONObject reqJson = JSONObject.parseObject(data);
        String username = reqJson.getString("username");
        String password = reqJson.getString("password");
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            return "用户名或密码不正确！";
        }
        if (subject.isAuthenticated()) {
            Session session = subject.getSession();
            session.setAttribute("username", subject.getPrincipal());
            logger.info("当前用户是：{}", session.getAttribute("username"));
            return "登录成功";
        } else {
            token.clear();
            return "登录失败";
        }
    }
}
