package com.jerry.security.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/25
 * Time: 14:41
 * Description: 简单返回对象
 */
@Data
@AllArgsConstructor
public class SimpleResponse {
    private Object content;
}
