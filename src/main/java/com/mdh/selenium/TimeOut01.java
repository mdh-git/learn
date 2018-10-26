package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author madonghao
 * @date 2018/10/16
 */
public class TimeOut01 {

    public static void main(String[] args) throws InterruptedException {

        /**
         * WebDriverWait类是由WebDirver提供的等待方法。
         * 在设置时间内，默认每隔一段时间检测一次当前页面元素是否存在，
         * 如果超过设置时间检测不到则抛出异常。具体格式如下：
         * WebDriverWait(driver, 10, 1)
         * driver： 浏览器驱动。 10： 最长超时时间， 默认以秒为单位。 1： 检测的的间隔（步长） 时间， 默认为 0.5s。
         */

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        //显式等待， 针对某个元素等待
        WebDriverWait wait = new WebDriverWait(driver,10,1);

        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("kw"));
            }
        }).sendKeys("selenium");

        driver.findElement(By.id("su")).click();
        Thread.sleep(2000);

        driver.quit();
    }
}
