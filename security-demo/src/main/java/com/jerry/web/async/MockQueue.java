package com.jerry.web.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 21:39
 * Description: 消息队列，当收到新订单的时候进行订单处理
 */
@Component
@Data
@Slf4j
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {

        new Thread(() -> {
            log.info("接到下单请求, " + placeOrder);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.completeOrder = placeOrder;

            log.info("下单请求处理完毕, " + placeOrder);
        }).start();
    }

}
