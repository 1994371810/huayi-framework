package com.huayi.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author gjw
 * @Date 2021/3/15 17:09
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Autowired(required = false)
    private GenerateUpdateId generateUpdateId;


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", ()-> new Date(), Date.class);

        if(generateUpdateId !=null){
            this.strictInsertFill(metaObject, "updateUser", generateUpdateId::getUpdateId, Long.class);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateTime", ()-> new Date(), Date.class);

        if(generateUpdateId !=null){
            this.strictInsertFill(metaObject, "updateUser", generateUpdateId::getUpdateId, Long.class);
        }
    }
}
