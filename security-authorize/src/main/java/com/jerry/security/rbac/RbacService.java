package com.jerry.security.rbac;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/9
 * Time: 21:28
 * Description:
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
