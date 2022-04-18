package com.catcraft.tyche.khl;

import love.forte.simboot.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class KhlBotRunApplication {
    public static void main(String[] args) {
        SpringApplication.run(KhlBotRunApplication.class, args);
    }
}
