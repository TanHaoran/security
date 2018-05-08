package com.jerry.security.app;

import com.jerry.security.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/8
 * Time: 10:17
 * Description: 实现了BeanPostProcessor接口就可以在Spring所有的Bean初始化之前和之后都要经过接口中的两个方法
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 这里处理的目的是：如果是app模块，就要替换掉MySpringSocialConfigurer的实现，使之调用一个服务进行操作而不是一个页面
        if (StringUtils.equals(beanName, "mySocialSecurityConfigurer")) {
            MySpringSocialConfigurer configurer = (MySpringSocialConfigurer) bean;
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
