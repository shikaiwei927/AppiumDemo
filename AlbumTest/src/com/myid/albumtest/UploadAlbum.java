package com.myid.albumtest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UploadAlbum {
	private AndroidDriver<AndroidElement> driver;
	@BeforeClass
    public void setUp() throws Exception {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "Album_netease.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");//手机操作系统的类型
        capabilities.setCapability(MobileCapabilityType.UDID, "JTJ4C15A15012272");//devices ID
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "SCL-TL00H"); // 真机名字
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1"); //真机版本 手机操作系统的版本
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        //对应的被测APK文件路径
//        capabilities.setCapability("unicodeKeyboard",true);//android输入法如果有问题开启这2项
//        capabilities.setCapability("resetKeyboard", true);
		//capabilities.setCapability("automationName", "Selendroid"); //4.2.2或者以下的真机需要配置下automationName为Selendroid
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // 新建session server地址 appium server通用接口
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
   
    @Test
    public void firstDemo() throws InterruptedException {
    	 swipeGuide();
         hideAutoBackupGuide();
         login();
        // 等待登陆app
        WebDriverWait wait = new WebDriverWait(driver, 30);
		// 判断是否存在本地相册
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
        Assert.assertTrue(driver.findElement(By.name("本地相册")).isDisplayed(),"顶部title与期望不符！");

        upload();
    }

    /**
     * 登陆
     */
    private void login() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
		//判断是否存在UserName输入框
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("UserName")));
        
        driver.findElement(By.id("UserName")).sendKeys("18767191571@163.com");
        driver.findElement(By.id("PassWord")).click();
        driver.findElement(By.id("PassWord")).sendKeys("wsk6821051");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//输入账号密码后登陆
        driver.findElement(By.id("login")).click();
    }

    /**
     * 上传图片到手机相册
     */
    private void upload() {
		
        driver.findElement(By.name("Camera")).click();
        driver.findElementsById("image_photo").get(0).click();
        driver.findElement(By.id("photo_back_or_not")).click();
//        driver.findElement(By.name("切换相册")).click();
        driver.findElements(By.id("cloud_album_name")).get(0).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("备份至 <手机相册>")));
        driver.findElement(By.name("确认备份")).click();
       
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
     * 首次打开，用户引导
     */
    public void swipeGuide()throws InterruptedException {
        // GuideActivity.java
        Dimension dimension;
        dimension = driver.manage().window().getSize();
        int SCREEN_WIDTH = dimension.getWidth();
        int SCREEN_HEIGHT = dimension.getHeight();

        System.out.println("被测设备宽高：" + SCREEN_WIDTH + "," + SCREEN_HEIGHT);
        Thread.sleep(5000);
        driver.swipe(SCREEN_WIDTH - 100, SCREEN_HEIGHT / 2, 100,  SCREEN_HEIGHT / 2, 2000);
        Thread.sleep(2000);
        driver.swipe(SCREEN_WIDTH - 100, SCREEN_HEIGHT / 2, 100, SCREEN_HEIGHT / 2, 2000);
        Thread.sleep(2000);
        driver.findElement(By.id("guide_btn")).click(); // 点击 立即体验
    }

    /**
     * 首次进入app，自动备份功能引导，跳过
     */
    public void hideAutoBackupGuide() {
        // AutoBackupGuideActivity.java

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("skipSet")));

        driver.findElement(By.id("skipSet")).click(); // 点击 跳过
    }
}

