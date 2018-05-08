package com.jerry.security.browser;

import com.jerry.security.core.authentication.AbstractChannelSecurityConfig;
import com.jerry.security.core.properties.SecurityConstants;
import com.jerry.security.core.properties.SecurityProperties;
import com.jerry.security.core.validate.code.ValidateCodeSecurityConfig;
import com.jerry.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/24
 * Time: 17:21
 * Description: 浏览器项目主配置类
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 这里DateSource就会读取application.properties中配置的数据源信息
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfigurer;

    /**
     * Session过期的处理策略
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    /**
     * Session并发时，后面Session踢掉前面Session，对前面用户做的处理策略
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * 退出成功处理器
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 设置项目启动的时候创建保存记住我的那张表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置和用户名密码登录相关的配置
        applyPasswordAuthenticationConfig(http);

        http
                // 设置验证码相关的配置
                .apply(validateCodeSecurityConfig)

                // 设置短信登录相关的配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)

                // 社交登录相关配置
                .and()
                .apply(mySocialSecurityConfigurer)

                // 记住我的配置
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                // 指定获取UserDetails的类
                .userDetailsService(userDetailsService)

                // Session相关配置
                .and()
                .sessionManagement()
                // 设置当Session过期时的策略处理
                .invalidSessionStrategy(invalidSessionStrategy)
                // 设置最大的Session数量，即用户在后面登录产生的Session会把前面登录时的Session失效掉。
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                // 设置这个表示当Session数量达到最大数后，会阻值后面的用户进行登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                // 配置这个，就可以针对后面用户登录踢掉前面用户，对被踢掉的用户做一个处理
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()

                // 退出登录相关的配置
                .and()
                .logout()
                // 配置处理退出登录的URL
                .logoutUrl("/signOut")
                // 下面两个logoutSuccessUrl和logoutSuccessHandler是互斥的，配置一个后另一个就会失效
                // 配置退出 登录后跳转的URL
                // .logoutSuccessUrl("/logout.html")
                .logoutSuccessHandler(logoutSuccessHandler)
                // 删除浏览器中Cookie
                .deleteCookies("JSESSIONID")

                .and()
                // authorizeRequests后面跟的都是已授权地址
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        "/user/register",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
                        securityProperties.getBrowser().getSignOutUrl()
                ).permitAll()
                // 对所有/user/*的get请求必须具有ROLE_ADMIN权限
                .antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")
                // 对其他所有请求
                .anyRequest()
                // 都需要授权
                .authenticated()

                .and()
                // 关闭跨域防护伪造
                .csrf().disable();
    }
}
