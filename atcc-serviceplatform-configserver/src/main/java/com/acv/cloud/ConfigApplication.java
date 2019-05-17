package com.acv.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: leo
 * @Date: 2019/5/8 16:36
 */
@RefreshScope
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class ConfigApplication {
        public static void main(String[] args){
                SpringApplication.run(ConfigApplication.class, args);
                //new SpringApplicationBuilder(ConfigApplication.class).web(WebApplicationType.SERVLET).run(args);
        }
}
