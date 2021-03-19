package com.huayi.controller;

import com.huayi.component.AuthUserDetailService;
import com.huayi.log.enums.EnableLog;
import com.huayi.entity.SysUser;
import com.huayi.service.TestService;
import com.huayi.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gjw
 * @Date 2021/3/16 11:42
 **/
@RestController
@Api(tags = "测试模块")
public class TestController {

    @Autowired
    private AuthUserDetailService authUserDetailService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/isLogin")
    @PreAuthorize("hasAuthority('userAdd')")

    public boolean login(){
        System.out.println(jwtTokenUtil.getLoginUser());
        return true;
    }

    @GetMapping("/user/login")
    @ApiOperation("用户登录")
    public String userLogin(String username,String password){
        String login = authUserDetailService.login(username, password);
        return login;
    }


    @ApiOperation("注册账户")
    @GetMapping("/register")
    public boolean register(String username,String password){
        return authUserDetailService.register(username, password);
    }



    @Autowired
    private TestService testService;

    @GetMapping("/testThread/{id}")
    @EnableLog("测试")
    public String test(String arg1, @PathVariable Integer id, SysUser sysUser){
        testService.test();
        return "完成";
    }


}
