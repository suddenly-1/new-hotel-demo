package com.suddenly.service.impl;

import com.suddenly.service.AutoLoginService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;


@SpringBootTest
class AutoLoginServiceImplTest {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void test() {

//        Jedis jedis = new Jedis("192.168.180.129", 6379);
//        jedis.auth("123456");
//        System.out.println(jedis.ping());

        redisTemplate.opsForValue().set("key3", "word");
        System.out.println(redisTemplate.opsForValue().get("key3"));


    }

}