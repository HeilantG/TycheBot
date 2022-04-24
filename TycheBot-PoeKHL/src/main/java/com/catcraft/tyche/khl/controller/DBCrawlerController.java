package com.catcraft.tyche.khl.controller;

import com.catcraft.tyche.khl.util.OkHttpUtil;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HeilantG
 * 此类为poedb的抓取接口
 */
@Component
public class DBCrawlerController {

    public  String getItemInfo(String item) {
        item = item.replace(" ","_");
        String URL = "https://poedb.tw/cn/" + item;
        StringBuilder sb = new StringBuilder();
        Response response = OkHttpUtil.getApi(URL);
        String htmlString = "";
        try {
            htmlString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(htmlString);
        Elements ItemName = document.getElementsByClass("ItemName");
        for (Element element : ItemName) {
            sb.append(element.text()).append("\n");
        }
        Elements ItemType = document.getElementsByClass("ItemType");
        for (Element element : ItemType) {
            sb.append(element.text()).append("\n");
        }
        List<Element> itemMagic = document.getElementsByClass("item_magic");
        List<String> magicText = itemMagic.stream().map(Element::text).distinct().collect(Collectors.toList());
        for (String text : magicText) {
            sb.append(text+"\n");
        }
        return sb.toString();
    }
}






