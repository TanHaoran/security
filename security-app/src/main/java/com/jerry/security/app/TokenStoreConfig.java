package com.jerry.security.app;

import com.jerry.security.app.jwt.MyJwtTokenEnhancer;
import com.jerry.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/8
 * Time: 14:08
 * Description: 负责令牌存取的配置类
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    // 1. 当jerry.security.oauth2.storeType的值是redis的时候，这个类里面的所有配置都生效
    @ConditionalOnProperty(prefix = "jerry.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        // 从Redis连接工厂拿到Redis连接，就连接到了Redis服务器上，如果产生Token就存在指定的Redis中了
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * Jwt令牌配置
     */
    @Configuration
    // 1. 当jerry.security.oauth2.storeType的值是jwt的时候，这个类里面的所有配置都生效
    // 2. 或者没有这个配置项的时候，这个类里面的所有配置都生效
    @ConditionalOnProperty(prefix = "jerry.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 做一些Token生成中密钥的配置
         *
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            // 设置签名密钥
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return accessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer() {
            return new MyJwtTokenEnhancer();
        }
    }
}
