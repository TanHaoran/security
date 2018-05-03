package com.jerry.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 9:44
 * Description: 默认的短信发送器，如果有更好的实现方式，需要实现SmsCodeSender接口就可以（因为ValidateCodeBeanConfig中定义的是Class)
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("向手机【{}】发送短信验正码【{}】", mobile, code);
    }
}
