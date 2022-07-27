package com.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.function.Consumer;

public class TestPipeLine {
    public static void main(String[] args) {
        // 連接redis服務端
        try (JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "redis-lottery-dev.caynne.com", 6379)) {
            System.out.println("redis 連線成功");
            Jedis jedis = jedisPool.getResource();

            // pipeline 不是原子性，中間可被穿插其他命令
            Pipeline pipelined = jedis.pipelined();
//            pipelined.set("k2", "v2");
//            pipelined.set("k3", "v3");
            Consumer<Pipeline> function = p -> p.mset("k1", "v1", "k2", "v2");
            function.accept(pipelined);

            // pipelined.sync();
            // pipelined.close(); close 等同 clear
            pipelined.clear(); // clear 已包括 sync()

            System.out.println(jedis.get("k1"));
            System.out.println(jedis.get("k2"));
        }
    }
}
