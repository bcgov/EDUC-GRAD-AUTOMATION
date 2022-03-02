package ca.bc.gov.educ;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Not in use
 */
class ChromeTest {

    WebDriver driver;
    public static String remote_url_chrome = "http://localhost:4444";
    public static String remote_url_firefox = "http://localhost:4446/wd/hub";

    /**@BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }**/

    //WebDriverManager wdm = WebDriverManager.chromedriver().browserInDocker();

    @BeforeEach
    void setupTest() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(
                new URL(remote_url_chrome),
                options
        );
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void test() {
        driver.get("https://google.com");

        Assertions.assertEquals("Google", driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));

        searchBox.sendKeys("Selenium");
        searchButton.click();

        searchBox = driver.findElement(By.name("q"));
        Assertions.assertEquals("Selenium", searchBox.getAttribute("value"));


    }

}