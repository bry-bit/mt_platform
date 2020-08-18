package com.mt.mt_platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.mt.mapperU8.*")
@ComponentScan(basePackages = "com.mt.*")
@EnableTransactionManagement
public class MtPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtPlatformApplication.class, args);
    }
}
