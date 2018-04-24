package com.jerry.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.jerry.validator.MyConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 15:13
 * Description: 用户类
 */
@Data
public class User {

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @JsonView(UserSimpleView.class)
    private String id;

    @JsonView(UserSimpleView.class)
    @MyConstraint(message = "这是一个测试")
    private String username;

    @JsonView(UserDetailView.class)
    // 校验不允许为空
    @NotBlank(message = "密码不能为空")
    private String password;

    @JsonView(UserSimpleView.class)
    // 必须是过去的时间
    @Past(message = "生日必须是过去的时间")
    private Date birthday;
}

