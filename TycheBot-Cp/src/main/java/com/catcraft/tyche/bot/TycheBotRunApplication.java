package com.catcraft.tyche.bot;

import com.catcraft.tyche.bot.listener.AllPrivateListener;
import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zhi_Pei
 * @create 2020/12/31 9:35
 */
@SpringBootApplication
@EnableSimbot
public class TycheBotRunApplication {
  @Autowired
  static AllPrivateListener allPrivateListener;
  public static void main(String[] args) {
    SpringApplication.run(TycheBotRunApplication.class, args);
  }

}
