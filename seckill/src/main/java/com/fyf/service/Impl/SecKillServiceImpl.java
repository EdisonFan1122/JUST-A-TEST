package com.fyf.service.Impl;

import com.fyf.bean.SecKill;
import com.fyf.bean.SuccessKilled;
import com.fyf.dao.SecKillDAO;
import com.fyf.dao.SuccessKilledDAO;
import com.fyf.dao.cache.RedisDAO;
import com.fyf.dto.Exposer;
import com.fyf.dto.SecKillExecution;
import com.fyf.enums.SecKillStatEnum;
import com.fyf.exception.RepeatKillException;
import com.fyf.exception.SecKillCloseException;
import com.fyf.exception.SecKillException;
import com.fyf.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Edison on 2018/10/10.
 */
@Slf4j
@Service
public class SecKillServiceImpl implements SecKillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * md5盐值字符串，用于混淆MD5
     */
    private final String slat = "ajhdsg!!@@#$q7872364872@#@#@tqwerqweweweqwEQWEr#";

    @Autowired
    private SuccessKilledDAO successKilledDAO;

    @Autowired
    private SecKillDAO secKillDAO;

    @Autowired
    private RedisDAO redisDAO;

    @Override
    public List<SecKill> getSecKillList() {
        return secKillDAO.queryAll(0, 4);
    }

    @Override
    public SecKill getById(long secKillId) {
        return secKillDAO.findBySecKillId(secKillId);
    }

    @Override
    public Exposer exportSecKillUrl(long secKillId) {
        //超时的基础上维护一致性
        SecKill secKill = redisDAO.getSecKill(secKillId);
        if (secKill == null) {
            secKill = secKillDAO.findBySecKillId(secKillId);
            if (secKill == null) {
                return new Exposer(false, secKillId);
            } else {
                redisDAO.putSecKill(secKill);
            }
        }

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, secKillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程，不可逆，MD5
        String md5 = getMd5(secKillId);
        return new Exposer(true, md5, secKillId);
    }

    private String getMd5(long secKillId) {
        String base = secKillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    /**
     * 使用注解控制事务方法的优点:
     * 1:开发团队达成一致约定,明确标注事务方法的编程风格。
     * 2:保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部.
     * 3:不是所有的方法都需要事务,如只有一条修改操作,只读操作不需要事务控制.
     */
    @Transactional
    @Override
    public SecKillExecution executeSecKill(long secKillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        if (md5 == null || md5.equals(getMd5(secKillId))) {
            throw new SecKillException("secKill data rewrite");
        }
        //执行秒杀业务逻辑：减库存，记录购买行为
        Date nowTime = new Date();

        try {
            //记录购买行为
            int insertCount = successKilledDAO.insertSuccessKilled(secKillId, userPhone);
            //唯一:secKillId,userPhone
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存,热点商品竞争
                int updateCount = secKillDAO.reduceNumber(secKillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录，秒杀结束,rollback
                    throw new SecKillCloseException("seckill is closed");
                } else {
                    //秒杀成功 commit
                    SuccessKilled successKilled = successKilledDAO.queryByIdWithSecKill(secKillId, userPhone);
                    return new SecKillExecution(secKillId, SecKillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SecKillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常 转化为运行期异常   出错会回滚
            throw new SecKillException("SecKill inner error:" + e.getMessage());
        }
    }

    @Override
    public SecKillExecution executeSecKillProcedure(long secKillId, long userPhone, String md5) {
        if (md5 == null || md5.equals(getMd5(secKillId))) {
            return new SecKillExecution(secKillId, SecKillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("secKillId", secKillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            secKillDAO.killByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled sk = successKilledDAO.queryByIdWithSecKill(secKillId, userPhone);
                return new SecKillExecution(secKillId, SecKillStatEnum.SUCCESS, sk);
            } else {
                return new SecKillExecution(secKillId, SecKillStatEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SecKillExecution(secKillId, SecKillStatEnum.INNER_ERROR);
        }
    }
}


/**
 * spring boot事务管理
 *
 * @Transactional( value = "transactionManagerPrimary",
 * isolation = Isolation.DEFAULT,
 * propagation = Propagation.REQUIRED)
 * value : 多数据源时，指定配置的事务管理器名
 * isolation : 事务的隔离级别
 * propagation ： 事物的传播行为
 * <p>
 * 5个隔离级别：
 * public enum Isolation {
 * DEFAULT(-1),
 * READ_UNCOMMITTED(1),
 * READ_COMMITTED(2),
 * REPEATABLE_READ(4),
 * SERIALIZABLE(8);
 * }
 * DEFAULT ：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是： READ_COMMITTED 。
 * READ_UNCOMMITTED ：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
 * READ_COMMITTED ：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
 * REPEATABLE_READ ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
 * SERIALIZABLE ：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
 * <p>
 * 7种传播行为
 * public enum Propagation {
 * REQUIRED(0),
 * SUPPORTS(1),
 * MANDATORY(2),
 * REQUIRES_NEW(3),
 * NOT_SUPPORTED(4),
 * NEVER(5),
 * NESTED(6);
 * }
 * REQUIRED ：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
 * SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
 * MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
 * REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
 * NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
 * NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
 * NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED
 */
