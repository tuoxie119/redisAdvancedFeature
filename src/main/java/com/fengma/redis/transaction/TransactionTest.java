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
        //ģ�������ͻ���
        Jedis jedisA = pool.getResource();
        Jedis jedisB = pool.getResource();
        //�����ύ,transactionRedisCommitKey��Ϊ2
        jedisA.set(transactionRedisCommitKey,"hello");
        jedisA.watch(transactionRedisCommitKey);
        Transaction transaction =jedisA.multi();
        transaction.set(transactionRedisCommitKey,"1");
        transaction.incr(transactionRedisCommitKey);
        transaction.exec();

        //����ع�,transactionRedisRollbackKey��ȻΪhello
        jedisB.set(transactionRedisRollbackKey,"hello");
        jedisB.watch(transactionRedisRollbackKey);
        Transaction transactionB =jedisB.multi();
        transactionB.set(transactionRedisRollbackKey,"1");
        transactionB.incr(transactionRedisRollbackKey);
        transactionB.discard();
        jedisB.close();

        //��execʱ��������ӵ�key�ӵ���watch�������仯�������������ʧ��
        Jedis jedisC = pool.getResource();
        Jedis jedisD = pool.getResource();
        jedisC.watch("test");
        Transaction transactionC =jedisC.multi();
        transactionC.set("test","transactionC");
        //�ı䱻��ص�ֵ����������ʧ��
        jedisD.set("test","transactionD");
        transactionC.exec();

    }
}
