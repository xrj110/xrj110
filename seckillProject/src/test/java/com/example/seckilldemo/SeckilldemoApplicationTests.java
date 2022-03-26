package com.example.seckilldemo;

import com.example.seckilldemo.utils.CookieUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SeckilldemoApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        System.out.println(CookieUtil.getDomainName("http://43.134.56.204:8080/login/toLogin"));
        System.out.println(CookieUtil.isIPAddressByRegex("43.134.56.204:8080"));
    }


}
