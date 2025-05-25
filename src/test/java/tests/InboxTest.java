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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;

public class InboxTest extends BaseTest {
    
    @BeforeEach
    public void testLoginWithValidCredentials() {
        driver.get("https://app.clickup.com/login");
        System.out.println("Navigated to login page: " + driver.getCurrentUrl());
        
        LoginPage loginPage = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        String email = System.getenv("EMAIL");
        // System.out.println(email);
        String password = System.getenv("PASSWORD");
        // System.out.println(password);
        
        try {
            // Wait for the login form to be present and interactive
            System.out.println("Waiting for page load completion...");
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            
            // Wait for email field to be present and interactable
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));
            
            // Perform login
            System.out.println("Entering email...");
            loginPage.enterEmail(email);
            
            
            System.out.println("Entering password...");
            loginPage.enterPassword(password);
                       
            System.out.println("Clicking login button...");
            loginPage.clickLogin();
            
            // Wait for URL to change or login indicators to appear
            System.out.println("Waiting for login completion...");
            boolean loginSuccess = wait.until(driver -> {
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Current URL during login check: " + currentUrl);
                
                // Check if URL changed from login page
                if (!currentUrl.contains("/login")) {
                    System.out.println("URL no longer contains /login");
                    return true;
                }
                
                // Check for login success indicators
                boolean success = loginPage.isLoggedIn().apply(driver);
                System.out.println("Login indicators check result: " + success);
                return success;
            });
            
            System.out.println("Login completion check result: " + loginSuccess);
            Assertions.assertTrue(loginSuccess, "Login should succeed and show dashboard elements");
            
            // Additional wait after successful login
            System.out.println("Waiting for page to stabilize...");
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.err.println("Login failed with error: " + e.getMessage());
            e.printStackTrace();
            throw new AssertionError("Login failed: " + e.getMessage());
        }
    }

    @Test
    public void testInboxNavigation() {
        LoginPage loginPage = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            System.out.println("\nStarting inbox navigation test");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Verify we're still logged in
            assertTrue(wait.until(loginPage.isLoggedIn()), 
                     "Should be logged in before proceeding with inbox navigation");
            
            System.out.println("Verified logged-in state, proceeding to click inbox");
            loginPage.clickInboxLink(wait);
            
            System.out.println("Clicked inbox, waiting for title");
            assertTrue(loginPage.isInboxTitlePresent(wait), 
                     "Inbox title should be visible after navigation");
            
            System.out.println("Inbox navigation test completed successfully");
            
        } catch (Exception e) {
            System.err.println("Inbox navigation test failed with error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
