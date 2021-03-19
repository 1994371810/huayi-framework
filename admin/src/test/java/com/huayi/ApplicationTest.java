package com.huayi;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huayi.entity.SysUser;
import com.huayi.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/15 17:38
 **/
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void test1(){
        SysUser sysUser = new SysUser(null,"aa","123",0,null,null,null,null,null);
        SysUser sysUser2 = new SysUser(null,"aa","123",0,null,null,null,null,null);
        SysUser sysUser3 = new SysUser(null,"aa","123",0,null,null,null,null,null);

        sysUserMapper.insert(sysUser);
        sysUserMapper.insert(sysUser2);
        sysUserMapper.insert(sysUser3);

        System.out.println(sysUser.getId());
        System.out.println(sysUser2.getId());
        System.out.println(sysUser3.getId());
    }

    @Test
    public void test2() throws JsonProcessingException {
        PageHelper.startPage(1,3);
        List<SysUser> sysUsers = sysUserMapper.selectList(null);

        System.out.println(sysUsers);

        ObjectMapper o = new ObjectMapper();

        System.out.println( o.writeValueAsString( new PageInfo(sysUsers)));
    }

    @Test
    public void test3(){
        String a ="asdf";
        Boolean b = false;
        System.out.println(ObjectUtil.isBasicType(a));
        System.out.println(ObjectUtil.isBasicType(b));
    }

}
