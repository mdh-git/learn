package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        //设置url地址
        driver.get("https://e.xinrenxinshi.com");

        driver.manage().window().maximize();
        Thread.sleep(3000);


        //获取title信息
        String title = driver.getTitle();
        System.out.println("title:"+title);
        //获取整个加载的信息
        String page = driver.getPageSource();
        //System.out.println("page:"+page);
        //根据id定位
        WebElement mobile = driver.findElement(By.id("mobile"));
        //清除已有的元素
        mobile.clear();
        //放入需要的元素
        mobile.sendKeys("18516157890");
        WebElement verify_code = driver.findElement(By.id("verify_code"));
        verify_code.clear();
        verify_code.sendKeys("5543");

        //根据name定位
        WebElement viewport = driver.findElement(By.name("viewport"));
        System.out.println(viewport.getAttribute("content"));

        //根据class name定位
        WebElement login_container = driver.findElement(By.className("login_container"));
        System.out.println(login_container.getText());

        //根据tag name定位
        WebElement h1 = driver.findElement(By.tagName("h1"));
        System.out.println(h1.getText());

        //通过xpath定位
        WebElement mobile1 = driver.findElement(By.xpath("//*[@id='verify_code']"));
        System.out.println(mobile1.getText());

        driver.get("https://m.baidu.cn");
        driver.manage().window().setSize(new Dimension(480, 800));
        Thread.sleep(2000);

        driver.quit();
        //driver.close();
    }
}
