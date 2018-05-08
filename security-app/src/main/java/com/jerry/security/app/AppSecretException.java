package com.jerry.security.app;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/8
 * Time: 10:08
 * Description:
 */
public class AppSecretException extends RuntimeException {

    public AppSecretException(String message) {
        super(message);
    }
}
