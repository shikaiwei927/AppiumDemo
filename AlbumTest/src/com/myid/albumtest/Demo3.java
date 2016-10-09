package com.myid.albumtest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class Demo3{
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
//	        capabilities.setCapability("unicodeKeyboard",true);//android输入法如果有问题开启这2项
//	        capabilities.setCapability("resetKeyboard", true);
			//capabilities.setCapability("automationName", "Selendroid"); //4.2.2或者以下的真机需要配置下automationName为Selendroid
	        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // 新建session server地址 appium server通用接口
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDown() throws Exception {
//	        driver.quit();
	    }
	
	@Test
	public void firstDemo() throws InterruptedException{
			//等待进入app 登录app
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_logo")));
			driver.findElements(By.className("android.widget.EditText")).get(0).sendKeys("18767191571@163.com");
			driver.findElements(By.className("android.widget.EditText")).get(0).click();
			driver.findElements(By.className("android.widget.EditText")).get(1).sendKeys("wsk6821051");
			driver.hideKeyboard();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
			driver.findElement(By.id("login")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("本地相册")));
			//访问云端相册和新建相册
			driver.swipe(100,200,400,200,200);
			driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("我的云相册")));
			driver.findElements(By.className("android.widget.ImageView")).get(2).click();
			driver.findElement(By.id("createalbum_edit_name")).click();
			driver.findElement(By.id("createalbum_edit_name")).sendKeys("123");
			driver.hideKeyboard();
			driver.findElement(By.id("createalbum_confirm_btn")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("123")));
			//批量添加相片
			driver.findElement(By.id("upload_into_thisalbum")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("选择备份相片")));
			driver.findElement(By.name("Camera")).click();
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("备份相片")));
			driver.findElements(By.id("cloud_image_photo")).get(0).click();
			driver.findElements(By.id("cloud_image_photo")).get(5).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("photo_list_backup_btn")));
			driver.findElement(By.id("photo_list_backup_btn")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name("备份报告")));
			driver.findElement(By.name("云端相册")).click();
			driver.swipe(100,200,400,200,200);
			driver.findElement(By.id("g_slidemenu_set_txt")).click();
			driver.findElement(By.name("注 销")).click();
			driver.findElement(By.name("退出")).click();
			driver.pressKeyCode(4);
	}
}