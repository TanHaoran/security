package com.jerry.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/8
 * Time: 13:55
 * Description: 支持的OAuth客户端的属性
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    /**
     * 默认令牌的有效期
     */
    private int accessTokenValiditySeconds = 7200;
}
