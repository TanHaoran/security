package com.jerry.security.core.social.qq.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.security.core.social.qq.api.QQ;
import com.jerry.security.core.social.qq.api.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/3
 * Time: 16:59
 * Description: 获取QQ用户信息的实现类
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 根据用户access_token获取用户openId的URL
     */
    private static final String URL_GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 根据access_token、appId、openId获取用户信息的URL
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPEN_ID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("获取用户openId:" + result);

        openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        // 发送http请求获取用户信息
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("获取用户信息:" + result);

        QQUserInfo userInfo;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
