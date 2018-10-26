package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author madonghao
 * @date 2018/10/15
 */
public class BaiduDemo {
    public static void main(String[] args) throws InterruptedException {

        /**
         * findElements(By.id())
         * findElements(By.name())
         * findElements(By.className())
         * findElements(By.tagName())
         * findElements(By.linkText())
         * findElements(By.partialLinkText())
         * findElements(By.xpath())
         * findElements(By.cssSelector())
         */

        WebDriver driver = new ChromeDriver();
        //设置窗口为最大
        driver.manage().window().maximize();
        driver.get("https://www.baidu.com/");

        //获得百度输入框的尺寸
        WebElement size = driver.findElement(By.id("kw"));
        System.out.println(size.getSize());

        //返回百度页面底部备案信息
        WebElement text = driver.findElement(By.id("cp"));
        System.out.println(text.getText());

        //返回元素的属性值， 可以是 id、 name、 type或元素拥有的其它任意属性
        WebElement ty = driver.findElement(By.id("kw"));
        System.out.println(ty.getAttribute("name"));

        //返回元素的结果是否可见， 返回结果为 True 或 False
        WebElement display = driver.findElement(By.id("kw"));
        System.out.println(display.isDisplayed());

        WebElement imput1 = driver.findElement(By.cssSelector("input[type='hidden'][value='utf-8']"));
        System.out.println("imput1(ie) : " +imput1.getAttribute("name"));

        WebElement input = driver.findElement(By.cssSelector("[name = ie]"));
        System.out.println("input:" + input.getAttribute("value"));

        WebElement form = driver.findElement(By.cssSelector("div#head > div.head_wrapper > div.s_form "));
        System.out.println("from:" + form.getAttribute("src"));

        WebElement span = driver.findElement(By.cssSelector("span.tools > span#mHolder > div#mCon > span"));
        System.out.println("span:" + span.getAttribute("innerHTML"));
        //System.out.print(driver.getPageSource());

        WebElement li_py = driver.findElement(By.cssSelector("span.tools > span#mHolder > ul#mMenu > li:nth-child(2) > a"));
        System.out.println("inputrn:" + li_py.getAttribute("innerHTML") + li_py.getAttribute("name"));

        WebElement a_trmap  = driver.findElement(By.cssSelector("div#u1 > a:nth-child(2)"));
        System.out.println("inputrn:" + a_trmap.getText() + a_trmap.getAttribute("name") + a_trmap.getAttribute("class"));

        WebElement search_text = driver.findElement(By.id("kw"));
        WebElement search_button = driver.findElement(By.id("su"));

        /*search_text.sendKeys("Java");
        Thread.sleep(3000);
        search_text.clear();
        search_text.sendKeys("Selenium");
        Thread.sleep(3000);
        search_button.click();*/
        //search_text.submit();

        //driver.quit();
    }
}
