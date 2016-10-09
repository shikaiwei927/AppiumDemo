package com.myid.albumtest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Appium Android Demo
 * @author yuqi
 *
 */
  
public class DeleteAlbum {
    private AndroidDriver<AndroidElement> driver;
    @BeforeClass
    public void setUp() throws Exception {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, "JTJ4C15A15012272"); 
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "SCL-TL00H"); 
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.netease.cloudalbum");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
    @Test
    public void deleteAlbum() throws InterruptedException {
        // 等待进入app
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
          
        driver.swipe(100, 200, 400, 200, 200);
        driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
        driver.findElements(By.name("cloud_image_photo")).get(0).click();
        driver.findElements(By.id("text1")).get(0).click();
        driver.findElement(By.id("photo_delete_btn")).click();
		driver.findElement(By.name("确定")).click();
	
    }

}

