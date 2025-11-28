package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenProductTest extends BaseTest {

    @Test
    public void openFirstProductFromSearchResults() {

        driver.get("https://www.daraz.lk/");

        // 1. Search for a valid product (laptop)
        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        try {
            Thread.sleep(2500); }
        catch (Exception ignored) {}

        // 2. Locate the first product card

        By firstProduct = By.cssSelector("div[data-qa-locator='product-item']");

        WebElement product = driver.findElement(firstProduct);
        product.click();

        try {
            Thread.sleep(2500); }
        catch (Exception ignored) {}

        // 3. Validate Product Detail Page
        String title = driver.getTitle().toLowerCase();
        Assert.assertTrue(title.contains("laptop") || title.contains("product"),
                "PDP title does NOT indicate a product page!");

        // 4. Validate product name element e
        boolean productNameVisible = driver.findElements(By.cssSelector("h1")).size() > 0;

        Assert.assertTrue(productNameVisible,
                "Product name (h1) was NOT found on the product page!");
    }
}

