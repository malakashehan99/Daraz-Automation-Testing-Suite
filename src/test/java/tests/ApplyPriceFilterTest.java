package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplyPriceFilterTest extends BaseTest {

    @Test
    public void applyPriceFilterAndVerify() throws InterruptedException {

        driver.get("https://www.daraz.lk/");

        // 1. Search for the product
        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        Thread.sleep(3000);

        // 2. Scroll to price filter
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
        Thread.sleep(1500);

        // 3. Enter Min and Max
        WebElement minInput = driver.findElement(By.xpath("//input[@placeholder='Min']"));
        WebElement maxInput = driver.findElement(By.xpath("//input[@placeholder='Max']"));

        minInput.clear();
        minInput.sendKeys("10000");

        maxInput.clear();
        maxInput.sendKeys("50000");

        // 4. Click the apply button
        WebElement applyBtn = driver.findElement(
                By.xpath("//div[contains(@class,'gJ98q')]//button[contains(@class,'ant-btn-icon-only')]")
        );
        applyBtn.click();

        Thread.sleep(4000);

        // 5. VALIDATION: Verify URL contains applied filter
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Filtered URL: " + currentUrl);

        Assert.assertTrue(
                currentUrl.contains("10000") && currentUrl.contains("50000"),
                "Price filter not applied in URL!"
        );

    }
}
