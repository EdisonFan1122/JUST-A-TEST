package com.fyf.dao;

import com.fyf.bean.SuccessKilled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Edison on 2018/10/10.
 */
@Repository
public interface SuccessKilledDAO extends JpaRepository<SuccessKilled, Integer> {

    /**
     * 插入购买明细，可过滤重复
     * @param secKillId
     * @param userPhone
     * @return 插入的行数
     */
    @Query(value = "insert ignore into success_killed(seckill_id,user_phone,state) values (#{secKillId},#{userPhone},0)",nativeQuery = true)
    int insertSuccessKilled (long secKillId,long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    @Query(value = "select" +
            "          sk.seckill_id," +
            "          sk.user_phone," +
            "          sk.create_time," +
            "          sk.state," +
            "          s.seckill_id," +
            "          s.name ," +
            "          s.number ," +
            "          s.start_time ," +
            "          s.end_time," +
            "          s.create_time" +
            "        from success_killed sk" +
            "        inner join seckill s on sk.seckill_id = s.seckill_id" +
            "        where sk.seckill_id=?1 and sk.user_phone=?2",nativeQuery = true)
    SuccessKilled queryByIdWithSecKill(long seckillId,long userPhone);

}
