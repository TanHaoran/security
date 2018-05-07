package com.jerry.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/7
 * Time: 16:09
 * Description: 社交登录过滤器的后处理器
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
