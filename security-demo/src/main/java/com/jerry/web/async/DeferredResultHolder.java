package com.jerry.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 21:41
 * Description: 模拟的在线程之间传递消息的对象
 */
@Component
@Data
public class DeferredResultHolder {

    // String放的是订单，DeferredResult放的就是订单的处理结果
    private Map<String, DeferredResult> map = new HashMap<>();
}
