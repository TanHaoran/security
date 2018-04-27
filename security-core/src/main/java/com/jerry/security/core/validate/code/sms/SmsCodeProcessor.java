package com.jerry.security.core.validate.code.sms;

import com.jerry.security.core.validate.code.ValidateCode;
import com.jerry.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 11:24
 * Description: 短信验证码处理器，生成和存储的逻辑抽象类已经实现了，这里只实现发送的逻辑
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode smsCode) throws Exception {
        // 读取请求中的手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");

        // 发送短信验证码
        smsCodeSender.send(mobile, smsCode.getCode());
    }
}
