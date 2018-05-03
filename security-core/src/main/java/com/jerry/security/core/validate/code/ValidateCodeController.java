package com.jerry.security.core.validate.code;

import com.jerry.security.core.properties.SecurityConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 9:17
 * Description: 验证码Controller
 */
@RestController
@Data
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    /**
     * 操作Session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String type) throws Exception {

        // 根据请求类型从处理器Map中找出对应的处理器进行创建验证码的操作，包括(生成、存储、发送)
        validateCodeProcessorMap.get(type + SecurityConstants.VALIDATE_CODE_PROCESSOR_SUFFIX)
                .create(new ServletWebRequest(request, response));
    }

}
