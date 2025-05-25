package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;


public class TitleTest extends BaseTest {
    
    @Test
    public void testPageTitle() {
        driver.get("https://app.clickup.com/login");  // Replace with your actual URL

        String title = driver.getTitle();
        System.out.println("Page title is: " + title);

        assertEquals("ClickUp", title, "Page title should match expected value");
    }


}
