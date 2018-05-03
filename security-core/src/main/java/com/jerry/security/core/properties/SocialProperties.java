package com.jerry.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/3
 * Time: 21:26
 * Description: 社交属性类
 */
@Data
public class SocialProperties {
    private QQProperties qq = new QQProperties();
}
