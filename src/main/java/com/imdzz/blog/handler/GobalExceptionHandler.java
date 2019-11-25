package com.imdzz.blog.handler;

import com.imdzz.blog.enums.ErrorCodeEnum;
import com.imdzz.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author imdzz
 * @version 1.0
 * @date 2019/11/25 9:39
 */
@ControllerAdvice
@Slf4j
public class GobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(Exception error, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if (error instanceof BlogException){
            BlogException blogException = (BlogException) error;
            modelAndView.addObject("code", blogException.getCode());
            modelAndView.addObject("msg", blogException.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            log.info("发生异常！code:{},msg:{}", blogException.getCode(), blogException.getMessage());
        } else{
            modelAndView .addObject("code", ErrorCodeEnum.SYSTEM_ERROR.getCode());
            modelAndView .addObject("msg", error.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            // 发生异常时打印堆栈信息
            log.error("系统错误！" + error.getMessage(), error);
        }
        modelAndView.setViewName("error.html");
        return modelAndView;
    }
}
