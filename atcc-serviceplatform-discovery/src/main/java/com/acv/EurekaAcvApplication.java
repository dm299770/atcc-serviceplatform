package com.acv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
//开启Eurka注册中心服务
@EnableEurekaServer
public class EurekaAcvApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaAcvApplication.class, args);
    }

}

