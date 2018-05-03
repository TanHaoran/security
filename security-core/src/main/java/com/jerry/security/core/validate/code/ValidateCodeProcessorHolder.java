package com.jerry.security.core.validate.code;

import com.jerry.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 16:15
 * Description: 验证码处理器持有类
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * 根据枚举类型获取对应的验证码处理器
     *
     * @param type 传入进来的类型已经是字符串小写的类型
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type + SecurityConstants.VALIDATE_CODE_PROCESSOR_SUFFIX;
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
