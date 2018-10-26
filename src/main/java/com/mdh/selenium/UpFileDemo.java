package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

/**
 *
 * @author madonghao
 * @date 2018/10/18
 */
public class UpFileDemo {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        File file = new File("");
        String path = file.getAbsolutePath();
        driver.get(path);

        //定位上传的按钮，添加本地文件
        driver.findElement(By.name("file")).sendKeys("D:\\\\upload_file.txt");
        Thread.sleep(3000);

        driver.quit();
    }
}
