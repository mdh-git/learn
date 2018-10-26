package com.mdh.spiderZhiHu;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by madonghao on 2018/10/11.
 */
public class SpiderPicture {
    public static void main(String[] args) throws IOException {
        String pictureUrl = "https://pic2.zhimg.com/80/v2-ed41df7d63c07d330fe6d96628822f09_hd.jpg";
        getPicture(pictureUrl);
    }

    private static void getPicture (String url) throws IOException {
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet实例
        HttpGet httpGet = new HttpGet(url);
        //设置请求头消息 User-Agent模拟浏览器
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        //创建httpPost实例
        HttpPost httpPost = new HttpPost("");

        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取.后缀
        String fileName = url.substring(url.lastIndexOf("."), url.length());
        if(response != null) {
            /**  获取网页内容 */
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            //文件复制
            FileUtils.copyToFile(inputStream,new File("D://spider//02"+fileName));
        }
        if(response != null) {
            response.close();
        }
        if(response != null) {
            httpClient.close();
        }
    }
}
