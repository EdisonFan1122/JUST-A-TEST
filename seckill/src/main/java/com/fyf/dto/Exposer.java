package com.fyf.dto;

import lombok.Data;

/**
 * 暴露秒杀地址dto
 * Created by Edison on 2018/10/10.
 */
@Data
public class Exposer {

    /**
     * 是否开启秒杀
     */
    private boolean exposed;

    /**
     * 加密算法
     */
    private String md5;

    private long secKillId;

    private long now;

    /**
     * 开始时间（ms）
     */
    private long start;

    private long end;

    public Exposer(boolean exposed, String md5, long secKillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.secKillId = secKillId;
    }

    public Exposer(boolean exposed, long secKillId,long now, long start, long end) {
        this.exposed = exposed;
        this.secKillId = secKillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long secKillId) {
        this.exposed = exposed;
        this.secKillId = secKillId;
    }
}
