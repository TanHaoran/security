package com.jerry.security.core.validate.code.sms;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 9:43
 * Description: 短信验证码发送器
 */
public interface SmsCodeSender {

    /**
     * 发送短信
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
