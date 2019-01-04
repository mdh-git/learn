package com.mdh.selenium.se;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Author: madonghao
 * @Date: 2019/1/3 19:33
 */
public class TetsPiao {
    public static void main(String[] args) {
        String url = "https://www.12306.cn/index/";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
    }
}
