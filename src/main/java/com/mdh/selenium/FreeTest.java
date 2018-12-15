package com.mdh.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by madonghao on 2018/12/5.
 */
public class FreeTest {

    public static void main(String[] args) {

        String url = "https://free-ss.tw/ss.json?_=";
        String furl = "https://free-ss.tw/";
        String time = String.valueOf(System.currentTimeMillis());
        WebDriver driver = new ChromeDriver();
        driver.get(furl);
        //String text = driver.findElement(By.xpath("/html/body/pre")).getText();
        //System.out.println(text);
        //driver.quit();

    }

}
