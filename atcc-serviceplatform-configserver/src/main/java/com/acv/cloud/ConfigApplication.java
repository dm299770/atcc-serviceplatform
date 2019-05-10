package com.acv.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: leo
 * @Date: 2019/5/8 16:36
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
        public static void main(String[] args){
                SpringApplication.run(ConfigApplication.class, args);
                //new SpringApplicationBuilder(ConfigApplication.class).web(WebApplicationType.SERVLET).run(args);
        }
}
