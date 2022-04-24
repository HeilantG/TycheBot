package com.catcraft.tyche.khl.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
     public static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(4, TimeUnit.SECONDS).build();

     public static Response getApi(String URL){
          Request request = new Request.Builder().get().url(URL).build();
          try {
               return OkHttpUtil.okHttpClient.newCall(request).execute();
          } catch (IOException e) {
               e.printStackTrace();
          }
          return null;
     }
}
