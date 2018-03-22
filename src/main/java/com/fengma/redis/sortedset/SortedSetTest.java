package com.fengma.redis.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liys on 2018/3/7.
 */
public class SortedSetTest {

    private static final String KEY = "sorted_set";

    private static final String VALUE = "layman";

    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.222.188",6379);
        Jedis jedis = pool.getResource();
        jedis.select(0);
        ZADD(jedis);
    }

    public static void ZADD(Jedis jedis) {
        Map<String, Double> sourceMember = new HashMap<String, Double>();
        for (int i = 0; i < 3000; i++) {
            double score = new Random().nextInt(10000);
            sourceMember.put(VALUE + score, score);
        }
        jedis.zadd(KEY, sourceMember);
    }
}
