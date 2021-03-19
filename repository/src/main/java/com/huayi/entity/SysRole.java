package com.huayi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (SysRole)表实体类
 *
 * @author guojunwang
 * @since 2021-03-15 16:52:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysRole extends Model<SysRole> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String desc;

    //是否删除
    @TableLogic
    private int deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;


}
