package com.mdh.spiderZhiHu;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @Author: madonghao
 * @Date: 2019/1/30 10:58
 */
public class CpicTest {

    private static final String BASE_URL	= "http://issue.cpic.com.cn";
    private static final String COMMON_URL	= BASE_URL + "/ecar/view/portal/page/common/";
    private static final String LOGIN_URL    = COMMON_URL + "login.html";
    private static final String INDEX_URL    = COMMON_URL + "index.html";

    /**
     * HttpClient:是一个接口
     * 首先需要先创建一个HttpClient的实例
     * CloseableHttpClient httpClient = HttpClients.createDefault();
     */

    /**
     * 发送GET请求:
     * 先创建一个HttpGet对象,传入目标的网络地址,然后调用HttpClient的execute()方法即可:
     * HttpGet httpGet = new HttpGet(url);
     * httpGet.addHeader("Accept", "text/html");
     * httpClient.execute(httpGet);
     */

    /**
     * 发送POST请求:
     * 创建一个HttpPost对象,传入目标的网络地址:
     * HttpPost httpPost=new HttpPost(url);
     * 通过一个NameValuePair集合来存放待提交的参数,并将这个参数集合传入到一个UrlEncodedFormEntity中,
     * 然后调用HttpPost的setEntity()方法将构建好的UrlEncodedFormEntity传入:
     * List<NameValuePair>params=newArrayList<NameValuePair>();
     * Params.add(new BasicNameValuePair("username","admin"));
     * Params.add(new BasicNameValuePair("password","123456"));
     * UrlEncodedFormEntity entity=newUrlEncodedFormEntity(params,"utf-8");
     * httpPost.setEntity(entity);
     * 调用HttpClient的execute()方法,并将HttpPost对象传入即可:
     * HttpClient.execute(HttpPost);
     * 执行execute()方法之后会返回一个HttpResponse对象,服务器所返回的所有信息就保护在HttpResponse里面.
     * 先取出服务器返回的状态码,如果等于200就说明请求和响应都成功了:
     * If(httpResponse.getStatusLine().getStatusCode()==200){
     *     //请求和响应都成功了
     *     HttpEntityentity=HttpResponse.getEntity();//调用getEntity()方法获取到一个HttpEntity实例
     * }
     * Stringresponse=EntityUtils.toString(entity,”utf-8”);
     * 用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串,防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
     */

    public static void main(String[] args) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(INDEX_URL);
            httpGet.addHeader("Accept", "text/html");
            httpGet.addHeader("Accept-Charset", "utf-8");
            httpGet.addHeader("Accept-Encoding", "gzip");
            httpGet.addHeader("Accept-Language", "en-US,en");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                Document doc = Jsoup.parse(response.toString(), BASE_URL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
