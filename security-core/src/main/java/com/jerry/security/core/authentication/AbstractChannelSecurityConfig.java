package com.jerry.security.core.authentication;

import com.jerry.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 16:20
 * Description: 使用用户名密码登录相关的配置类，包括登录方式、登录页面、处理登录的请求、成功处理器和失败处理器
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler myAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http
                // formLogin()是表单登录,httpBasic()是默认的弹窗登录
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                // 告诉UsernamePasswordAuthenticationFilter处理下面这个请求
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);
    }

}
