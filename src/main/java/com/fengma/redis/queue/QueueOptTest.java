package com.fengma.redis.queue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liys on 2018/1/30.
 */
public class QueueOptTest {

    public static void main(String[] args) {
        createData();
        consumerIds();

    }

    public static void createData(){
        Jedis jedis = new Jedis("192.168.222.188",6379);
        for (int i=0;i<676;i++){
            jedis.lpush("task-queue",String.valueOf(i));
        }

    }

    public static void consumerIds(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2000);
        config.setMaxIdle(2000);
        config.setMaxWaitMillis(50000);
        config.setMinIdle(1000);
        JedisPool pool = new JedisPool(config, "192.168.222.188",6379);
        ExecutorService executorService =Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new IdConsumerThread(pool,i));
        }
    }

}
