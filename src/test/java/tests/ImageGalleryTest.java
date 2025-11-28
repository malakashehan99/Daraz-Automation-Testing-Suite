package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ImageGalleryTest extends BaseTest {

    @Test
    public void verifyImageThumbnailSwap() {

        driver.get("https://www.daraz.lk/");

        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        try {
            Thread.sleep(2500);}
        catch (Exception ignored) {}

        // Open first product
        driver.findElement(By.cssSelector("div[data-qa-locator='product-item']")).click();

        try {
            Thread.sleep(3000);}
        catch (Exception ignored) {}

        // Main product image
        WebElement mainImage = driver.findElement(
                By.cssSelector("img.pdp-mod-common-image.gallery-preview-panel__image")
        );

        String beforeSrc = mainImage.getAttribute("src");
        System.out.println("Main BEFORE = " + beforeSrc);

        // All thumbnail images
        List<WebElement> thumbnails = driver.findElements(
                By.cssSelector("img.item-gallery__thumbnail-image")
        );

        Assert.assertTrue(thumbnails.size() > 1,
                "No thumbnails found!");

        //  Click the second thumbnail
        thumbnails.get(1).click();

        try {
            Thread.sleep(2000);}
        catch (Exception ignored) {}

        String afterSrc = mainImage.getAttribute("src");
        System.out.println("Main AFTER = " + afterSrc);

        //  Validation
        Assert.assertNotEquals(beforeSrc, afterSrc,
                "Image did NOT change after clicking thumbnail.");
    }
}
