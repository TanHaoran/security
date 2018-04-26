package com.jerry.security.core.validate.code;

import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/26
 * Time: 9:32
 * Description: 校验码过滤器，继承的OncePerRequestFilter保证这个过滤器只被调用一次
 */
@Setter
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 首先只有登录的请求并且是post请求才会进入这个过滤器
        if (StringUtils.equals("/authentication/form", request.getRequestURI())
                && StringUtils.equalsIgnoreCase("post", request.getMethod())) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                // 如果失败后就要直接返回，不走后面的过滤器
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 校验验证码
     *
     * @param request
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        // 从Session获取访问signIn.html页面生成的验证码
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request,
                ValidateCodeController.SESSION_KEY);

        // 从请求中取出用户输入的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            // 如果过期了就要从Session中移除验证码
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        // 如果验证都成功，就从session中移除验证阿妈
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }
}
