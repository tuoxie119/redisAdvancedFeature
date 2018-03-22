package com.fengma.redis.expireEvent;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by liys on 2018/2/22.
 */
public class Subscriber {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.222.188",6379);
        Jedis jedis = pool.getResource();
        //只订阅patten匹配在超时事件
        jedis.psubscribe(new KeyExpiredListener(), "__key*@0__:expired");
     }
}
