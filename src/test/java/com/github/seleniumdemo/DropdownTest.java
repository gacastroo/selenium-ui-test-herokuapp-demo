package com.github.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DropdownTest {

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
    void testDropdownSelection() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        dropdown.selectByVisibleText("Option 2");

        WebElement selectedOption = dropdown.getFirstSelectedOption();
        assertEquals("Option 2", selectedOption.getText());
    }
}
