package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomDataTest {

    @Test
    public void testRandomTitleInEmailField() {
        // Setup WebDriver manually
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {
            // Step 1: Generate random title
            Faker faker = new Faker();
            String randomTitle = faker.book().title();
            System.out.println("Generated random title: " + randomTitle);

            // Step 2: Navigate to ClickUp login
            driver.get("https://app.clickup.com/login");

            // Step 3: Find email input field
            WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
            emailField.sendKeys(randomTitle);

            // Step 4: Assert value matches
            String fieldValue = emailField.getAttribute("value");
            assertEquals(randomTitle, fieldValue, "The email field should contain the random title.");

            System.out.println("✅ Test passed: Email field contains random title.");

        } finally {
            // Teardown
            driver.quit();
        }
    }
}
