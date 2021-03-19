package com.huayi.service;

import cn.hutool.core.thread.ThreadUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author gjw
 * @Date 2021/3/19 14:19
 **/
@Service

public class TestService {

    @Async("fastExecutor")

    public void test(){
        ThreadUtil.sleep(3000);
        System.out.println("执行完成");
    }

}
