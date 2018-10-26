package com.mdh.spiderZhiHu;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by madonghao on 2018/10/11.
 */
public class SpiderTest {

    public static void main(String[] args) throws IOException {

        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet实例
        String urlPicture = "https://pic2.zhimg.com/80/v2-ed41df7d63c07d330fe6d96628822f09_hd.jpg";
        String urlWen = "https://www.zhihu.com/question/22545625/answer/501589559";

        HttpGet httpGet = new HttpGet(urlWen);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        //创建httpGet实例
        HttpPost httpPost = new HttpPost("");

        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取.后缀
        String fileName = urlPicture.substring(urlPicture.lastIndexOf("."), urlPicture.length());
        if(response != null) {
            /**  获取网页内容 */
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("网页内容:"+result);
            /**  获取Content-Type */
            System.out.println("Content-Type："+entity.getContentType().getValue());
            /** 响应状态 */
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("响应状态:"+statusCode);
            //InputStream inputStream = entity.getContent();
            //文件复制
            //FileUtils.copyToFile(inputStream,new File("D://spider//01"+fileName));
        }
        if(response != null) {
            response.close();
        }
        if(response != null) {
            httpClient.close();
        }
    }

}
