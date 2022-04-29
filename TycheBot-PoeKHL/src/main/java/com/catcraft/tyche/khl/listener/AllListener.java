package com.catcraft.tyche.khl.listener;

import com.catcraft.tyche.khl.util.CardUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.Filters;
import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.kaiheila.message.KaiheilaRequestMessage;
import love.forte.simbot.definition.*;
import love.forte.simbot.event.*;
import love.forte.simbot.kaiheila.api.message.MessageCreateRequest;
import love.forte.simbot.kaiheila.api.message.MessageType;
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

    @Listener
    @Filters(value = {
            @Filter(value = "测试卡片"),
    })
    public void testGroup(ChannelMessageEvent channelMessageEvent) {
        System.out.println("onGroup");
        Channel channel = channelMessageEvent.getChannel();
        KaiheilaRequestMessage requestMessage = new KaiheilaRequestMessage(new MessageCreateRequest(
                MessageType.CARD.getType(),
                channel.getId(),
                CardUtil.makeItemCard("测试", "", "", "", ""),
                channelMessageEvent.getId(),
                null,
                null
        ));
        channel.sendBlocking(requestMessage);
    }

    @Listener
    @Filters(value = {
            @Filter(value = "我是谁"),
    })
    public void testUsrInfo(ChannelMessageEvent channelMessageEvent) {
        System.out.println("onGroup");
        Channel channel = channelMessageEvent.getChannel();
        channel.sendBlocking(channelMessageEvent.getAuthor().getId() + "|"
                + channelMessageEvent.getAuthor().getNickname() + "|" + channelMessageEvent.getAuthor().getAvatar());
    }
}
