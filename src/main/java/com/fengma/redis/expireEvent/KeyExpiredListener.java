package com.fengma.redis.expireEvent;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by liys on 2018/2/22.
 */
public class KeyExpiredListener extends JedisPubSub {
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage pattern " + pattern + " " + channel + " " + message);
    }
}
