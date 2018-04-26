package com.jerry.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 9:07
 * Description: 图形验证码
 */
@Data
@AllArgsConstructor
public class ImageCode {

    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime;

    /**
     * 传将在多少秒过期的构造方法
     *
     * @param image
     * @param code
     * @param expireIn
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        // 调用3个标准的构造方法
        this(image, code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
