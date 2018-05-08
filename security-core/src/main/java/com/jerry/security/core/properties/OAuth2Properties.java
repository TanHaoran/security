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

    private OAuth2ClientProperties[] clients = {};
}
