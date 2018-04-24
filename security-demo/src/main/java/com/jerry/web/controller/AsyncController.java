package com.jerry.web.controller;

import com.jerry.web.async.DeferredResultHolder;
import com.jerry.web.async.MockQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 21:26
 * Description:
 */
@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping("/sync")
    public String sync() throws InterruptedException {
        log.info("【同步处理】主线程开始");

        Thread.sleep(1000);

        log.info("【同步处理】主线程返回");

        return "success";
    }

    @RequestMapping("/async")
    public Callable<String> async() {
        log.info("【异步处理】主线程开始");

        Callable<String> result = () -> {
            log.info("【异步处理】副线程开始");
            Thread.sleep(1000);
            log.info("【异步处理】副线程返回");
            return "success";
        };

        log.info("【异步处理】主线程返回");

        return result;
    }

    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {
        log.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        log.info("主线程返回");

        return result;
    }

}
