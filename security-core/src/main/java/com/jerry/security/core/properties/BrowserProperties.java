package com.jerry.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/25
 * Time: 14:50
 * Description: 浏览器属性配置类
 */
@Data
public class BrowserProperties {

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;
}
