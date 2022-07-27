package com.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.function.Consumer;

public class TestTransaction {
    public static void main(String[] args) {
        // 連接redis服務端
        try (JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "redis-lottery-dev.caynne.com", 6379)) {
            System.out.println("redis 連線成功");
            Jedis jedis = jedisPool.getResource();

            // transaction 是原子性，做法是用 queue
            // jedis.watch("xxx", "ooo"); // 可以不先有這些 key
            Transaction transaction = jedis.multi();
            try {
//                transaction.set("k2", "v2");
//                transaction.set("k3", "v3");
                Consumer<Transaction> function = t -> t.mset("k1", "v1", "k2", "v2");
                function.accept(transaction);
                transaction.exec();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                transaction.discard();
            }
            System.out.println(jedis.get("k1"));
            System.out.println(jedis.get("k2"));
        }
    }
}
