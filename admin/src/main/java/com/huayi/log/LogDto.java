package com.huayi.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author gjw
 * @Date 2021/3/19 15:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LogDto implements Serializable {

    /**方法名*/
    private String methodName;

    /**访问地址*/
    private String url;

    /**参数*/
    private String args;

    /**是否成功*/
    private boolean success;

    /*IP*/
    private String ip;

    /**ip所在地*/
    private String ipAddress;

    /** 接口耗时时间 */
    private Long time;

    /**错误信息*/
    private String errorInfo;

    /**返回信息*/
    private String returnInfo;

}
