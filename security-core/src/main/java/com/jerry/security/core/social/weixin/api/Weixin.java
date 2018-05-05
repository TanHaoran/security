package com.jerry.security.core.social.weixin.api;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/5
 * Time: 14:38
 * Description:
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
