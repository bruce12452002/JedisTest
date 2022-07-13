package com.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SubThread extends Thread {
    private final JedisPool jedisPool;
    private final Subscriber subscriber = new Subscriber();

    private final String channel = "mychannel";

    public SubThread(JedisPool jedisPool) {
        super("Subscriber");
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        // 注意：subscribe是一個阻塞的方法，在取消訂閱該頻道前，thread會一直阻塞在這，無法執行後面的 Code
        System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", channel));

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();   /* 取出一个連線*/
            jedis.subscribe(subscriber, channel);    //通過subscribe 的api去訂閱，傳入參數為訂閱者和頻道名
        } catch (Exception e) {
            System.out.printf("subscribe channel error, %s%n", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
