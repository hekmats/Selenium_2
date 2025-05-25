package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class LoginPage {
    private WebDriver driver;

    private By emailInput = By.xpath("//input[@type='email']");
    private By passwordInput = By.xpath("//input[@type='password']");
    private By loginButton = By.xpath("//button[contains(., 'Log In')]");
    
    private By workspaceContainer = By.cssSelector("[class*=workspace], [class*=main-content]");
    private By navigationMenu = By.cssSelector("[class*=nav], [class*=sidebar], [class*=menu]");
    private By headerMenu = By.cssSelector("[class*=header], .cu-top-nav");
    
    private By inboxLink = By.xpath("//a[contains(@href, '/inbox') and contains(., 'Inbox')]");
    private By inboxTitle = By.xpath("//h1[contains(@class, 'title') and contains(text(), 'Inbox')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WebElement emailElement = driver.findElement(emailInput);
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordInput);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public ExpectedCondition<Boolean> isLoggedIn() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    boolean hasWorkspace = !driver.findElements(workspaceContainer).isEmpty();
                    boolean hasNavigation = !driver.findElements(navigationMenu).isEmpty();
                    boolean hasHeader = !driver.findElements(headerMenu).isEmpty();
                    
                    System.out.println("Login verification check:");
                    System.out.println("- Workspace container present: " + hasWorkspace);
                    System.out.println("- Navigation menu present: " + hasNavigation);
                    System.out.println("- Header menu present: " + hasHeader);
                    System.out.println("- Current URL: " + driver.getCurrentUrl());
                    
                    return hasWorkspace || hasNavigation || hasHeader;
                } catch (Exception e) {
                    System.out.println("Exception in isLoggedIn check: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public String toString() {
                return "login verification to complete";
            }
        };
    }

    public void clickInboxLink(WebDriverWait wait) {
        try {
            System.out.println("Starting inbox link click process...");
            wait.until(ExpectedConditions.presenceOfElementLocated(inboxLink));
            
            WebElement inbox = driver.findElement(inboxLink);
            String inboxUrl = inbox.getAttribute("href");
            System.out.println("Inbox URL: " + inboxUrl);
            
            // Navigate directly to the inbox URL
            System.out.println("Navigating directly to inbox URL...");
            driver.get(inboxUrl);
            
            // Wait for URL change and page load
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            System.out.println("Navigation completed. Current URL: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            System.out.println("Exception in clickInboxLink: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to navigate to inbox", e);
        }
    }

    public boolean isInboxTitlePresent(WebDriverWait wait) {
        try {
            System.out.println("Checking for inbox title...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            wait.until(ExpectedConditions.urlContains("inbox"));
            System.out.println("URL contains 'inbox', now checking for title element");
            
            boolean titlePresent = wait.until(ExpectedConditions.presenceOfElementLocated(inboxTitle)).isDisplayed();
            System.out.println("Title element present: " + titlePresent);
            return titlePresent;
        } catch (TimeoutException e) {
            System.out.println("TimeoutException in isInboxTitlePresent: " + e.getMessage());
            System.out.println("Stack trace: ");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected exception in isInboxTitlePresent: " + e.getMessage());
            System.out.println("Stack trace: ");
            e.printStackTrace();
            return false;
        }
    }

    public WebElement getInboxLink() {
        return driver.findElement(inboxLink);
    }
}
