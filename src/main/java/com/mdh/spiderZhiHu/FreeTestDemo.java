package com.mdh.spiderZhiHu;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by madonghao on 2018/12/5.
 */
public class FreeTestDemo {
    public static void main(String[] args) {

        try {
            String url = "https://free-ss.tw/ss.json?_=";
            String time = String.valueOf(System.currentTimeMillis());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url + time);
            httpGet.setHeader("authority","free-ss.tw");
            httpGet.setHeader("method","GET");
            httpGet.setHeader("path","/ss.json?_="+time);
            httpGet.setHeader("scheme","https");
            httpGet.setHeader("accept","application/json, text/javascript, */*; q=0.01");
            httpGet.setHeader("accept-encoding","gzip, deflate, br");
            httpGet.setHeader("accept-language","zh-CN,zh;q=0.9");
            httpGet.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            httpGet.setHeader("x-requested-with","XMLHttpRequest");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
