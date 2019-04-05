
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * FunctionalTest handles setup and teardown of WebDriver.
 */
public class FunctionalTest {
    protected static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.wrike.com");
    }

   @After
    public void cleanUp(){
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void tearDown(){
        driver.close();
    }
}
