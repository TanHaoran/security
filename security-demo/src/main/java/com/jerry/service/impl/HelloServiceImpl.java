package com.jerry.service.impl;

import com.jerry.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 16:53
 * Description:
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        log.info("greeting");
        return "Hello, " + name;
    }
}
