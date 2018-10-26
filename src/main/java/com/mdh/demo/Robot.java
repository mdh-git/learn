package com.mdh.demo;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by madonghao on 2018/9/20.
 */
public class Robot {
    public static void main(String[] args) {
        URL url = null;
        URLConnection urlConnection = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        //String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
        //url匹配规则
        String regex = "https://[\\w+\\.?/?]+\\.[A-Za-z]+";
        Pattern p = Pattern.compile(regex);
        try {
            //爬取的网址、这里爬取的是一个生物网站
            url = new URL("https://www.rndsystems.com/cn");
            urlConnection = url.openConnection();
            //将爬取到的链接放到D盘的SiteURL文件中
            pw = new PrintWriter(new FileWriter("D:/SiteURL.txt"), true);
            br = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String buf = null;
            while ((buf = br.readLine()) != null) {
                Matcher buf_m = p.matcher(buf);
                while (buf_m.find()) {
                    pw.println(buf_m.group());
                }
            }
            System.out.println("爬取成功^_^");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }
}
