package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginWithValidCredentials() {
        // Load from config file
        String baseUrl = ConfigReader.get("base.url");
        String email = ConfigReader.get("email");
        String password = ConfigReader.get("password");

        driver.get(baseUrl);
        System.out.println("Navigated to login page: " + driver.getCurrentUrl());

        LoginPage loginPage = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("Waiting for page load completion...");
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));

            System.out.println("Entering email...");
            loginPage.enterEmail(email);

            System.out.println("Entering password...");
            loginPage.enterPassword(password);

            System.out.println("Clicking login button...");
            loginPage.clickLogin();

            System.out.println("Waiting for login completion...");
            boolean loginSuccess = wait.until(driver -> {
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Current URL during login check: " + currentUrl);

                if (!currentUrl.contains("/login")) {
                    System.out.println("URL no longer contains /login");
                    return true;
                }

                boolean success = loginPage.isLoggedIn().apply(driver);
                System.out.println("Login indicators check result: " + success);
                return success;
            });

            System.out.println("Login completion check result: " + loginSuccess);
            assertTrue(loginSuccess, "Login should succeed and show dashboard elements");

        } catch (Exception e) {
            System.err.println("Login failed with error: " + e.getMessage());
            e.printStackTrace();
            throw new AssertionError("Login failed: " + e.getMessage());
        }
    }
}
