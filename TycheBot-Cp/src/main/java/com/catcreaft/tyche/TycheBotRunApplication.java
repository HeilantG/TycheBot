package com.catcreaft.tyche;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zhi_Pei
 * @create 2020/12/31 9:35
 */
@SpringBootApplication
@EnableSimbot
public class TycheBotRunApplication {
  public static void main(String[] args) {
    SpringApplication.run(TycheBotRunApplication.class, args);
  }
}
