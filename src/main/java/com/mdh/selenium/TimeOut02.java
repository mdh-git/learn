package com.mdh.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author madonghao
 * @date 2018/10/16
 */
public class TimeOut02 {

    public static void main(String[] args) {

        /**
         * WebDriver 提供了几种方法来等待元素。
         * implicitlyWait。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常。
         * setScriptTimeout。异步脚本的超时时间。WebDriver可以异步执行脚本，这个是设置异步执行脚本脚本返回结果的超时时间。
         * pageLoadTimeout。页面加载时的超时时间。因为WebDriver会等页面加载完毕再进行后面的操作，所以如果页面超过设置时间依然没有加载完成，那么WebDriver就会抛出异常。
         */

        WebDriver driver = new ChromeDriver();

        //页面加载超时时间设置5s
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.get("https://www.baidu.com/");

        //定位对象时给 10s 的时间, 如果 10s 内还定位不到则抛出异常
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("kw")).sendKeys("selenium");

        //异步脚本的超时时间设置成 3s
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);

        driver.quit();
    }
}
