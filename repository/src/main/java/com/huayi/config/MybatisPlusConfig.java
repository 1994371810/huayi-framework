package com.huayi.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author gjw
 * @Date 2021/3/15 17:08
 **/
@Configuration
@MapperScan("com.huayi.mapper")
public class MybatisPlusConfig {



    /**
     * 配置 PageHelper
     * */
    @Bean
    PageInterceptor pageInterceptor(){

        Properties properties = new Properties();

        //标识是哪一种数据库
        properties.setProperty("helperDialect", "mysql");
        //启用合理化
        properties.setProperty("reasonable", "true");
        //如果查询第0页部分页
        properties.setProperty("pageSizeZero", "true");


        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }





}
