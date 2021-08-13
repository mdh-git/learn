package com.mdh.gecco;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @Author: madonghao
 * @Date: 2021/7/2 10:19 上午
 */
public class Test {

      public static void main(String[] args) throws IOException {
          String titleUrl = "http://xh.5156edu.com/pinyi.html";
          getTitle(titleUrl);
      }

    private static void getTitle(String titleUrl) throws IOException {
        //Document doc = Jsoup.connect(titleUrl).get();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(titleUrl);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Document doc = null;
        if(response != null) {
            doc = Jsoup.parse(response.toString(), titleUrl);
        }

        System.out.println(doc);
    }
}
