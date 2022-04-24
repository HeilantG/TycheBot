package com.catcraft.tyche.khl.listener;

import love.forte.simboot.annotation.Listener;
import love.forte.simbot.definition.*;
import love.forte.simbot.event.*;
import org.springframework.stereotype.Component;

@Component
public class AllListener {
    //    私聊
    @Listener
    public void reply(ContactMessageEvent contactMessageEvent) {
        System.out.println("onPrivate");
        final String reply = contactMessageEvent.getMessageContent().getPlainText().trim();
        // 如果有，发送消息，并阻止后续事件的执行。
        Contact user = contactMessageEvent.getUser();
        user.sendBlocking(reply);
        // 返回 EventResult.truncate 代表阻止后续其他监听函数的执行。
    }

//    @Listener
//    public void replyGroup(ChannelMessageEvent channelMessageEvent) {
//        System.out.println("onGroup");
//        final String reply = channelMessageEvent.getMessageContent().getPlainText().trim();
//        Channel channel = channelMessageEvent.getChannel();
//        channel.sendBlocking(reply);
//    }
}
