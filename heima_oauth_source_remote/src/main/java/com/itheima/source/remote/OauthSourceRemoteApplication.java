package com.itheima.source.remote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.source.remote.mapper")
public class OauthSourceRemoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthSourceRemoteApplication.class, args);
    }
}
