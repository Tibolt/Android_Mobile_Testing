package org.example;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class WebviewTest {
    private AndroidDriver driver;

    @BeforeEach
    public void SetUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName","UiAutomator2");
        caps.setCapability("appium:deviceName","Pixel_2");
        caps.setCapability("appium:appPackage", "com.wdiodemoapp");
        caps.setCapability("appium:appActivity", ".MainActivity");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testWebviewAndSwitch() throws InterruptedException {
        driver.findElement(By.xpath("//android.widget.TextView[@text=\"Webview\"]")).click();
        Thread.sleep(7000);
        driver.context("WEBVIEW_com.wdiodemoapp");
        boolean RobotVisible = driver.findElement(By.cssSelector("h1.hero_title")).isDisplayed();
        Assertions.assertTrue(RobotVisible);

        driver.context("NATIVE_APP");
        driver.findElement(By.xpath("//android.widget.TextView[@text=\"Home\"]")).click();
        boolean LogoVisible = driver.findElement(By.xpath("//android.widget.TextView[@text=\"WEBDRIVER\"]")).isDisplayed();
        Assertions.assertTrue(LogoVisible);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
