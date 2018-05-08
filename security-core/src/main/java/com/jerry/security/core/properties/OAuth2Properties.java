package com.jerry.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/8
 * Time: 13:54
 * Description:
 */
@Data
public class OAuth2Properties {

    /**
     * 签名密钥（发令牌时用它签名，验令牌的时候用它验签）
     */
    private String jwtSigningKey = "jerry";

    private OAuth2ClientProperties[] clients = {};

}
