package com.fengma.redis.expireEvent;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by liys on 2018/2/22.
 */
public class TestJedis {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.222.188",6379);
        Jedis jedis = pool.getResource();
        jedis.select(0);
        jedis.set("notify", "Äã»¹ÔÚÂð");
        jedis.expire("notify", 10);

        jedis.select(1);
        jedis.set("test","test");
        jedis.expire("test", 20);
    }
}
