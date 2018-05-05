package com.jerry.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/5
 * Time: 13:56
 * Description: 实现了这个接口就可以实现社交账号授权直接注册进入系统
 */
// @Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        // 根据社交用户信息默认创建用户，并返回用户唯一标识
        // 这里暂时先用用户名作为唯一标示
        return connection.getDisplayName();
    }
}
