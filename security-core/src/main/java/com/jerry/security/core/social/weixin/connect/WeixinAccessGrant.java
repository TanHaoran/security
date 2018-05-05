package com.jerry.security.core.social.weixin.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/5
 * Time: 14:47
 * Description: 因为微信和标准的AccessGrant不一样，多了一个openId，这里需要覆盖AccessGrant，添加openId
 */
@Data
public class WeixinAccessGrant extends AccessGrant {

    private String openId;

    public WeixinAccessGrant() {
        super("");
    }

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

}
