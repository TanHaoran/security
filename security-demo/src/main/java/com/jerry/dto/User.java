package com.jerry.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    private String username;

    @JsonView(UserDetailView.class)
    // 校验不允许为空
    @NotBlank
    private String password;

    @JsonView(UserSimpleView.class)
    // 必须是过去的时间
    @Past
    private Date birthday;
}

