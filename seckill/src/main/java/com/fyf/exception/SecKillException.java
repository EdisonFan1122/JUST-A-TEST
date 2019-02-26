package com.fyf.exception;

/**
 * 秒杀相关异常
 * Created by Edison on 2018/10/10.
 */
public class SecKillException extends RuntimeException{

    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
