package com.catcraft.tyche.crawler;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author HeilantG
 */
@EnableSimbot
@SpringBootApplication
public class NinjaCrawlerRunApplication {
    public static void main(String[] args) {
        SpringApplication.run(NinjaCrawlerRunApplication.class, args);
    }
}
