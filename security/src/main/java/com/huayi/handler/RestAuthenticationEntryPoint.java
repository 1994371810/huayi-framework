package com.huayi.handler;

import cn.hutool.json.JSONUtil;
import com.huayi.util.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author gjw
 * @Date 2021/3/16 9:59
 **/

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Result result = new Result(Result.Status.NoLogin);

        if( authException instanceof UsernameNotFoundException || authException instanceof BadCredentialsException){
            result = new Result(Result.Status.Fail).setMsg(authException.getMessage());
        }

        response.getWriter().println( JSONUtil.parse(result) );
        response.getWriter().flush();
    }

}





