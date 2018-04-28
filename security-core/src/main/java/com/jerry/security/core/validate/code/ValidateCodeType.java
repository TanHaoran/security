package com.jerry.security.core.validate.code;

import com.jerry.security.core.properties.SecurityConstants;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 16:15
 * Description: 验证码类型枚举类
 */
public enum ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     *
     * @return
     */
    public abstract String getParamNameOnValidate();
}
