package com.jerry.exception;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/4/23
 * Time: 17:31
 * Description:
 */
@Data
public class UserNotExistException extends RuntimeException {

    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }
}
