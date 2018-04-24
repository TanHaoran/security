package com.jerry.handler;

import com.jerry.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 17:38
 * Description: 控制器异常处理类
 */
// 表明是处理Controller异常的
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 当任何控制器中抛出UserNotExistException都会进入这个方法中
     *
     * @param exception
     * @return
     */
    // 表明处理的是什么异常
    @ExceptionHandler(UserNotExistException.class)
    // 表示将返回类型转成一个json
    @ResponseBody
    // 返回错误码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotExistException(UserNotExistException exception) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", exception.getId());
        result.put("message", exception.getMessage());
        return result;
    }
}
