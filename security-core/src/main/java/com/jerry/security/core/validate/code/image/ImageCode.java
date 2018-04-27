package com.jerry.security.core.validate.code.image;

import com.jerry.security.core.validate.code.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 9:07
 * Description: 图形验证码
 */
@Data
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    /**
     * 传将在多少秒后过期的构造方法
     *
     * @param image
     * @param code
     * @param expireIn 多少秒后过期
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }
}
