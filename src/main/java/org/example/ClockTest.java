package org.example;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class ClockTest {
    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName","UiAutomator2");
        caps.setCapability("appium:deviceName","Pixel_5");
//        caps.setCapability("appium:appPackage", "com.google.android.deskclock");
//        caps.setCapability("appium:appActivity", "com.android.deskclock.DeskClock");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @BeforeEach
    public void runClock() {
        driver.activateApp("com.google.android.deskclock");
    }

    @Test
    public void openTimerTab() {
        driver.findElement(By.id("com.google.android.deskclock:id/digital_clock")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Timer']")).click();
    }

    @Test
    public void activateTimer() throws InterruptedException {
        Actions action = new Actions(driver);
        action.sendKeys("30").perform();
        driver.findElement(By.id("com.google.android.deskclock:id/fab")).click();

        Thread.sleep(5000); // wait 5 seconds
        String timeLeft = driver.findElement(By.id("com.google.android.deskclock:id/countdown")).getText();
        System.out.println("Time left: " + timeLeft);
        Assertions.assertEquals(25, timeLeft);
    }

    @Test
    public void disableTimer() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Button[@text='Stop']"))).click();
    }

    @AfterEach
    public void shutdownClock() {
        driver.terminateApp("com.google.android.deskclock");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
