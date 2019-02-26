package com.fyf.dao;

import com.fyf.bean.SecKill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Edison on 2018/10/10.
 */
@Repository
public interface SecKillDAO extends JpaRepository<SecKill, Integer>,SecKillDaoCustom<SecKill, Integer> {

    /**
     * 减库存
     *
     * @param secKillId
     * @param killTime
     * @return 如果影响行数>1，表示更新的记录行数
     */
    @Query(value = "UPDATE seckill" +
            "        SET number = number - 1" +
            "        WHERE seckill_id = ?1" +
            "        AND start_time <![CDATA[ <= ]]> ?2" +
            "        AND end_time >= ?2" +
            "        AND NUMBER > 0", nativeQuery = true)
    int reduceNumber(long secKillId, Date killTime);

    /**
     * 根据ID查询秒杀对象
     *
     * @param secKillId
     * @return
     */
    SecKill findBySecKillId(long secKillId);

    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    @Query(value = " SELECT seckill_id,name,number,start_time,end_time,create_time" +
            "        FROM seckill" +
            "        ORDER BY create_time DESC" +
            "        LIMIT #{offset},#{limit}", nativeQuery = true)
    List<SecKill> queryAll(int offset, int limit);


}
