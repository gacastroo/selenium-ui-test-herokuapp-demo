package com.github.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckboxTest {

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
    void testCheckboxInteraction() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElements(By.cssSelector("input[type='checkbox']")).get(0);
        WebElement checkbox2 = driver.findElements(By.cssSelector("input[type='checkbox']")).get(1);

        // Toggle checkbox 1
        checkbox1.click();
        assertTrue(checkbox1.isSelected());

        // Toggle checkbox 2
        checkbox2.click();
        assertFalse(checkbox2.isSelected());
    }
}
