package com.catcraft.tyche.bot.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catcraft.tyche.bot.features.qqAi.QQAiTalk;
import com.catcraft.tyche.bot.util.MsgCreate;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zhi_Pei
 * @create 2021/1/7 14:38
 */
@Component
@Listen(GroupMsg.class)
public class AllGroupListener {
    @Autowired
    MsgCreate msgCreate;
    @Autowired
    QQAiTalk qqAiTalk;

    @Filter(value = "来.*老黄历.*")
    public void programmerCalendar(GroupMsg msg, MsgSender sender) {
        String dayMsg = msgCreate.getProgrammerCalendar(1);
        sender.SENDER.sendGroupMsg(msg, dayMsg);
    }

    @Filter(value = "芦苇.*", matchType = MatchType.REGEX_MATCHES)
    public void qqAiTalk(GroupMsg msg, MsgSender sender) {
        String talk = qqAiTalk.getTalk(msg.getMsg(), msg.getAccountInfo().getAccountCode());
        JSONObject jsonObject = JSON.parseObject(talk);
        String answer = jsonObject.getJSONObject("data").getString("answer");
        if (answer.length() == 0) {
            answer = "听不懂你在说什么呢";
        }
        sender.SENDER.sendGroupMsg(msg, answer);
    }
}
