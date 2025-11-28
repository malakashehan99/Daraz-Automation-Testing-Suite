package tests;

import org.testng.annotations.Test;

public class LaunchDarazTest extends BaseTest {

    @Test
    public void launchWebsite() {
        driver.get("https://www.daraz.lk/");
        System.out.println("Page Title: " + driver.getTitle());
    }
}

