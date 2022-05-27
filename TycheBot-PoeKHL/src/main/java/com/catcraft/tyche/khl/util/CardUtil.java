package com.catcraft.tyche.khl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CardUtil {
    public static String makeItemCard(String itemName, String translatedName, String chaosPrice, String exPrice, String itemInfo) {
        String data = "{\n" +
                "    \"type\": \"card\",\n" +
                "    \"theme\": \"secondary\",\n" +
                "    \"size\": \"lg\",\n" +
                "    \"modules\": [\n" +
                "      {\n" +
                "        \"type\": \"header\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"plain-text\",\n" +
                "          \"content\": \"" + translatedName + "\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"divider\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"section\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"plain-text\",\n" +
                "          \"content\": \"" + itemInfo + "\"\n" +
                "        },\n" +
                "        \"mode\": \"right\",\n" +
                "        \"accessory\": {\n" +
                "          \"type\": \"image\",\n" +
                "          \"src\": \"http://www.godparadise.cn/wp-content/uploads/2022/04/cropped-5bei1-180x180.png\",\n" +
                "          \"size\": \"sm\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"divider\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"section\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"paragraph\",\n" +
                "          \"cols\": 2,\n" +
                "          \"fields\": [\n" +
                "            {\n" +
                "              \"type\": \"kmarkdown\",\n" +
                "              \"content\": \"**市场价格**\\n崇高价格: " + exPrice + "ex\\n混沌价格: " + chaosPrice + "chaos\" " +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"kmarkdown\",\n" +
                "              \"content\": \"**7天涨幅**\\n施工中\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"divider\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"action-group\",\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"button\",\n" +
                "            \"theme\": \"primary\",\n" +
                "            \"value\": \"https://www.pathofexile.com/trade/search/Sentinel?q={%22query%22:{%22filters%22:{},%22name%22:%22" + itemName + "%22}}\",\n" +
                "            \"click\": \"link\",\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain-text\",\n" +
                "              \"content\": \"市集链接\"\n" +
                "            }\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"divider\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"context\",\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"plain-text\",\n" +
                "            \"content\": \"数据来源于PoeNinja\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n";
        JSONObject jsonObject = JSON.parseObject(data);
        return "[" + jsonObject.toJSONString() + "]";
    }

    public static String makeInfoCard(String iconUrl, String nickname, String poeID, int vouch, String ban){
            String data = "   {\n" +
                    "    \"type\": \"card\",\n" +
                    "    \"theme\": \"secondary\",\n" +
                    "    \"size\": \"lg\",\n" +
                    "    \"modules\": [\n" +
                    "      {\n" +
                    "        \"type\": \"section\",\n" +
                    "        \"text\": {\n" +
                    "          \"type\": \"kmarkdown\",\n" +
                    "          \"content\": \"**昵称**\\n "+nickname+" \"\n" +
                    "        },\n" +
                    "        \"mode\": \"right\",\n" +
                    "        \"accessory\": {\n" +
                    "          \"type\": \"image\",\n" +
                    "          \"src\": \""+iconUrl+"\",\n" +
                    "          \"size\": \"sm\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"type\": \"section\",\n" +
                    "        \"text\": {\n" +
                    "          \"type\": \"paragraph\",\n" +
                    "          \"cols\": 3,\n" +
                    "          \"fields\": [\n" +
                    "            {\n" +
                    "              \"type\": \"kmarkdown\",\n" +
                    "              \"content\": \"**poe账号ID**\\n "+poeID+" \"\n" +
                    "            },\n" +
                    "            {\n" +
                    "              \"type\": \"kmarkdown\",\n" +
                    "              \"content\": \"**vouch总数**\\n "+vouch+" \"\n" +
                    "            },\n" +
                    "            {\n" +
                    "              \"type\": \"kmarkdown\",\n" +
                    "              \"content\": \"**账号状态**\\n "+ban+" \"\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }";
        JSONObject jsonObject = JSON.parseObject(data);
            return "["+jsonObject.toJSONString()+"]";

    }
}
