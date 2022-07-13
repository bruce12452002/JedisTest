package com.example.demo;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {       // 收到消息會調用
        System.out.printf("receive redis published message, channel %s, message %s%n", channel, message);
        //this.unsubscribe();
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    // 訂閱頻道會調用
        System.out.printf("subscribe redis channel success, channel %s, subscribedChannels %d%n",
                channel, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   // 取消訂閱會調用
        System.out.printf("unsubscribe redis channel, channel %s, subscribedChannels %d%n",
                channel, subscribedChannels);

    }
}
