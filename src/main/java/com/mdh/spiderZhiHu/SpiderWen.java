package com.mdh.spiderZhiHu;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by madonghao on 2018/10/11.
 */
public class SpiderWen {
    public static void main(String[] args) throws IOException {
        String titleUrl = "https://www.zhihu.com/question/22545625/answer/501589559";
        //System.out.println(getTitle(titleUrl));
        //System.out.println(getWen(titleUrl));
        Document doc = Jsoup.parseBodyFragment(getWen(titleUrl));
        System.out.println(doc.toString());
        //String title = doc.title();
        //System.out.println("title :"+ title);
        //Elements elements = doc.getElementsByTag("title");
        //System.out.println(elements.toString());
        /*for(Element element : elements) {
            String text = element.text();
            System.out.println(text);
        }*/

        //String str = tds.attr("");
        //System.out.println(str);
        //doc.select("div.masthead").first();
        Elements elements = doc.getElementsByClass("QuestionHeader-detail");
        System.out.println(elements.toString());
        String spanEl = elements.first().cssSelector();
        //Element elementSpan = elements.
        //System.out.println(elementSpan);


    }

    private static String getTitle (String url ) throws IOException {
        Document doc = Jsoup.connect(url).get();
        /** 获取页面的标题 String title = doc.title(); */
        return doc.title();
    }

    private static String getWen (String url ) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if(response != null) {
            /**  获取网页内容 */
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }
        if(response != null) {
            response.close();
        }
        if(response != null) {
            httpClient.close();
        }
        return null;
    }
}
