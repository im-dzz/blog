package com.imdzz.blog.handler;

import com.imdzz.blog.enums.ErrorCode;
import com.imdzz.blog.exception.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
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
public class GobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultExceptionHandler(Exception error, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if (error instanceof BlogException){
            BlogException blogException = (BlogException) error;
            modelAndView.addObject("code", blogException.getCode());
            modelAndView.addObject("msg", blogException.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            logger.info("发生异常！code:{},msg:{}", blogException.getCode(), blogException.getMessage());
        } else{
            modelAndView .addObject("code", ErrorCode.SYSTEM_ERROR.getCode());
            modelAndView .addObject("msg", error.getMessage());
            modelAndView.addObject("url", request.getRequestURL());
            logger.error("发生异常！code:{},msg:{}", ErrorCode.SYSTEM_ERROR.getCode(), error.getMessage());
        }
        modelAndView.setViewName("error.html");
        return modelAndView;
    }
}
