package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InvalidSearchTest extends BaseTest {

    @Test
    public void invalidSearchShouldShowNoResults() {

        driver.get("https://www.daraz.lk/");

        // 1. Use invalid keyword
        String randomWord = "ace135xyz";

        // 2. Locate search bar
        By searchBar = By.name("q");

        // 3. Perform search
        driver.findElement(searchBar).sendKeys(randomWord, Keys.ENTER);

        // 4. Temporary wait
        try {
            Thread.sleep(2500);
        } catch (Exception ignored) {}

        // 5. Get page source for checking
        String pageSource = driver.getPageSource().toLowerCase();

        // 6. Validate: results page should NOT show product tiles
        boolean noResults =
                pageSource.contains("no results found") ||
                        pageSource.contains("0 items found") ||
                        !pageSource.contains("item");

        Assert.assertTrue(noResults,
                "Invalid search still shows item results!");
    }
}

