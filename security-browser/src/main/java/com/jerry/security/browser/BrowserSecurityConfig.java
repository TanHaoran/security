package com.jerry.security.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/24
 * Time: 17:21
 * Description: 浏览器项目主配置类
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // formLogin()是表单登录,httpBasic()是默认的弹窗登录
                .formLogin()
                .and()

                // authorizeRequests后面跟的都是已授权地址
                .authorizeRequests()
                // 对其他所有请求
                .anyRequest()
                // 都需要授权
                .authenticated();
    }
}
