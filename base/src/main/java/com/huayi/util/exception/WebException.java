package com.huayi.util.exception;

import com.huayi.util.Result;
import lombok.Getter;

/**
 * @Author gjw
 * @Date 2021/3/16 11:00
 **/
@Getter
public class WebException extends RuntimeException{

    private Result.Status status;

    private String message;

    public WebException(Result.Status status){
        super(status.getMsg());
        this.status = status;
    }

    public WebException(Result.Status status, String message){
        super(status.getMsg());
        this.status = status;
        this.status.setMsg(message);
    }



}
