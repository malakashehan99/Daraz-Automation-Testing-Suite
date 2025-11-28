package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class EndToEndFlowTest extends BaseTest {

    @Test
    public void completeShoppingFlow() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        // 1. Launch Daraz Home Page
        driver.get("https://www.daraz.lk/");
        Assert.assertTrue(driver.getTitle().length() > 0, "Daraz home page did not load!");
        System.out.println("✓ Launched Daraz.lk");


        // 2. Search for a product (Laptop)

        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys("laptop", Keys.ENTER);
        Thread.sleep(2500);

        Assert.assertTrue(driver.getTitle().toLowerCase().contains("laptop"),
                "Search results title does not show laptop!");
        System.out.println("✓ Search results loaded");


        // 3. Apply Price Filter (100,000 - 500,000)

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");
        Thread.sleep(1500);

        WebElement minInput = driver.findElement(By.xpath("//input[@placeholder='Min']"));
        WebElement maxInput = driver.findElement(By.xpath("//input[@placeholder='Max']"));

        minInput.clear();
        minInput.sendKeys("100000");

        maxInput.clear();
        maxInput.sendKeys("500000");

        WebElement applyBtn = driver.findElement(
                By.xpath("//div[contains(@class,'gJ98q')]//button[contains(@class,'ant-btn-icon-only')]")
        );
        applyBtn.click();

        Thread.sleep(3000);
        Assert.assertTrue(
                driver.getCurrentUrl().contains("10000") && driver.getCurrentUrl().contains("50000"),
                "Price filter NOT applied!"
        );
        System.out.println("Price filter applied");


        // 4. Sort Products (Price Low to High)

        Thread.sleep(2500);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Open sort dropdown using JS (shadow DOM)
        WebElement sortDropdown = (WebElement) js.executeScript(
                "return document.querySelector('div.ant-select.ant-select-lg').shadowRoot || " +
                        "document.querySelector('div.ant-select.ant-select-lg');"
        );

        sortDropdown.click();

        WebElement lowToHigh = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Price low to high')]"))
        );
        lowToHigh.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("priceasc"),
                "Sorting Low to High NOT applied!");
        System.out.println("Sorting applied (Low to High)");


        // 5. Open the first product

        Thread.sleep(2000);
        WebElement firstProduct = driver.findElement(By.cssSelector("div[data-qa-locator='product-item']"));
        firstProduct.click();

        Thread.sleep(3000);
        Assert.assertTrue(driver.findElements(By.cssSelector("h1")).size() > 0,
                "Product page not opened!");
        System.out.println("Product page opened");


        // 6. Verify Product Details (Name & Price)

        WebElement title = driver.findElement(By.cssSelector("h1"));
        String productTitle = title.getText().trim();
        Assert.assertFalse(productTitle.isEmpty(), "Product title missing!");

        WebElement price = driver.findElement(By.cssSelector("span.pdp-price_type_normal"));
        Assert.assertFalse(price.getText().trim().isEmpty(), "Product price missing!");

        System.out.println("Product details verified");


        // 7. Verify Image Gallery Thumbnail Switch

        WebElement mainImage = driver.findElement(
                By.cssSelector("img.pdp-mod-common-image.gallery-preview-panel__image")
        );
        String beforeSrc = mainImage.getAttribute("src");

        List<WebElement> thumbnails = driver.findElements(
                By.cssSelector("img.item-gallery__thumbnail-image")
        );
        Assert.assertTrue(thumbnails.size() > 1, "No thumbnails found!");

        thumbnails.get(1).click();
        Thread.sleep(2000);

        String afterSrc = mainImage.getAttribute("src");

        Assert.assertNotEquals(beforeSrc, afterSrc,
                "Main image did NOT update after clicking thumbnail!");

        System.out.println("Image gallery thumbnail switching verified");


        // End of E2E Test

        System.out.println("\n END-TO-END FLOW PASSED SUCCESSFULLY!");
    }
}

