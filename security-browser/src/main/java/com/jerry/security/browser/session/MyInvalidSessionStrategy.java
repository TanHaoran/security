package com.jerry.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/6
 * Time: 11:21
 * Description: 当Session过期的处理策略
 */
public class MyInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public MyInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        onSessionInvalid(request, response);
    }

}
