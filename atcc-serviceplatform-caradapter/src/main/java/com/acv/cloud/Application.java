package com.acv.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 按照官方的建议放在root目录下，这样才能扫描到Service和dao，不然还会引起，扫描不到注解的问题
 * @author luomouren
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableFeignClients
// mapper.java扫描
@MapperScan("com.acv.cloud.mapper")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}