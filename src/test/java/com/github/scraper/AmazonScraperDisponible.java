package com.github.scraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AmazonScraperDisponible {

    public static void main(String[] args) throws IOException {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.amazon.es/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Aceptar cookies
            try {
                WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
                acceptCookies.click();
                System.out.println("Banner de cookies cerrado.");
            } catch (TimeoutException e) {
                System.out.println("No se mostró banner de cookies.");
            }

            // Buscar
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
            searchInput.sendKeys("RTX 4070");
            searchInput.sendKeys(Keys.RETURN);

            // Espera resultados
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.s-main-slot")));

            // Sacar lista de productos (div.s-result-item)
            List<WebElement> products = driver.findElements(By.cssSelector("div.s-main-slot > div.s-result-item"));

            FileWriter csvWriter = new FileWriter("amazon_availability.csv");
            csvWriter.append("Producto,Disponibilidad\n");

            int count = 0;

            for (WebElement product : products) {
                try {
                    // Título
                    String title = product.findElement(By.cssSelector("h2 span")).getText();

                    String disponibilidad;

                    try {
                        // Si tiene precio
                        String price = product.findElement(By.cssSelector("span.a-price > span.a-offscreen")).getText();
                        disponibilidad = "Disponible - " + price;
                    } catch (NoSuchElementException e) {
                        // Si no hay precio
                        disponibilidad = "No disponible";
                    }

                    System.out.println(title + " - " + disponibilidad);
                    csvWriter.append("\"").append(title.replace(",", " "))
                             .append("\",").append(disponibilidad.replace(",", ".")).append("\n");

                    count++;

                } catch (NoSuchElementException e) {
                    // Si no hay título → ignorar este producto
                }
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Scraping completado. Productos guardados: " + count);

        } finally {
            driver.quit();
        }
    }
}
