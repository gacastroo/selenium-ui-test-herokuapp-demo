package com.github.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DynamicLoadingTest {

    private WebDriver driver;

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testDynamicLoading() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement startButton = driver.findElement(By.cssSelector("#start button"));
        startButton.click();

        WebElement helloWorld = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        assertTrue(helloWorld.getText().contains("Hello World!"));
    }
}
