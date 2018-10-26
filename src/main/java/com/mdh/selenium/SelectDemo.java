package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author madonghao
 * @date 2018/10/18
 */
public class SelectDemo {

    public static void main(String[] args) throws InterruptedException {

        /**
         * Select类用于定位select标签。
         * selectByValue()方法符用于选取<option>标签的value值。
         */

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        driver.findElement(By.linkText("设置")).click();
        driver.findElement(By.linkText("搜索设置")).click();
        Thread.sleep(3000);

        //<select>标签的下拉框选择
        WebElement el = driver.findElement(By.xpath("//select"));
        Select sel = new Select(el);
        sel.selectByValue("20");
        Thread.sleep(2000);

        driver.quit();
    }
}
