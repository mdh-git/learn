package com.mdh.tencentmap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 腾讯地图
 * 文档： https://lbs.qq.com/service/webService/webServiceGuide/webServiceOverview
 * @author madonghao
 * @create 2020-08-27 9:16
 **/
@Slf4j
public class TencentMapDemo {
    public static void main(String[] args) {

        //String response = getResponseString();
        String response = null;
        response = "{\"status\":0,\"message\":\"query ok\",\"result\":{\"rows\":[{\"elements\":[{\"distance\":7034,\"duration\":1538}]}]}}";
        String result = getResult(response);
        System.out.println(result);

    }

    private static String getResult(String response) {
        String distance = null;
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray rows = result.getJSONArray("rows");
        if(rows.size() == 1){
            String object = rows.getString(0);
            JSONObject object1 = JSONObject.parseObject(object);
            JSONArray elements = object1.getJSONArray("elements");
            if(elements.size() == 1){
                String string = elements.getString(0);
                JSONObject jsonObject1 = JSONObject.parseObject(string);
                distance = jsonObject1.getString("distance");
            }
        }
        return distance;
    }

    /**
     * 根据两对经纬度,获取两个地点之前的距离
     * 详细说明看文档
     * @return
     */
    private static String getResponseString() {
        String url = "https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=39.984092,116.306934&to=39.981987,116.362896&key=OE2BZ-6FWRQ-LFQ56-GOGXT-CKES2-OEBS2";
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            result = EntityUtils.toString(parseResponse(response), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static HttpEntity parseResponse(CloseableHttpResponse response) throws IOException {
        Assert.notNull(response, "HttpResponse is null");
        HttpEntity responseDate = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            responseDate = response.getEntity();
        } else {
            log.warn("Http返回异常，返回状态码[{}]返回信息[{}]", response.getStatusLine().getStatusCode(), response.getEntity() != null ? EntityUtils.toString(response.getEntity(), "UTF-8") : "");
        }

        return responseDate;
    }
}
