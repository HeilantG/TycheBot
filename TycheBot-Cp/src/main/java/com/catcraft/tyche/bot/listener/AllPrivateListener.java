package com.catcraft.tyche.bot.listener;

import com.catcraft.tyche.bot.util.MsgCreate;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.bot.BotManager;
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
    @Autowired
    BotManager botManager;

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

    public void sendMsg(String msg, String qqCode) {
        botManager.getDefaultBot().getSender().SENDER.sendPrivateMsg(qqCode, msg);
    }
}
