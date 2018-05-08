package com.jerry.security.app;

import com.jerry.security.core.properties.OAuth2ClientProperties;
import com.jerry.security.core.properties.SecurityProperties;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/6
 * Time: 17:09
 * Description: 认证服务器配置，当继承了AuthorizationServerConfigurerAdapter之后，注入的内容就不会自动找了，需要我们自己配置
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 配置TokenEndpoint
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 指定Token的存储位置
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    /**
     * 与客户端相关的配置，也就是会有哪些客户端回来访问我们的第三方应用，我们会给哪些第三方应用发Token
     * 这个方法配置后，在application.properties中配置的client_id和client_secret就不起作用了
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 指定client信息存储在内存中
        // 因为这里是在我们自己的服务和我们自己的app或前端进行通讯，我们这里就存储在内存中即可
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties config : securityProperties.getOauth2().getClients()) {

                // 循环读取出数组中存放的OAuth2ClientProperties，全部添加到clients中
                builder
                        .withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        // 令牌的有效时间（单位是秒）
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        // 配置支持的授权模式
                        .authorizedGrantTypes("refresh_token", "password")
                        // 发出去的权限有哪些
                        .scopes("all", "read", "write")
                ;
            }
        }


    }
}
