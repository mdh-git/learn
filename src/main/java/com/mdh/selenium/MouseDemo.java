package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class MouseDemo {

    public static void main(String[] args) throws InterruptedException {

        /**
         * contextClick() 右击
         clickAndHold() 鼠标点击并控制
         doubleClick() 双击
         dragAndDrop() 拖动
         release() 释放鼠标
         perform() 执行所有Actions中存储的行为

         import org.openqa.selenium.interactions.Actions;
         导入提供鼠标操作的 ActionChains 类
         Actions(driver) 调用Actions()类，将浏览器驱动driver作为参数传入。
         clickAndHold() 方法用于模拟鼠标悬停操作， 在调用时需要指定元素定位。
         perform() 执行所有ActionChains中存储的行为， 可以理解成是对整个操作的提交动作。
         */

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");

        Actions action = new Actions(driver);

        // 鼠标右键点击指定的元素
        action.contextClick(driver.findElement(By.linkText("贴吧"))).perform();

        // 鼠标右键双击击指定的元素
        //action.doubleClick(driver.findElement(By.linkText("贴吧"))).perform();


        // 鼠标点击并控制
        /*WebElement search_setting = driver.findElement(By.linkText("设置"));
        Actions action = new Actions(driver);
        action.clickAndHold(search_setting).perform();
        action.release();
        Thread.sleep(1000);

        WebElement search_setting1 = driver.findElement(By.linkText("地图"));
        Actions action1 = new Actions(driver);
        action1.contextClick(search_setting1).perform();*/

        /** 鼠标拖拽动作,将 source 元素拖放到 target 元素的位置。*/
        WebElement source = driver.findElement(By.linkText("贴吧"));
        WebElement target = driver.findElement(By.id("su"));
        Actions action2 = new Actions(driver);
        action2.dragAndDrop(source,target).perform();

        // 释放鼠标
        action2.release().perform();
        //driver.quit();
    }
}
