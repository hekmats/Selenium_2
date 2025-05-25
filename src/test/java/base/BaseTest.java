package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;



public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private Path tempUserDataDir;
    private static final int DEFAULT_TIMEOUT = 10; // seconds

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory for Chrome user data
        tempUserDataDir = Files.createTempDirectory("chrome-user-data");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }



    @AfterEach
    public void tearDown() throws IOException {
        if (driver != null) {
            driver.quit();
        }
        // Delete the temp user data directory
        if (tempUserDataDir != null) {
            Files.walk(tempUserDataDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> path.toFile().delete());
        }
    }
}
