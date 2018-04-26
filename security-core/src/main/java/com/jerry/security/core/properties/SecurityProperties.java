package com.jerry.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/25
 * Time: 14:49
 * Description: 主属性配置类
 */
@Data
@ConfigurationProperties(prefix = "jerry.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();
}
