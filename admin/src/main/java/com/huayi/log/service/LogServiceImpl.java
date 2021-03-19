package com.huayi.log.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.huayi.log.*;
/**
 * @Author gjw
 * @Date 2021/3/19 15:58
 **/
@Component
@Slf4j
public class LogServiceImpl implements LogService{

    private final String logKey = "admin-log";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(LogDto logDto) {
        log.info("日志---->"+logDto);

        ListOperations listOP = redisTemplate.opsForList();
        listOP.rightPush(logKey,logDto);
    }

}
