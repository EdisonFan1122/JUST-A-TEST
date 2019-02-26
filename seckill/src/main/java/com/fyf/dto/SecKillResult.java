package com.fyf.dto;

import lombok.Data;

/**
 * 所有ajax请求放回类型,封装json结果
 * Created by Edison on 2018/10/11.
 */
@Data
public class SecKillResult<T> {

    private boolean success;

    private T data;

    private String error;

    public SecKillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SecKillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

}
