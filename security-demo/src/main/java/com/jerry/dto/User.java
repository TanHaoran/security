package com.jerry.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 15:13
 * Description: 用户类
 */
@Data
public class User {

    private String username;
    private String password;
}

