package com.catcreaft.tyche.bot.listener;

import com.catcreaft.tyche.bot.util.MsgCreate;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zhi_Pei
 * @create 2021/1/7 14:38
 */
@Component
@Listen(PrivateMsg.class)
public class AllPrivateListener {
  @Autowired
  MsgCreate msgCreate;

  @Filter(value = "来.*老黄历.*")
  public void programmerCalendar(PrivateMsg msg, MsgSender sender) {
    String dayMsg = msgCreate.getProgrammerCalendar(1);
    sender.SENDER.sendPrivateMsg(msg, dayMsg);
  }

  @Filter(value = "来")
  public void programmerCalendar1(PrivateMsg msg, MsgSender sender) {
    String dayMsg = msgCreate.getProgrammerCalendar(1);
    sender.SENDER.sendPrivateMsg(msg, dayMsg);
  }
}
