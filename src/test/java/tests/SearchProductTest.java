package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest {

    @Test
    public void searchValidProduct() {

        // Open Daraz
        driver.get("https://www.daraz.lk/");

        // Search bar
        By searchBar = By.name("q");

        // Type "laptop" and press enter
        driver.findElement(searchBar).sendKeys("laptop", Keys.ENTER);

        // Wait seconds (temporary)
        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}

        // Validate results
        String title = driver.getTitle().toLowerCase();
        Assert.assertTrue(title.contains("laptop"),
                "Search results page title does not contain 'laptop'");
    }
}


