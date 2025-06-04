package org.example;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

public class DragTest {
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
    public void testDragAndDrop() throws InterruptedException {
        driver.findElement(By.xpath("//android.widget.TextView[@text=\"Drag\"]")).click();
        String[] elements = {"l1", "l2", "l3", "r1", "r2", "r3", "c1", "c2", "c3"};

        for (String id : elements) {
            WebElement dragElement = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='drag-" + id + "']/android.widget.ImageView"));
            WebElement dropElement = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='drop-" + id + "']/android.view.ViewGroup"));

            dragAndDrop(dragElement, dropElement);
        }
        Assertions.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"You made it, click retry if you want to try it again.\"]")).isDisplayed());
    }

    private void dragAndDrop(WebElement from, WebElement to) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);

        int startX = from.getLocation().getX() + from.getSize().getWidth() / 2;
        int startY = from.getLocation().getY() + from.getSize().getHeight() / 2;
        int endX = to.getLocation().getX() + to.getSize().getWidth() / 2;
        int endY = to.getLocation().getY() + to.getSize().getHeight() / 2;

        dragNDrop.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(dragNDrop));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
