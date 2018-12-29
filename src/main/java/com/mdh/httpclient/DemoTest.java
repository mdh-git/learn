package com.mdh.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author: madonghao
 * @Date: 2018/12/26 20:02
 */
public class DemoTest {
    public static void main(String[] args){
        try {
            /*String urlToken = "http://192.168.3.201:10006/user_admin/genTokenbyUid";
            HttpPost httpPost = new HttpPost(urlToken);
            JSONObject param= new JSONObject();
            param.put("userId", "101818");
            param.put("appCode", 101);
            StringEntity stringEntity = new StringEntity(param.toString());
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            //拿到token
            JSONObject jsonObject = JSON.parseObject(result);
            Object token = jsonObject.get("result");*/


            String urlResult = "https://admin.lechebang.cn/gateway/saas/carSales/insurance/account/queryStoreAccountListForOperation";
            HttpPost httpPostResult = new HttpPost(urlResult);
            JSONObject param1 = new JSONObject();
            param1.put("groupId", -1);
            param1.put("appCode", 1011);
            param1.put("groupType", 0);
            param1.put("token", "5a8f9cfda91a1d8b0bf29a598cfeb21c");
            StringEntity stringEntity1 = new StringEntity(param1.toString());
            stringEntity1.setContentType("application/x-www-form-urlencoded");
            httpPostResult.setEntity(stringEntity1);
            CloseableHttpClient httpClient1 = HttpClients.createDefault();
            CloseableHttpResponse response1 = httpClient1.execute(httpPostResult);
            HttpEntity entity1 = response1.getEntity();
            String result1 = EntityUtils.toString(entity1, "UTF-8");
            System.out.println(result1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
