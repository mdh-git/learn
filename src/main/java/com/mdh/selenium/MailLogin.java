package com.mdh.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author madonghao
 * @date 2018/10/16
 */
public class MailLogin {

    public static void main(String[] args) throws Exception {

        /**
         * frame/iframe 表单嵌套页面的应用，
         * WebDriver 只能在一个页面上对元素识别与 定位，
         * 对于 frame/iframe 表单内嵌页面上的元素无法直接定位。
         *
         * switchTo().frame()方法将当前定位的主体切换为 frame/iframe 表单的内嵌页面中。
         * switchTo().defaultContent()方法跳出表单。
         */

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.126.com");
        try {
            WebElement xf = driver.findElement(By.xpath("//*[@id='loginDiv']/iframe"));
            driver.switchTo().frame(xf);
            driver.findElement(By.name("email")).clear();
            driver.findElement(By.name("email")).sendKeys("username");
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys("password");
            driver.findElement(By.id("dologin")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e){
            robotSnapshot();
        }

    }

    /**
     * 截屏方法二、Robot实现截屏
     * @throws Exception
     */
    public static void robotSnapshot() throws Exception {
        //调用截图方法
        BufferedImage img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(img, "png", new File("robot_screen01.png"));
    }
}
