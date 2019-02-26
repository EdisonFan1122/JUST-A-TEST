package com.fyf.exception;

/**
 * 秒杀关闭异常
 * Created by Edison on 2018/10/10.
 */
public class SecKillCloseException extends SecKillException {

    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
