package com.jerry.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 9:44
 * Description:
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("向手机【{}】发送短信验正码【{}】", mobile, code);
    }
}
