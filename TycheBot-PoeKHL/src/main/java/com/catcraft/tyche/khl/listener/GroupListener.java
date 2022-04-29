package com.catcraft.tyche.khl.listener;

import com.catcraft.tyche.khl.controller.DBCrawlerController;
import com.catcraft.tyche.khl.controller.NINJACrawlerController;
import com.catcraft.tyche.khl.entity.Currency;
import com.catcraft.tyche.khl.repository.CurrencyRepository;
import com.catcraft.tyche.khl.util.CardUtil;
import love.forte.simboot.annotation.Filter;
import love.forte.simboot.annotation.FilterValue;
import love.forte.simboot.annotation.Filters;
import love.forte.simboot.annotation.Listener;
import love.forte.simboot.filter.MatchType;
import love.forte.simbot.component.kaiheila.message.KaiheilaRequestMessage;
import love.forte.simbot.definition.Channel;
import love.forte.simbot.event.ChannelMessageEvent;
import love.forte.simbot.kaiheila.api.message.MessageCreateRequest;
import love.forte.simbot.kaiheila.api.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class GroupListener {

    @Autowired
    DBCrawlerController dbCrawlerController;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    NINJACrawlerController ninjaCrawlerController;

    private static final String[] categorys = {"DivinationCard", "Artifact", "Prophecy", "Oil", "Incubator", "UniqueWeapon"
            , "UniqueArmour", "UniqueAccessory", "UniqueFlask", "UniqueJewel", "DeliriumOrb", "Invitation", "Scarab", "Watchstone"
            , "Fossil", "HelmetEnchant", "Beast", "Essence", "Vial"};

    @Listener
    @Filters(value = {
            @Filter(value = "查价 {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "cj {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "CJ {{itemName}}", matchType = MatchType.REGEX_MATCHES)
    })
    public void ItemValue(ChannelMessageEvent channelMessageEvent, @FilterValue("itemName") String itemName) {
//        final String reply = channelMessageEvent.getMessageContent().getPlainText().trim();
        Channel channel = channelMessageEvent.getChannel();
        System.out.println("查价触发: " + itemName);
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            channel.sendBlocking("未找到该物品: " + itemName);
            return;
        }
        if (currency.getItemInfo() == null || currency.getItemInfo().isEmpty()) {
            currency.setItemInfo(dbCrawlerController.getItemInfo(currency.getName()));
            currencyRepository.save(currency);
        }
        KaiheilaRequestMessage requestMessage = new KaiheilaRequestMessage(new MessageCreateRequest(
                MessageType.CARD.getType(),
                channel.getId(),
                CardUtil.makeItemCard(currency.getName(), currency.getTranslatedName(), String.valueOf(currency.getChaosValue()),
                        String.valueOf(currency.getExaltedValue()), currency.getItemInfo()),
                channelMessageEvent.getId(),
                null,
                null
        ));
        channel.sendBlocking(requestMessage);
    }

    @Listener
    @Filters(value = {
            @Filter(value = "查询 {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "cx {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "CX {{itemName}}", matchType = MatchType.REGEX_MATCHES)
    })
    public void ItemInfo(ChannelMessageEvent channelMessageEvent, @FilterValue("itemName") String itemName) {
        System.out.println("查询触发: " + itemName);
        Channel channel = channelMessageEvent.getChannel();
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            channel.sendBlocking("未找到该物品: " + itemName);
            return;
        }
        if (currency.getItemInfo() == null || currency.getItemInfo().isEmpty()) {
            currency.setItemInfo(dbCrawlerController.getItemInfo(currency.getName()));
            currencyRepository.save(currency);
        }

        String stringBuilder = currency.getName() + "\n" +
                "=================\n" +
                (currency.getItemInfo().isEmpty() ? "此物品暂未支持\n" : currency.getItemInfo()) +
                "=================\n" +
                "混沌价值: " + currency.getChaosValue() + "chaos \n" +
                "崇高价值: " + currency.getExaltedValue() + "ex \n" +
                "=================\n";
//        System.out.println(groupMsg.getGroupInfo().getGroupCode());
//        if (!groupMsg.getGroupInfo().getGroupCode().equals("607221355")) {
//            stringBuilder.append("Godparadise公会专用查价机器人\n").append("详情请见个人签名\n");
//        }
        channel.sendBlocking(stringBuilder);
    }


    @Listener
    @Filter(value = "设置别名 {{itemName}}-{{num,\\d+}}-{{commonName}}", matchType = MatchType.REGEX_MATCHES)
    public void setCommonName(ChannelMessageEvent channelMessageEvent,
                              @FilterValue("itemName") String itemName, @FilterValue("num") int num,
                              @FilterValue("commonName") String commonName) {
        System.out.println("别名触发: " + itemName + num + commonName);
        Channel channel = channelMessageEvent.getChannel();
        if (num > 3 || num < 0) {
            channel.sendBlocking("数字设置错误: " + num);
            return;
        }
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            channel.sendBlocking("未找到该物品: " + itemName);
            return;
        }
        switch (num) {
            case 0:
                currency.setName(commonName);
            case 1:
                currency.setCommonName(commonName);
                break;
            case 2:
                currency.setCommonName2(commonName);
                break;
            case 3:
                currency.setCommonName3(commonName);
                break;
            default:
                break;
        }
        currencyRepository.save(currency);
        String sb = "设置成功\n" +
                "物品名称: " + currency.getName() + "\n" +
                "物品译名: " + currency.getTranslatedName() + "\n" +
                "物品别名1: " + currency.getCommonName() + "\n" +
                "物品别名2: " + currency.getCommonName2() + "\n" +
                "物品别名3: " + currency.getCommonName3() + "\n";
        channel.sendBlocking(sb);
    }


    @Listener
    @Filter(value = "刷新数据 tychePoe", matchType = MatchType.REGEX_MATCHES)
    public void refreshData(ChannelMessageEvent channelMessageEvent) {
        Channel channel = channelMessageEvent.getChannel();
        for (String s : categorys) {
            ninjaCrawlerController.getItemValue(s);
        }
        ninjaCrawlerController.currencyValue();
        channel.sendBlocking("更新完成");
    }

    @Listener
    @Filter(value = "初始化数据 tychePoe", matchType = MatchType.REGEX_MATCHES)
    public void initData(ChannelMessageEvent channelMessageEvent) {
        ninjaCrawlerController.itemInit();
        channelMessageEvent.getChannel().sendBlocking("初始化完成");
    }

    /**
     * 通过物品名称查询物品实体
     *
     * @param itemName 独特物品名称
     * @return
     */
    private Currency getCurrency(String itemName) {
        Currency currency;
        if (Pattern.matches("^[a-zA-Z]*$", itemName.replaceAll(" ", ""))) {
            currency = currencyRepository.findByName(itemName.replaceAll("'", ""));
        } else {
            currency = currencyRepository.findByTranslatedName(itemName);
        }
        if (currency == null) {
            currency = currencyRepository.findByCommonName(itemName);
        }
        if (currency == null) {
            currency = currencyRepository.findByCommonName2(itemName);
        }
        if (currency == null) {
            currency = currencyRepository.findByCommonName3(itemName);
        }
        return currency;
    }

}
