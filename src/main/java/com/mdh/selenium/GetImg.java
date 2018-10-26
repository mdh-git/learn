package com.mdh.selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author madonghao
 * @date 2018/10/19
 */
public class GetImg {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile,new File("D:\\haha\\screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
