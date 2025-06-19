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
public class HerokuappLoginTest {

    private WebDriver driver;
    private final String BASE_URL = "https://the-internet.herokuapp.com";

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
    void testLoginFlow() {
        driver.get(BASE_URL);
        assertTrue(driver.getTitle().contains("The Internet"));

        driver.findElement(By.linkText("Form Authentication")).click();
        assertTrue(driver.getCurrentUrl().contains("/login"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys("tomsmith");
        passwordInput.sendKeys("SuperSecretPassword!");
        loginButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        assertTrue(successMessage.getText().contains("You logged into a secure area!"));

        // Logout
        driver.findElement(By.cssSelector(".icon-2x.icon-signout")).click();
        WebElement loginPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        assertTrue(loginPage.isDisplayed());
    }
}
