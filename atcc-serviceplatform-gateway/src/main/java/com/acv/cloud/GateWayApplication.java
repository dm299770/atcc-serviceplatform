package com.acv.cloud;

import com.acv.cloud.filter.AccessFilter;
import com.acv.cloud.filter.ErrorFileter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

//    @Bean
//    AccessFilter AccessFilter() {
//
//        return new AccessFilter();
//   }
//    @Bean
//    ErrorFileter ErrorFileter(){
//        return new ErrorFileter();
//    }
}

