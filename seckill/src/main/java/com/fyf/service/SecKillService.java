package com.fyf.service;

import com.fyf.bean.SecKill;
import com.fyf.dto.Exposer;
import com.fyf.dto.SecKillExecution;
import com.fyf.exception.RepeatKillException;
import com.fyf.exception.SecKillCloseException;
import com.fyf.exception.SecKillException;

import java.util.List;

/**
 * Created by Edison on 2018/10/11.
 */
public interface SecKillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSecKillList();

    /**
     * 查询单个秒杀记录
     * @param secKillId
     * @return
     */
    SecKill getById(long secKillId);

    /**
     * 秒杀开启输出秒杀接口地址,
     * 否则输出系统时间和秒杀时间
     *
     * @param secKillId
     */
    Exposer exportSecKillUrl(long secKillId);

    /**
     * 执行秒杀操作
     * @param secKillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSecKill(long secKillId, long userPhone, String md5)
            throws SecKillException,RepeatKillException,SecKillCloseException;

    /**
     * 执行秒杀操作by 存储过程
     * @param secKillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSecKillProcedure(long secKillId, long userPhone, String md5);


}
