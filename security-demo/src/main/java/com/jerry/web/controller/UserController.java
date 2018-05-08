package com.jerry.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jerry.dto.User;
import com.jerry.dto.UserQueryCondition;
import com.jerry.exception.UserNotExistException;
import com.jerry.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 15:24
 * Description: 用户控制器
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

//    @Autowired
//    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 普通查询，返回一个集合
     *
     * @return
     */
    @GetMapping("/query")
    @JsonView(User.UserSimpleView.class)
    public List<User> query() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    /**
     * 带参数的查询，参数可以不传，但有默认值
     *
     * @param username
     * @return
     */
    @GetMapping("/queryParam")
    @JsonView(User.UserSimpleView.class)
    public User queryParam(@RequestParam(required = false, defaultValue = "guest", name = "username")
                                   String username) {
        log.info("用户名:{}", username);
        User user = new User();
        user.setUsername(username);
        return user;
    }

    /**
     * 条件参数查询
     *
     * @param condition
     * @return
     */
    @GetMapping("/queryCondition")
    public UserQueryCondition queryCondition(UserQueryCondition condition) {
        log.info("条件:{}", condition.toString());
        return condition;
    }

    /**
     * 分页查询，带有默认值
     *
     * @param condition
     * @param pageable
     * @return
     */
    @GetMapping("/queryPage")
    @JsonView(User.UserSimpleView.class)
    public List<User> queryPage(UserQueryCondition condition,
                                @PageableDefault(page = 1, size = 10, sort = "ageFrom") Pageable pageable) {
        log.info("每页条数:{}", pageable.getPageSize());
        log.info("页数:{}", pageable.getPageNumber());
        log.info("排序字段:{}", pageable.getSort());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    /**
     * 只能传数字的请求
     *
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable(name = "id") String id) {
        User user = new User();
        user.setUsername("Jerry");
        return user;
    }

    /**
     * 创建用户
     *
     * @param user
     * @param errors
     * @return
     */
    @PostMapping
    // 使用@RequestBody才能正确接收字段对应值
    // 如果想要捕捉校验错误信息，那么必须加入BindingResult对象
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error ->
                    log.info("【错误信息】" + error.getDefaultMessage()));
        }
        log.info("id={}", user.getId());
        log.info("username={}", user.getUsername());
        log.info("birthday={}", user.getBirthday());
        user.setId("1");
        return user;
    }

    /**
     * 修改用户
     *
     * @param user
     * @param errors
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                log.info("【错误字段】{},【错误信息】{}", fieldError.getField(), error.getDefaultMessage());
            });
        }

        log.info("id={}", user.getId());
        log.info("username={}", user.getUsername());
        log.info("password={}", user.getPassword());
        log.info("birthday={}", user.getBirthday());
        user.setId("1");
        return user;
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        log.info("id={}", id);
    }

    /**
     * 抛出异常的请求
     *
     * @param id
     * @return
     */
    @GetMapping("/error/{id:\\d+}")
    public User error(@PathVariable("id") String id) {
        throw new UserNotExistException(id);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/me")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping("/authentication")
    public Object getAuthentication(Authentication authentication, HttpServletRequest request)
            throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        // 验签的时候设置密钥并获取自定义信息
        Claims claims = Jwts.parser()
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        String company = (String) claims.get("company");

        log.info("公司名称:" + company);

        return authentication;
    }

    /**
     * 获取当前用户UserDetails信息
     *
     * @return
     */
    @GetMapping("/userDetails")
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    /**
     * 使用社交账号登录后的注册
     *
     * @param user
     */
    @PostMapping("/register")
    public void register(User user, HttpServletRequest request) {
        // 无论是注册还是绑定，都会拿到一个唯一标识
        String userId = user.getUsername();
        // 设置两种浏览器和App社交登录后注册的不同的处理类型
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        // appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
    }
}
