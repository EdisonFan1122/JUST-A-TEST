package com.fyf.dao;

import java.util.Map;

/**
 * Created by Edison on 2018/10/12.
 */
public interface SecKillDaoCustom<T,ID> {

    /**
     * 使用存储过程执行
     *
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);

}
