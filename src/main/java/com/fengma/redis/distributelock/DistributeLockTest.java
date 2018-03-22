package com.fengma.redis.distributelock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liys on 2018/2/16.
 */
public class DistributeLockTest {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxIdle(100);

        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(10000);

        // 设置空间连接
        config.setMaxIdle(100);

        // 创建连接池
        JedisPool pool = new JedisPool(config, "192.168.222.188",6379);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i=0;i<1000;i++){
            DistributeLockClientThread distributeLockClientThread = new DistributeLockClientThread(String.valueOf(i),pool.getResource());
            executorService.execute(distributeLockClientThread);
        }
        executorService.shutdown();
    }
}
