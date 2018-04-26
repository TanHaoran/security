package com.jerry.security.core.validate.code;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 9:17
 * Description: 校验码Controller
 */
@RestController
@Data
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    /**
     * 操作Session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 根据随机数生成图片
        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));

        // 2. 将随机数存到Session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);

        // 3. 将生成的图片写到接口的响应中
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }


}
