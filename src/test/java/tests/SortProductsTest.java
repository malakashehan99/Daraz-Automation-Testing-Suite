package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SortProductsTest extends BaseTest {
    @Test
    public void sortByPriceLowToHigh() {

        driver.get("https://www.daraz.lk/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Search laptop
        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        // Wait 3 seconds for shadow DOM content load
        try {
            Thread.sleep(3000);
        }
        catch (Exception ignored) {}

        // JS to enter shadow roots
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement sortDropdown = (WebElement) js.executeScript(
                "return document.querySelector('div.ant-select.ant-select-lg').shadowRoot || " +
                        "document.querySelector('div.ant-select.ant-select-lg');"
        );

        sortDropdown.click();

        // Select “Price low to high”
        WebElement lowToHigh = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(),'Price low to high')]")
        ));
        lowToHigh.click();

        // Validate URL contains sort=priceasc
        Assert.assertTrue(driver.getCurrentUrl().contains("sort=priceasc"),
                "Sorting Low to High NOT applied!");
    }


}