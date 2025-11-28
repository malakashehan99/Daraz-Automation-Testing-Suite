package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyProductDetailsTest extends BaseTest {

    @Test
    public void verifyProductDetails() {

        driver.get("https://www.daraz.lk/");

        // 1. Search product
        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        try {
            Thread.sleep(2000); }
        catch (Exception ignored) {}

        // 2. Open first product
        By firstProduct = By.cssSelector("div[data-qa-locator='product-item']");
        driver.findElement(firstProduct).click();

        try {
            Thread.sleep(2500); }
        catch (Exception ignored) {}

        // 3. Verify product title (H1 exists & not empty)
        WebElement titleElement = driver.findElement(By.cssSelector("h1"));
        String productTitle = titleElement.getText().trim();

        Assert.assertTrue(!productTitle.isEmpty(),
                " Product title is missing!");

        System.out.println(" Product Title Found: " + productTitle);


        // 4. Verify price is visible
        WebElement priceElement = driver.findElement(
                By.cssSelector("span.pdp-price_type_normal")
        );

        String priceText = priceElement.getText().trim();
        Assert.assertTrue(!priceText.isEmpty(),
                " Product price is missing!");

        System.out.println(" Product Price Found: " + priceText);


        // 5. Verify product images/thumbnails are visible
        boolean imagesPresent =
                driver.findElements(By.cssSelector("img")).size() > 0;

        Assert.assertTrue(imagesPresent,
                " No product images found!");

        System.out.println(" Product images found");
    }
}
