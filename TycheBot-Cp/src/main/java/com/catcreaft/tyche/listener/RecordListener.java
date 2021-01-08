package com.catcreaft.tyche.listener;

import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.OnPrivate;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.PrivateMsg;
import org.springframework.stereotype.Component;

/**
 * 消息记录
 *
 * @author Heilant Gong {@link this#recordPrivate(PrivateMsg)} 记录私聊消息 {@link
 *     this#recordGroup(GroupMsg)} 记录群聊消息
 */
@Component
public class RecordListener {

  @OnPrivate
  @Filter
  public void recordPrivate(PrivateMsg privateMsg) {
    System.out.println(privateMsg.getMsg());
  }

  @OnGroup
  @Filter
  public void recordGroup(GroupMsg groupMsg) {
    System.out.println(groupMsg.getMsg());
  }
}
