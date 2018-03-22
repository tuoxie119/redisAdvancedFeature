package com.fengma.redis.transaction;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

/**
 * Created by liys on 2018/2/26.
 */
public class TransactionTest {
    private static final String transactionRedisCommitKey = "tansactionCommitKey";
    private static final String transactionRedisRollbackKey = "tansactionRollbackKey";
    public static void main(String[] args) {

        JedisPool pool = new JedisPool(new JedisPoolConfig(), "192.168.222.188",6379);
        //模拟两个客户端
        Jedis jedisA = pool.getResource();
        Jedis jedisB = pool.getResource();
        //事务提交,transactionRedisCommitKey变为2
        jedisA.set(transactionRedisCommitKey,"hello");
        jedisA.watch(transactionRedisCommitKey);
        Transaction transaction =jedisA.multi();
        transaction.set(transactionRedisCommitKey,"1");
        transaction.incr(transactionRedisCommitKey);
        transaction.exec();

        //事务回滚,transactionRedisRollbackKey依然为hello
        jedisB.set(transactionRedisRollbackKey,"hello");
        jedisB.watch(transactionRedisRollbackKey);
        Transaction transactionB =jedisB.multi();
        transactionB.set(transactionRedisRollbackKey,"1");
        transactionB.incr(transactionRedisRollbackKey);
        transactionB.discard();
        jedisB.close();

        //当exec时候如果监视的key从调用watch后发生过变化，则整个事务会失败
        Jedis jedisC = pool.getResource();
        Jedis jedisD = pool.getResource();
        jedisC.watch("test");
        Transaction transactionC =jedisC.multi();
        transactionC.set("test","transactionC");
        //改变被监控的值，导致事务失败
        jedisD.set("test","transactionD");
        transactionC.exec();

    }
}
