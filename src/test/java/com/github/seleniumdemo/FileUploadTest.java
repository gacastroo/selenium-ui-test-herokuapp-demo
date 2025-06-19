package com.github.seleniumdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploadTest {

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
    void testFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-upload")));
        WebElement uploadButton = driver.findElement(By.id("file-submit"));

        // Ruta a un archivo temporal (puedes crear un archivo vacío "testfile.txt" para pruebas)
        String filePath = Paths.get(System.getProperty("user.home"), "testfile.txt").toString();

        uploadInput.sendKeys(filePath);
        uploadButton.click();

        WebElement uploadedFiles = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files")));
        assertTrue(uploadedFiles.isDisplayed());
    }
}
