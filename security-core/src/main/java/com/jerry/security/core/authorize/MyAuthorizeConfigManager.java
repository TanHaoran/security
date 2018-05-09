package com.jerry.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/9
 * Time: 10:28
 * Description:
 */
@Component
public class MyAuthorizeConfigManager implements AuthorizeConfigManager {

    /**
     * 因为是一个有序集合，所以这里要是用List
     */
    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviderSet;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 将所有的权限配置的都加进来
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviderSet) {
            authorizeConfigProvider.config(config);
        }
        // config.anyRequest().authenticated();
    }
}
