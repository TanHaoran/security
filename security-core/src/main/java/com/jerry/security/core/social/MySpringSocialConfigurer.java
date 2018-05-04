package com.jerry.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/4
 * Time: 9:27
 * Description: 配置SocialAuthenticationFilter过滤器处理URL的配置类
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    /**
     * 过滤器要处理的URL
     */
    private String filterProcessesUrl;

    public MySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 这里覆盖掉父类的postProcess()方法，个性化自己的过滤器，目的是改变这个过滤器处理的URL
     *
     * @param object
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
