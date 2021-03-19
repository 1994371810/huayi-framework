package com.huayi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huayi.entity.SysMenu;

import java.util.List;

/**
 * (SysMenu)表数据库访问层
 *
 * @author guojunwang
 * @since 2021-03-15 17:05:24
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuByRoleId(String roleId);

    List<SysMenu> selectMenuByUserId(String userId);
}
