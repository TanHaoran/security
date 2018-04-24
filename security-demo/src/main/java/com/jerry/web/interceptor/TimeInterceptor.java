package com.jerry.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 19:18
 * Description: 拦截器，比Filter的优势在于它有一个Handler，它不但会拦截自定义的Controller，也会拦截Spring自己提供的Controller，缺点是
 * 拿不到方法中参数的值
 */
// 这里不但要声明Component组件，而且还需要进行配置才能起作用，需要配置一个继承自WebMvcConfigurerAdapter的类，复写addInterceptors方法
// 如果请求中发生异常，那么就会从preHandle直接进入到afterHandle中，不会执行postHandle
@Component
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("【拦截器】preHandle");

        log.info("【拦截器】" + ((HandlerMethod) handler).getBean().getClass().getName());

        log.info("【拦截器】" + ((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("startTime", new Date().getTime());
        // 这里如果返回false就不会调用后面的postHandle方法和afterCompletion方法
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {
        log.info("【拦截器】postHandle");

        long start = (long) request.getAttribute("startTime");

        log.info("【拦截器】time interceptor 耗时: " + (new Date().getTime() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        log.info("【拦截器】afterCompletion");

        long start = (long) request.getAttribute("startTime");

        log.info("【拦截器】time interceptor 耗时: " + (new Date().getTime() - start));

        // 如果有异常，e就会保存异常信息
        // 注意：如果有@ControllerAdvice标注的类处理掉异常后，这个ex就不会有异常信息了
        log.info("【拦截器】exception is " + ex);
    }

}
