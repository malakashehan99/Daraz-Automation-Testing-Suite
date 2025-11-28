package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class PaginationTest extends BaseTest {

    @Test
    public void verifyPaginationWorks() {

        driver.get("https://www.daraz.lk/");

        driver.findElement(By.name("q")).sendKeys("laptop");
        driver.findElement(By.name("q")).submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for pagination to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.ant-pagination")));

        // Remove all obstructing popups
        hideObstructingElements();

        // Get current page number before clicking
        int beforePage = Integer.parseInt(
                driver.findElement(By.cssSelector("li.ant-pagination-item-active")).getText()
        );
        System.out.println("Before Page = " + beforePage);

        WebElement nextBtn = driver.findElement(By.cssSelector("li.ant-pagination-next"));

        // Scroll next button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextBtn);

        // Remove popups again (sometimes reappears)
        hideObstructingElements();

        // Click safely using JS (final failsafe)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);

        // Wait until page index updates
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(
                        By.cssSelector("li.ant-pagination-item-active"),
                        String.valueOf(beforePage)
                )
        ));

        int afterPage = Integer.parseInt(
                driver.findElement(By.cssSelector("li.ant-pagination-item-active")).getText()
        );

        System.out.println("After Page = " + afterPage);

        Assert.assertEquals(afterPage, beforePage + 1, "Pagination did not move to next page");
    }

    // Helper: Remove popups

    private void hideObstructingElements() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Hide top banner
        try {
            WebElement banner = driver.findElement(By.id("topActionLiveUpBanner"));
            js.executeScript("arguments[0].style.display='none';", banner);
            System.out.println("Top banner hidden");
        } catch (Exception ignored) {}

        // Close chat widget
        try {
            WebElement chat = driver.findElement(By.cssSelector("button.im-app__cont-minimize"));
            chat.click();
            System.out.println("Chat popup closed");
        } catch (Exception ignored) {}

        // Hide side message popup
        try {
            WebElement msg = driver.findElement(By.cssSelector("div.message-view"));
            js.executeScript("arguments[0].style.display='none';", msg);
            System.out.println("Message popup hidden");
        } catch (Exception ignored) {}

        // Remove any overlay
        try {
            js.executeScript(
                    "document.querySelectorAll('*').forEach(el => {" +
                            " if (window.getComputedStyle(el).zIndex > 1000) {" +
                            "     el.style.display='none';" +
                            " }" +
                            "});"
            );
            System.out.println("Extra overlays removed");
        } catch (Exception ignored) {}
    }

}






