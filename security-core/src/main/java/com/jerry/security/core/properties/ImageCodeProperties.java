package com.jerry.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 10:20
 * Description: 图形验证码属性类
 */
@Data
public class ImageCodeProperties {

    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expireIn = 60;

    /**
     * 使用逗号隔开的多个URL，注意不要有空格
     */
    private String url = "";
}
