package com.huayi.handler;

import cn.hutool.json.JSONUtil;
import com.huayi.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  当访问接口没有权限时，自定义的返回结果
 * @Author gjw
 * @Date 2021/3/16 9:47
 **/

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Result<String> result = new Result<>(Result.Status.NoAuth);

        response.getWriter().println( JSONUtil.parse(result) );
        response.getWriter().flush();
    }
}
