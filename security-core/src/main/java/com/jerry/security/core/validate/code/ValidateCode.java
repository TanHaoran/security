package com.jerry.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/27
 * Time: 9:27
 * Description: 验证码
 */
@Data
@AllArgsConstructor
public class ValidateCode implements Serializable {

    private String code;
    private LocalDateTime expireTime;

    /**
     * 传将在多少秒过期的构造方法
     *
     * @param code
     * @param expireIn
     */
    public ValidateCode(String code, int expireIn) {
        // 调用2个标准的构造方法
        this(code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}