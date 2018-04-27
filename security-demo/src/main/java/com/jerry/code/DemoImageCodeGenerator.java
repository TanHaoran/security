package com.jerry.code;

import com.jerry.security.core.validate.code.image.ImageCode;
import com.jerry.security.core.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 11:12
 * Description: 自定义的验证码生成器
 */
// @Component("imageCodeGenerator")
@Slf4j
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        log.info("更高级的图形验证码生成代码");
        return null;
    }
}
