package com.catcraft.tyche.crawler.controller;

import catcode.CatCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.catcraft.tyche.crawler.entity.Currency;
import com.catcraft.tyche.crawler.repository.CurrencyRepository;
import com.catcraft.tyche.crawler.util.OkHttpUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author HeilantG
 * 此类未NinJa的抓取接口
 */
@RestController
public class NINJACrawlerController {


    @Autowired
    CurrencyRepository currencyRepository;

    /**
     * ITEM接口
     * DivinationCard 命运卡 //命运卡重名
     * Artifact 掘墓通货
     * Prophecy 预言 //重名 Blood in the Eyes
     * Oil 油
     * Incubator 孕育石
     * UniqueWeapon 传奇武器
     * UniqueArmour 传奇护甲
     * UniqueAccessory 传奇饰品
     * UniqueFlask 传奇药水
     * UniqueJewel 传奇珠宝 //重名 Wildfire
     * SkillGem 技能宝石 //  未支持
     * ClusterJewel 星团 // 未支持
     * Map 地图 // 未支持
     * DeliriumOrb //梦魇宝珠
     * Invitation //马文邀请
     * Scarab //圣甲虫
     * Watchstone //守望石
     * Base Type // 基础物品 未支持
     * Fossil //化石
     * Resonator //共振器 未支持
     * HelmetEnchant //特殊品质技能石
     * Beast //猎魔
     * Essence //精髓
     * Vial //??
     *
     * @return 获取最新价格
     */
    @GetMapping("/item/{itemType}/init")
    public String getItemValue(@PathVariable String itemType) {
        String URL = "https://poe.ninja/api/data/ItemOverview?league=Sentinel&type=" + itemType + "&language=en";
        Response response = OkHttpUtil.getApi(URL);
        String jsonString;
        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        JSONObject jsonRes = JSON.parseObject(jsonString);
        JSONArray lines = jsonRes.getJSONArray("lines");
        for (Object line : lines) {
            JSONObject currencyInfo = JSON.parseObject(line.toString());
//          装备名称不一致,修复
            System.out.println(currencyInfo.getString("name"));
            Currency currencyItem = currencyRepository.findByName(currencyInfo.getString("name").replace("'", ""));
            if (currencyItem == null) {
                System.out.println("not find item: " + currencyInfo.getString("name"));
                continue;
            }
            currencyItem.setChaosValue(currencyInfo.getDoubleValue("chaosValue"));
            currencyItem.setExaltedValue(currencyInfo.getDoubleValue("exaltedValue"));
            currencyItem.setListingCount(currencyInfo.getInteger("listingCount"));
//            System.out.println(currencyItem);
            currencyRepository.save(currencyItem);
        }
        return "success";
    }

    /**
     * 通货
     *
     * @return
     */
    @GetMapping("/currency/init")
    public String currencyValue() {
        String URL = "https://poe.ninja/api/data/CurrencyOverview?league=Sentinel&type=Currency&language=en";
        Response response = OkHttpUtil.getApi(URL);
        String jsonString;
        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        JSONObject jsonRes = JSON.parseObject(jsonString);
        JSONArray lines = jsonRes.getJSONArray("lines");
        //ec比例
        Currency exalted_orb = currencyRepository.findByName("Exalted Orb");
        for (Object line : lines) {
            JSONObject currencyInfo = JSON.parseObject(line.toString());
//          装备名称不一致,修复
            System.out.println(currencyInfo.getString("currencyTypeName"));
            Currency currencyItem = currencyRepository.findByName(currencyInfo.getString("currencyTypeName").replace("'", ""));
            if (currencyItem == null) {
                System.out.println("not find item: " + currencyInfo.getString("name"));
                continue;
            }
            currencyItem.setChaosValue(currencyInfo.getDoubleValue("chaosEquivalent"));
            //计算崇高价值
            double chaosEquivalent = currencyInfo.getDoubleValue("chaosEquivalent") / exalted_orb.getChaosValue();
            //只保留两位数
            DecimalFormat df = new DecimalFormat("#0.00");
            currencyItem.setExaltedValue(Double.parseDouble(df.format(chaosEquivalent)));
//            System.out.println(currencyItem);
            currencyRepository.save(currencyItem);
        }
        return "success";
    }

    /**
     * 初始化所有特殊物品
     *
     * @return
     */
    @GetMapping("/item/info/init")
    public String itemInit() {
        String URL = "https://poedb.tw/json/autocomplete_cn.json";
        Response response = OkHttpUtil.getApi(URL);
        String jsonString;
        try {
            jsonString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        ArrayList<Currency> currencies = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(jsonString);
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(o.toString());
            Currency currency = new Currency();
            currency.setName(jsonObject.getString("value").replace("_", " "));
            currency.setTranslatedName(jsonObject.getString("label"));
            currencies.add(currency);
        }
        currencyRepository.saveAllAndFlush(currencies);

        return "success";
    }

}
