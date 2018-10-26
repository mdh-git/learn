package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class BrowserGo {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        //get 到百度首页
        driver.get("https://www.baidu.com/");
        System.out.println("now accesss " + driver.getCurrentUrl());
        Thread.sleep(3000);

        //点击 "新闻" 连接
        driver.findElement(By.linkText("新闻")).click();
        System.out.println("now accesss " + driver.getCurrentUrl());
        Thread.sleep(3000);
        //刷新页面
        driver.navigate().refresh();
        Thread.sleep(3000);

        //执行浏览器后退
        driver.navigate().back();
        System.out.println("now accesss " + driver.getCurrentUrl());
        Thread.sleep(3000);

        //执行浏览器前面
        driver.navigate().forward();
        System.out.println("now accesss " + driver.getCurrentUrl());
        Thread.sleep(3000);

        driver.quit();
    }
}
