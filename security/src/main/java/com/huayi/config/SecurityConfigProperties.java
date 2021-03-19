package com.huayi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author gjw
 * @Date 2021/3/16 9:28
 **/

@ConfigurationProperties(prefix = "security.confg")
@Data
public class SecurityConfigProperties {

    /**
     * 加密密钥
     * */
    private String secret;

    /**令牌过期时间(毫秒)*/
    private Long expiration;

    /**允许访问的路径*/
    private List<String> allowPath;

}
