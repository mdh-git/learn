package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author madonghao
 * @date 2018/10/19
 */
public class JSDemo {
    public static void main(String[] args) throws InterruptedException {
        /**
         * window.scrollTo(左边距,上边距);
         * window.scrollTo(0,450);
         */

        WebDriver driver = new ChromeDriver();

        //设置浏览器窗口大小
        //window().setSize()方法将浏览器窗口设置为固定宽高显示，
        // 目的是让窗口出现水平和垂直滚动条。
        // 然后通过executeScript()方法执行JavaScripts代码来移动滚动条的位置。
        driver.manage().window().setSize(new Dimension(700,600));
        driver.get("https://www.baidu.com");

        //进行百度搜索
        driver.findElement(By.id("kw")).clear();
        driver.findElement(By.id("kw")).sendKeys("webdriver api");
        driver.findElement(By.id("su")).click();
        Thread.sleep(2000);

        //将页面滚动条拖到底部
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(100,350);");
        Thread.sleep(3000);

        driver.quit();

    }
}
