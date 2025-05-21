package org.example;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;

public class ClockTest {
    private AndroidDriver driver;

    @BeforeEach
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:automationName","UiAutomator2");
        caps.setCapability("appium:deviceName","Pixel_2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeEach
    public void runClock() {
        driver.terminateApp("com.google.android.deskclock");
        driver.activateApp("com.google.android.deskclock");
    }

    @Test
    public void checkTime() {
        driver.findElement(By.id("com.google.android.deskclock:id/digital_clock"));
        String appTime = driver.findElement(By.id("com.google.android.deskclock:id/digital_clock")).getText().trim();

        LocalTime systemTime = LocalTime.now().withSecond(0).withNano(0);

        System.out.println("Clock time: " + appTime);
        System.out.println("Local system time: " + systemTime);

        Assertions.assertEquals(systemTime, appTime, "App clock time does not match local system time.");
    }

    @Test
    public void activateTimer() throws InterruptedException {
        driver.findElement(By.xpath("//android.widget.TextView[@text='TIMER']")).click();

        driver.findElement(By.id("com.google.android.deskclock:id/right_button")).click();
        driver.findElement(By.id("com.google.android.deskclock:id/timer_setup_digit_1")).click();
        driver.findElement(By.id("com.google.android.deskclock:id/timer_setup_digit_0")).click();
        driver.findElement(By.id("com.google.android.deskclock:id/fab")).click();

        String timeLeft = driver.findElement(By.id("com.google.android.deskclock:id/timer_time_text")).getText();
        System.out.println("Time left: " + timeLeft);
//        Assertions.assertEquals(5, timeLeft);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Toast[contains(@text, 'Timer')]")));
        driver.findElement(By.id("android:id/button1")).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
