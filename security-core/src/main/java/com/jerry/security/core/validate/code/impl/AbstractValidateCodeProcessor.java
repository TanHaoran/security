package com.jerry.security.core.validate.code.impl;

import com.jerry.security.core.validate.code.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 10:22
 * Description: 默认的验证码处理器实现类
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 这里是Spring的依赖搜索，启动的时候会查找Spring容器中所有的ValidateCodeGenerator实现，
     * 将他们的Bean的名字作为Key放进这个Map
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 1. 生成
        C validateCode = generate(request);

        // 2. 存储
        save(request, validateCode);

        // 3. 发送
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    /**
     * 第一步：生成验证码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 第二步：存储验证码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        // 保存进Session
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(),
                validateCode);
    }

    /**
     * 第三步：发送验证码
     *
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的URL获取验证码的类型：image或者sms
     *
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }


    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }
}
