package com.suddenly.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;


@SpringBootTest
class AutoLoginServiceImplTest {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void test() {

        Jedis jedis = new Jedis("101.133.174.185", 6379);
        jedis.auth("98100815");
        System.out.println(jedis.ping());

        redisTemplate.opsForValue().set("key1", "test");
        System.out.println(redisTemplate.opsForValue().get("key1"));


    }

}