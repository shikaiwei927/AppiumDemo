package com.myid.albumtest;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
public class Demo4 {
	private AndroidDriver<AndroidElement> driver;
    private WebDriverWait wait;
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
        capabilities.setCapability("unicodeKeyboard",true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noreset", true);
//        capabilities.setCapability("automationName","Selendroid");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
    @Test
    public void waitLaunch() throws InterruptedException {
//		swipeGuide();  //首次安装App的时候释放这两行
//		hideAutoBackupGuide();
        wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login_logo")));
}
    /*
     * 登录
     */
    @Test(dependsOnMethods = "waitLaunch")
    public void login() throws InterruptedException{
        driver.findElement(By.id("UserName")).sendKeys("18767191571@163.com");
        driver.findElement(By.id("UserName")).click();
        driver.findElement(By.id("PassWord")).click();
        driver.findElement(By.id("PassWord")).sendKeys("wsk6821051");
        driver.hideKeyboard();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
        driver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
        Assert.assertTrue(driver.findElementByName("本地相册").isDisplayed(),"登录失败");
       
    }
    /*
     * 1本地查看照片信息 和上传到云相册
     */
    @Test(dependsOnMethods = "login")
    public void localphoto() throws InterruptedException{
        driver.findElement(By.name("Camera")).click();
        driver.findElements(By.id("image_photo")).get(0).click();
        driver.findElement(By.id("photo_info_btn")).click();
        Assert.assertTrue(driver.findElement(By.name("相片信息")).isDisplayed(),"查看照片信息失败!");
        driver.findElement(By.name("确定")).click();
        driver.pressKeyCode(4);
        //上传到云相册
        uploadphoto();
    }
    /*
     * 2下载相册和删除云端相片
     */
    @Test(dependsOnMethods = "localphoto")
    public void yunphoto() throws InterruptedException{
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
    	driver.swipe(100, 200, 400, 200, 200);
        driver.findElement(By.id("g_slidemenu_cloud_txt")).click();
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cloud_album_wrap")));
    	driver.findElements(By.id("cloud_album_name")).get(0).click();
    	Assert.assertTrue(driver.findElement(By.id("cloud_image_photo")).isDisplayed(),"上传照片失败!");
    	driver.findElements(By.id("cloud_image_photo")).get(0).click();
    	driver.findElement(By.id("photo_save_btn")).click();//下载相册
    	Thread.sleep(5000);
    	driver.pressKeyCode(4);
    	driver.findElements(By.id("cloud_image_photo")).get(0).click();
    	driver.findElement(By.id("photo_delete_btn")).click();
    	driver.findElement(By.name("确定")).click();
    	Assert.assertTrue(driver.findElement(By.name("相片数：0张")).isDisplayed(),"删除照片失败!");
    	driver.pressKeyCode(4);
    	Thread.sleep(1000);
    	driver.pressKeyCode(4);
    	Thread.sleep(1000);
    	driver.swipe(100, 200, 400, 200, 200);
    	driver.findElement(By.id("g_slidemenu_local_txt")).click();
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
    	Dimension dimension;
        dimension = driver.manage().window().getSize();
        int WIDTH = dimension.getWidth();
        int HEIGHT = dimension.getHeight();
        driver.swipe(WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT-100, 2000);
//    	driver.swipe(400, 500, 400,1000, 2000);
    	Assert.assertTrue(driver.findElementByName("163photo").isDisplayed(),"下载失败!");
    	driver.findElement(By.name("163photo")).click();
    	driver.findElements(By.id("image_photo")).get(0).click();
    	driver.findElement(By.id("photo_delete_btn")).click();
    	driver.findElement(By.name("确定")).click();
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
    }
    /*
     * 本地照片批量上传
     */
    @Test(dependsOnMethods = "yunphoto")
    public void yunaddphoto() throws InterruptedException{
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
		driver.findElement(By.name("Camera")).click();
		driver.findElement(By.className("android.widget.ImageView")).click();
		for(int j=0;j<3;j++){
        driver.findElements(By.id("local_image_check")).get(j).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.id("photo_list_backup_btn")));
        driver.findElement(By.id("photo_list_backup_btn")).click();
        wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("备份报告")));
        Thread.sleep(1000);
        driver.findElement(By.name("云端相册")).click();
    }
    /*
     * 上传上传到云端的相册
     */
    
    @Test(dependsOnMethods="yunaddphoto")
    public void yundelphoto() throws InterruptedException{
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cloud_album_wrap")));
    	driver.findElements(By.id("cloud_album_name")).get(0).click();
		for(int i=0;i<3;i++){
		driver.findElements(By.id("cloud_image_photo")).get(0).click();
		driver.findElement(By.id("photo_delete_btn")).click();
		driver.findElement(By.name("确定")).click();
		Thread.sleep(1500);
		driver.pressKeyCode(4);
		}
		driver.resetApp();
    }
    public void uploadphoto() throws InterruptedException{
    	driver.findElements(By.id("image_photo")).get(0).click();
      Assert.assertTrue(driver.findElementByName("未备份").isDisplayed(),"已备份");
      driver.pressKeyCode(4);
      driver.findElements(By.id("image_photo")).get(0).click();
      driver.findElement(By.id("photo_back_or_not")).click();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cloud_album_name")));
      driver.findElement(By.id("cloud_album_name")).click();
      driver.findElement(By.name("确认备份")).click();
      Thread.sleep(30000);
      driver.closeApp();
      driver.launchApp();
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("local_album_wrap")));
      driver.findElement(By.name("Camera")).click();
      driver.findElements(By.id("image_photo")).get(0).click();
      Assert.assertTrue(driver.findElementByName("已备份").isDisplayed(),"未备份");
      driver.pressKeyCode(4);
      Thread.sleep(1000);
      driver.pressKeyCode(4);
    }
    public void swipeGuide()throws InterruptedException {
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
    public void hideAutoBackupGuide() {
        // AutoBackupGuideActivity.java
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("skipSet")));
        driver.findElement(By.id("skipSet")).click(); // 点击 跳过
    }
   
}
