package com.jerry.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 10:59
 * Description: 校验码生成器
 */
public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    ImageCode generate(ServletWebRequest request);
}
