package com.fyf.dao.Impl;

import com.fyf.dao.SecKillDaoCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * Created by Edison on 2018/10/12.
 */
@Repository
public class SecKillDAOImpl implements SecKillDaoCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * 使用存储过程执行
     *
     * @param paramMap
     */
    @Override
    public void killByProcedure(Map paramMap) {
        this.em.createStoredProcedureQuery("execute_seckill");
    }

}


/**
 * @Repository注解在持久层中，具有将数据库操作抛出的原生异常翻译转化为spring的持久层异常的功能。
 */
