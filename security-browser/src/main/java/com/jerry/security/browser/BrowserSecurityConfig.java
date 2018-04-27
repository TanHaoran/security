package com.jerry.security.browser;

import com.jerry.security.core.properties.SecurityProperties;
import com.jerry.security.core.validate.code.SmsCodeFilter;
import com.jerry.security.core.validate.code.ValidateCodeFilter;
import com.jerry.security.core.validate.code.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/24
 * Time: 17:21
 * Description: 浏览器项目主配置类
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 这里DateSource就会读取application.properties中配置的数据源信息
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 加密解密的工具类，这里可以定义我们自己实现加密解密的实现类，只需要实现PasswordEncoder接口就好
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

        // 创建验证码过滤器
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        // 调用afterPropertiesSet()初始化设置url集合
        validateCodeFilter.afterPropertiesSet();

        // 创建短信验证码过滤器
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        // 调用afterPropertiesSet()初始化设置url集合
        smsCodeFilter.afterPropertiesSet();

        http
                // 将自定义的验证码过滤器加在UsernamePasswordAuthenticationFilter之前做判断
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 将自定义的短信验证码过滤器加在UsernamePasswordAuthenticationFilter之前做判断
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)

                // formLogin()是表单登录,httpBasic()是默认的弹窗登录
                .formLogin()
                .loginPage("/authentication/require")
                // 告诉UsernamePasswordAuthenticationFilter处理下面这个请求
                .loginProcessingUrl("/authentication/form")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)

                // 记住我的配置
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                // 指定获取UserDetails的类
                .userDetailsService(userDetailsService)

                .and()
                // authorizeRequests后面跟的都是已授权地址
                .authorizeRequests()
                .antMatchers(
                        "/authentication/require",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*"
                ).permitAll()
                // 对其他所有请求
                .anyRequest()
                // 都需要授权
                .authenticated()

                .and()
                // 关闭跨域防护伪造
                .csrf().disable()

                // 将短信验证码配置加进浏览器配置中
                .apply(smsCodeAuthenticationSecurityConfig);
    }
}
