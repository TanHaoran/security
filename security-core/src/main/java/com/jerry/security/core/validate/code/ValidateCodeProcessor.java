package com.jerry.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 10:19
 * Description: 验证码处理器，封装了不同的验证码处理逻辑
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入Session中的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建验证码(生成、存储、发送)
     *
     * @param request Spring工具类，请求和响应都封装在了里面
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;
}
