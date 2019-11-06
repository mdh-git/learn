package com.mdh.spiderZhiHu;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author madonghao
 * @create 2019-10-14 18:50
 **/
public class TestGetLocal {

    static String sucess = "0";

    public static void main(String[] args) {
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location=39.943700,116.351395&get_poi=0&key=OE2BZ-6FWRQ-LFQ56-GOGXT-CKES2-OEBS2";
        String json = null;
        HttpGet httpGet = new HttpGet();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // httpGet.setHeader("Cookie","dm_login_weixin_rem=; logout_page=dm_loginpage; mpuv=18cb3c98-a62a-454f-ce6a-799d32734347");
        try {
            httpGet.setURI(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            byte[] body = EntityUtils.toByteArray(entity);
            StatusLine sL = httpResponse.getStatusLine();
            int statusCode = sL.getStatusCode();
            if (statusCode == 200) {
                json = new String(body, "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject object = JSONObject.parseObject(json);
        String status = object.getString("status");
        if(sucess.equals(status)){
            JSONObject result = object.getJSONObject("result");
            JSONObject addressComponent = result.getJSONObject("address_component");
            String province = addressComponent.getString("province");
            String city = addressComponent.getString("city");
            // System.out.println(province);
            System.out.println(city.replace("市",""));
        } else {
            System.out.println(object.getString("message"));
        }
        // System.out.println(status);
        // System.out.println(json);
    }
}
