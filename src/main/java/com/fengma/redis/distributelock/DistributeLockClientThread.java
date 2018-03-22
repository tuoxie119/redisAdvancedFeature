package com.fengma.redis.distributelock;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * Created by liys on 2018/2/16.
 */
public class DistributeLockClientThread implements Runnable {
    private String requestId;
    private Jedis jedis;

    public DistributeLockClientThread(String requestId, Jedis jedis) {
        this.requestId = requestId;
        this.jedis = jedis;
    }

    public void run() {
        boolean flag = RedisTool.tryGetDistributedLock(jedis,"distributelock",requestId,5000);
        if(flag){
            System.out.println(requestId +"==get lock success");
        }else {
            System.out.println(requestId +"==get lock fail");
        }

        try {
            Random random = new Random();
            Thread.sleep(1000* random.nextInt(4));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean releaseFlag = RedisTool.releaseDistributedLock(jedis,"distributelock",requestId);
        if(releaseFlag){
            System.out.println(requestId +"==release lock success");
        }else {
            System.out.println(requestId +"==release lock fail");
        }

        jedis.close();
    }
}
