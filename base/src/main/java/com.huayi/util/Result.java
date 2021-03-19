package com.huayi.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/16 9:48
 **/

@Data
@Accessors(chain = true)
public class Result<T> {

    /**
     * 状态码
     * */
    private Integer code;

    /**
     * 响应消息
     * */
    private String msg;

    /**
     * 响应数据
     * */
    private List<T> data;

    /**
     * 分页数据
     * */
    private Object pageInfo;



    public Result(Status status){
        this.code = status.code;
        this.msg = status.msg;
    }





    @Getter
    public static enum Status {

        Success(200,"成功"),
        Fail(0,"失败"),
        Error(500,"服务器异常"),
        NoAuth(415,"权限不足"),
        NoLogin(-1,"请登录"),
        TokenExpire(-2,"登录信息已过期，请重新登录");

        private Integer code;
        @Setter
        private String msg;

        private Status(Integer code,String msg){
            this.code = code;
            this.msg = msg;
        }


    }
}
