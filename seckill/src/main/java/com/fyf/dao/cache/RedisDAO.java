package com.fyf.dao.cache;

import com.fyf.bean.SecKill;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Edison on 2018/10/11.
 */
public class RedisDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);

    public RedisDAO(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public SecKill getSecKill(long secKillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "secKill:" + secKillId;
                byte[] bytes = jedis.get(key.getBytes());
                //缓存重新获取到
                if (bytes != null) {
                    SecKill secKill = null;
                    ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
                    //对象被反序列化
                    return secKill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSecKill(SecKill secKill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "secKill:" + secKill.getSecKillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int tineOut = 60 * 60;
                String result = jedis.setex(key.getBytes(), tineOut, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }
}


/**
 * 1.对象以字节码存储在redis
 * 对象被序列化为字节码数组bytes
 * set(key.getBytes(),bytes)
 * get(key.getBytes())
 * <p>
 * 2.最佳序列化工具：谷歌自研Protostuff。bytes为对象被序列化后的字节数组
 * LinkedBuffer：当对象比较大的时候用缓冲区
 * private RuntimeSchema<SecKill> schema = RuntimeSchema.createFrom(SecKill.class);
 * byte[] bytes = ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
 */
