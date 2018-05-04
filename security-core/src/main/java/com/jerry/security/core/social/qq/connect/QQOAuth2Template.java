package com.jerry.security.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/4
 * Time: 10:13
 * Description:
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 因为在OAuth2Template中的exchangeForAccess换取令牌的时候，只有useParametersForClientAuthentication为true的时候
        // 才会传递client_id和client_secret的值。
        // 所以这里需要设置setUseParametersForClientAuthentication为true，才能在发请求的时候带着client_id和client_secret的值
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 重新覆盖createRestTemplate()方法，添加能处理text/html的Converter
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        // 添加能处理text/html响应的的Converter
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    /**
     * 默认的postForAccessGrant()方法在获取accessToken的请求后，拿到的是一个Map，然后从中获取信息。
     * 而QQ返回的JSON是一个长字符串，所以需要覆盖这个方法，从中获取需要的信息。
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseString = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取accessToken的响应是:" + responseString);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseString, "&");

        // 令牌、过期时间和刷新令牌
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }
}
