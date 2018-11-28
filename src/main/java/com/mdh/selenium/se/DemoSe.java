package com.mdh.selenium.se;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author madonghao
 * @date 2018/11/19
 */
public class DemoSe {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        String url = "http://issue.cpic.com.cn/ecar/view/portal/page/common/login.html";
        driver.get("http://issue.cpic.com.cn/ecar/view/portal/page/common/login.html");
        driver.manage().window().maximize();


        WebElement username = driver.findElement(By.cssSelector("#j_username"));
        username.clear();
        username.sendKeys("abc");

        WebElement password = driver.findElement(By.cssSelector("#_password"));
        password.clear();
        password.sendKeys("aaaaa");

        WebElement verifyCode = driver.findElement(By.cssSelector("#verifyCode"));
        verifyCode.clear();
        verifyCode.sendKeys("adbc");

        Actions action = new Actions(driver);

        // 鼠标右键点击指定的元素
        action.doubleClick(driver.findElement(By.linkText("立即登录"))).perform();


        Thread.sleep(3000);
        driver.quit();
    }
}
