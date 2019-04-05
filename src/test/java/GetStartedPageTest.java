import io.qameta.allure.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Feature("Get started and resend email pages tests")
public class GetStartedPageTest extends FunctionalTest {

    @Test
    @Description("Check with assertion that page is initialized and enter the email. " +
            "after that check with assertion that page is initialized and contains correct twitter icon with correct link.")
    public void testGetStartedAndResend(){
        GetStartedPage getStartedPage = new GetStartedPage(driver);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", getStartedPage.getStartedButton);
        Random rand = new Random();
        int email_length = rand.nextInt(((12 - 6) + 1) + 6);
        assertTrue(getStartedPage.isCreateAccountInteractable());
        getStartedPage.enterEmail(GetStartedPage.getRandomString(rand, email_length) + "+wpt@wriketask.qaa");
        ResendPage resendPage = getStartedPage.submit();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return d.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/div/div[1]/p[3]/button")).isDisplayed();
            }
        });
        assertTrue(resendPage.isInitialized());

        resendPage.resendButton.click();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d){
                return !d.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/div/div[1]/p[3]/button")).isDisplayed();
            }
        });
        assertTrue(resendPage.resendButtonIsClicked());
        //assertEquals("https://twitter.com/wrike", resendPage.getTwitterLink());
        assertEquals(tw_path, resendPage.getTwPath());
        //resendPage.randomSurvey(resendPage.getAllRadioButtons());
        //assertTrue(resendPage.surveyFormIsSubmitted());
    }

    //path represents twitter icon
    private String tw_path = "/content/themes/wrike/dist/img/sprite/vector/footer-icons.symbol.svg?v2#twitter";
    private String tw_path1 = "M14.2 3.2v.42A9.23 9.23 0 0 1-.01 11.39a6.66 6.66 0 0 0 .78 0 6.5 6.5 0 0 0 4-1.38 3.25" +
            " 3.25 0 0 1-3-2.25 4.21 4.21 0 0 0 .61 0 3.42 3.42 0 0 0 .85-.11 3.24 3.24 0 0 1-2.6-3.18 3.27 3.27 0 0 0 1.47.41" +
            " 3.25 3.25 0 0 1-1-4.34 9.22 9.22 0 0 0 6.69 3.39 3.66 3.66 0 0 1-.08-.74A3.25 3.25 0 0 1 13.32.97a6.39 6.39 0 0 0" +
            " 2.06-.78 3.23 3.23 0 0 1-1.43 1.79 6.5 6.5 0 0 0 1.87-.5A7 7 0 0 1 14.2 3.2z";

}
