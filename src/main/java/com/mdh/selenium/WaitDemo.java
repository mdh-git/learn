package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @Author: madonghao
 * @Date: 2018/12/26 21:56
 */
public class WaitDemo {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.126.com");

        //设置10秒
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement xf = driver.findElement(By.xpath("//*[@id='loginDiv']/iframe"));
        driver.switchTo().frame(xf);

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(new ExpectedCondition<WebElement>(){
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.linkText(""));
            }}).click();
    }
}
