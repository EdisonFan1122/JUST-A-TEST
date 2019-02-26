package com.fyf.exception;

/**
 * 重复秒杀异常，运行期异常
 * Created by Edison on 2018/10/10.
 */
public class RepeatKillException extends SecKillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
