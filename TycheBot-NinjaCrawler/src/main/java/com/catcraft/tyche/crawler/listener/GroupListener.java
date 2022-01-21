package com.catcraft.tyche.crawler.listener;

import catcode.CatCodeUtil;
import com.catcraft.tyche.crawler.controller.DBCrawlerController;
import com.catcraft.tyche.crawler.controller.NINJACrawlerController;
import com.catcraft.tyche.crawler.entity.Currency;
import com.catcraft.tyche.crawler.repository.CurrencyRepository;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.Filters;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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


    @OnGroup
    @Filters(value = {
            @Filter(value = "查价 {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "cj {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "CJ {{itemName}}", matchType = MatchType.REGEX_MATCHES)
    })
    public void ItemValue(GroupMsg groupMsg, MsgSender sender, @FilterValue("itemName") String itemName) {
        System.out.println("查价触发: " + itemName);
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            sender.SENDER.sendGroupMsg(groupMsg, "未找到该物品: " + itemName);
            return;
        }
        if (currency.getItemInfo() == null || currency.getItemInfo().isEmpty()) {
            currency.setItemInfo(dbCrawlerController.getItemInfo(currency.getName()));
            currencyRepository.save(currency);
        }

        StringBuilder stringBuilder = new StringBuilder();
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;

        // 构建at
//        String at = util.toCat("at", "code=" + groupMsg.getAccountInfo().getAccountCode());
        stringBuilder.append(currency.getName()).append("\n")
                .append(currency.getTranslatedName()).append("\n")
//                .append("=================\n")
//                .append(currency.getItemInfo().isEmpty() ? "此物品暂未支持\n" : currency.getItemInfo())
                .append("=================\n")
                .append("混沌价值: ").append(currency.getChaosValue()).append("chaos \n")
                .append("崇高价值: ").append(currency.getExaltedValue()).append("ex \n")
                .append("=================\n");
//        System.out.println(groupMsg.getGroupInfo().getGroupCode());
        if (!groupMsg.getGroupInfo().getGroupCode().equals("607221355")) {
            stringBuilder.append("Godparadise公会专用查价机器人\n").append("详情请见个人签名\n");
        }
        sender.SENDER.sendGroupMsg(groupMsg,  stringBuilder.toString());
    }

    @OnGroup
    @Filters(value = {
            @Filter(value = "查询 {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "cx {{itemName}}", matchType = MatchType.REGEX_MATCHES),
            @Filter(value = "CX {{itemName}}", matchType = MatchType.REGEX_MATCHES)
    })
    public void ItemInfo(GroupMsg groupMsg, MsgSender sender, @FilterValue("itemName") String itemName) {
        System.out.println("查询触发: " + itemName);
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            sender.SENDER.sendGroupMsg(groupMsg, "未找到该物品: " + itemName);
            return;
        }
        if (currency.getItemInfo() == null || currency.getItemInfo().isEmpty()) {
            currency.setItemInfo(dbCrawlerController.getItemInfo(currency.getName()));
            currencyRepository.save(currency);
        }

        StringBuilder stringBuilder = new StringBuilder();
        // 获取猫猫码工具
        CatCodeUtil util = CatCodeUtil.INSTANCE;

        // 构建at
//        String at = util.toCat("at", "code=" + groupMsg.getAccountInfo().getAccountCode());
        stringBuilder.append(currency.getName()).append("\n")
                .append("=================\n")
                .append(currency.getItemInfo().isEmpty() ? "此物品暂未支持\n" : currency.getItemInfo())
                .append("=================\n")
                .append("混沌价值: ").append(currency.getChaosValue()).append("chaos \n")
                .append("崇高价值: ").append(currency.getExaltedValue()).append("ex \n")
                .append("=================\n");
//        System.out.println(groupMsg.getGroupInfo().getGroupCode());
        if (!groupMsg.getGroupInfo().getGroupCode().equals("607221355")) {
            stringBuilder.append("Godparadise公会专用查价机器人\n").append("详情请见个人签名\n");
        }
        sender.SENDER.sendGroupMsg(groupMsg,  stringBuilder.toString());
    }

    @OnGroup
    @Filter(value = "设置别名 {{itemName}}-{{num,\\d+}}-{{commonName}}", matchType = MatchType.REGEX_MATCHES)
    public void setCommonName(GroupMsg groupMsg, MsgSender sender,
                              @FilterValue("itemName") String itemName, @FilterValue("num") int num,
                              @FilterValue("commonName") String commonName) {
        System.out.println("别名触发: " + itemName + num + commonName);
        if (num > 3 || num < 0) {
            sender.SENDER.sendGroupMsg(groupMsg, "数字设置错误: " + num);
            return;
        }
        Currency currency = getCurrency(itemName);
        if (currency == null) {
            sender.SENDER.sendGroupMsg(groupMsg, "未找到该物品: " + itemName);
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
        sender.SENDER.sendGroupMsg(groupMsg, sb);
    }




    @OnGroup
    @Filter(value = "刷新数据 tychePoe", matchType = MatchType.REGEX_MATCHES)
    public void refreshData(GroupMsg groupMsg, MsgSender msgSender) {
        for (String s : categorys) {
            ninjaCrawlerController.getItemValue(s);
        }
        ninjaCrawlerController.currencyValue();
        msgSender.SENDER.sendGroupMsg(groupMsg, "更新完成");
    }

    @OnGroup
    @Filter(value = "初始化数据 tychePoe", matchType = MatchType.REGEX_MATCHES)
    public void initData(GroupMsg groupMsg, MsgSender msgSender) {
        ninjaCrawlerController.itemInit();
        msgSender.SENDER.sendGroupMsg(groupMsg, "初始化完成");
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
