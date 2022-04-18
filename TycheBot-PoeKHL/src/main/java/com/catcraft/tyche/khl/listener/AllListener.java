package com.catcraft.tyche.khl.listener;

import love.forte.simboot.annotation.Listener;
import love.forte.simbot.definition.Contact;
import love.forte.simbot.definition.Friend;
import love.forte.simbot.event.ContactMessageEvent;
import love.forte.simbot.event.EventResult;
import love.forte.simbot.event.FriendMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class AllListener {
    @Listener
    public EventResult reply(ContactMessageEvent friendMessageEvent) {
        final String reply = friendMessageEvent.getMessageContent().getPlainText().trim();

        // 如果有，发送消息，并阻止后续事件的执行。
        Contact user = friendMessageEvent.getUser();
        user.sendBlocking(reply);

        // 返回 EventResult.truncate 代表阻止后续其他监听函数的执行。
        return EventResult.truncate();

    }
}
