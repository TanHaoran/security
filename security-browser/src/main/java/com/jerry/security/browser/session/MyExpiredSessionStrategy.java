package com.jerry.security.browser.session;

import com.jerry.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/6
 * Time: 10:36
 * Description: 当Session并发时，后面Session踢掉前面Session，对前面用户做的处理策略
 */
public class MyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public MyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /**
     * 覆盖父类的方法，表示是由并发登录引起的
     * @return
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
