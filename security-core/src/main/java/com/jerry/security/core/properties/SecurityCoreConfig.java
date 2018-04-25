package com.jerry.security.core.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/25
 * Time: 14:52
 * Description: core模块配置类
 */
@Configuration
// @EnableConfigurationProperties表示让SecurityProperties配置类生效
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
