package com.jerry.security.core.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/3
 * Time: 20:52
 * Description: 社交配置适配器，配置了操作数据库的Repository
 */
@Configuration
@EnableSocial
public class SocialConfigurer extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringSocialConfigurer mySocialSecurityConfigurer() {
        return new SpringSocialConfigurer();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 第一个参数是数据源，第二个参数是产生Connection的工厂(随着不同的服务提供商，这个会不同)，第三个参数是加密的方式(这里不加密)
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // 如果表明有前缀的话，这里需要设置一下
        jdbcUsersConnectionRepository.setTablePrefix("my_");
        return jdbcUsersConnectionRepository;
    }
}
