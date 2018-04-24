package com.jerry.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 15:23
 * Description: User查询条件类
 */
@Data
@ToString
public class UserQueryCondition {

    private String username;

    /**
     * 年龄起始值
     */
    private Integer ageFrom;

    /**
     * 年龄终止值
     */
    private Integer ageTo;

    private String desc;
}
