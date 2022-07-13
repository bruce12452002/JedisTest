package com.example.demo;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestMainSub {
    public static void main(String[] args) {
        // 連接redis服務端
        JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "redis-lottery-dev.caynne.com", 6379);
        System.out.println("redis 連線成功");

        Publisher publisher = new Publisher(jedisPool);    //發布者
        publisher.start();

        SubThread subThread = new SubThread(jedisPool);    //訂閱者
        subThread.start();
    }
}
