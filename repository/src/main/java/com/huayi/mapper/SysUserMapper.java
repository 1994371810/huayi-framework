package com.huayi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huayi.entity.SysUser;

/**
 * (SysUser)表数据库访问层
 *
 * @author guojunwang
 * @since 2021-03-15 17:05:24
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    public SysUser selectUserByUsername(String username);

}
