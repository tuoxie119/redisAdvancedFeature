package com.fengma.redis.queue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by liys on 2018/1/30.
 */
public class IdConsumerThread implements Runnable {
    private Jedis jedis;
    private int i;
    public IdConsumerThread(JedisPool pool, int i){
        this.jedis = pool.getResource();
        this.i = i;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String key = jedis.lpop("task-queue");
        System.out.println("线程="+this.i + " key="+key);
        jedis.close();
    }
}
