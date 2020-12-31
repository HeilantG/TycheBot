package com.catcreaft.tyche.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.stereotype.Component;

/**
 * @author Zhi_Pei
 * @create 2020/12/31 9:56
 */
@Component
public class BaseListener {

  @OnGroup
  @Filter(value = "提醒", matchType = MatchType.EQUALS)
  public void remindClass(GroupMsg msg, MsgSender sender) {
    sender.SENDER.sendGroupMsg(msg, "hi");
  }
}
