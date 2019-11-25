package com.imdzz.blog.exception;

import com.imdzz.blog.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * 自定义异常
 * @author imdzz
 * @version 1.0
 * @date 2019/11/22 16:59
 */
@Data
public class BlogException extends RuntimeException{
    private String code;

    public BlogException(ErrorCodeEnum errorCode){
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public BlogException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
