package com.huayi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (SysMenu)表实体类
 *
 * @author guojunwang
 * @since 2021-03-15 16:52:23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysMenu extends Model<SysMenu> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String desc;

    private String path;

    /**权限*/
    private String authorities;

    //是否删除
    @TableLogic
    private int deleted;

    //父类id
    private Long parentId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;


}
