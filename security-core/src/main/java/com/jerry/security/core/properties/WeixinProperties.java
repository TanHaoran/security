package com.jerry.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/5
 * Time: 14:21
 * Description:
 */
@Data
public class WeixinProperties extends SocialProperties {

    private String providerId = "weixin";
}
