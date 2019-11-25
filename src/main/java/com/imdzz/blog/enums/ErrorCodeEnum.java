package com.imdzz.blog.enums;

/**
 * 异常码
 * @author imdzz
 * @version 1.0
 * @date 2019/11/22 17:00
 */
public enum ErrorCodeEnum {
    OK("0", "成功"),
    SYSTEM_ERROR("500", "系统错误"),

    PARAM_ERROR("100001", "参数错误"),
    COMMENT_CAN_NOT_BE_NULL("100002", "评论内容不能为空"),

    USER_NOT_EXITS("200001", "用户不存在");


    private final String code;

    private final String msg;

    ErrorCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
