package com.jerry.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/25
 * Time: 10:24
 * Description: 自定义获取用户信息服务，在这个类使用@Autowired就可以注入JPA或者Mapper根据进行数据查询操作返回用户信息
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    /**
     * 注入在BrowserSecurityConfig中加入@Bean的PasswordEncoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登陆用户名:" + username);
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登陆用户Id:" + userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String username) {

        // 这里写根据用户名查找用户信息操作

        // 根据查找到的用户信息判断用户是否被冻结

        String password = passwordEncoder.encode("123456");
        log.info("数据库密码:" + password);

        // 这里对权限字符串定义要在配置文件中指定的角色前面加"ROLE_"
        return new SocialUser(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
    }
}
