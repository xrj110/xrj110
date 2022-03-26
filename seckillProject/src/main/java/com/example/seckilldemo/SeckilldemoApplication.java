package com.example.seckilldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.seckilldemo.mapper")
public class SeckilldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckilldemoApplication.class, args);
    }

}
