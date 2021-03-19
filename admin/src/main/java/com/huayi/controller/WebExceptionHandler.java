package com.huayi.controller;

import com.huayi.util.Result;
import com.huayi.util.exception.WebException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author gjw
 * @Date 2021/3/17 10:12
 **/
@RestControllerAdvice(basePackages = "com.huayi.controller")
public class WebExceptionHandler {

    @ExceptionHandler(WebException.class)
    public Result webException(WebException webException){
        return new Result(webException.getStatus()).setMsg(webException.getStatus().getMsg());
    }

}
