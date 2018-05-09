package com.jerry.security.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/9
 * Time: 21:34
 * Description:
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principle = authentication.getPrincipal();

        boolean hasPermission = false;

        // 因为principle可能是一个字符串，也就是一个匿名的Authentication，这里要判断principle必须UserDetails的认证后的Authentication
        if (principle instanceof UserDetails) {
            String username = ((UserDetails) principle).getUsername();

            // 这里应当读取用户所拥有权限的所有URL
            Set<String> urls = new HashSet<>();

            for (String url : urls) {
                // 如果有权限匹配就返回
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

        }
        return hasPermission;
    }
}
