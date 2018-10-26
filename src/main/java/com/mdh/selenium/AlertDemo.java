package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author madonghao
 * @date 2018/10/18
 */
public class AlertDemo {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 在 WebDriver中处理JavaScript所生成的alert、confirm以及prompt
         * switch_to_alert()方法定位到alert/confirm/prompt，
         * 然后使用text/accept/dismiss/sendKeys等方法进行操作。
         *
         * getText()： 返回 alert/confirm/prompt 中的文字信息。
         * accept()： 接受现有警告框。
         * dismiss()： 解散现有警告框。
         * sendKeys(keysToSend)： 发送文本至警告框。
         * keysToSend： 将文本发送至警告框。
         */
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        driver.findElement(By.linkText("设置")).click();
        driver.findElement(By.linkText("搜索设置")).click();
        Thread.sleep(2000);

        //保存设置
        driver.findElement(By.className("prefpanelgo")).click();

        //接收弹窗
        driver.switchTo().alert().accept();
        //String text = driver.switchTo().alert().getText();
        //System.out.println(text);
        Thread.sleep(5000);

        driver.quit();
    }
}
