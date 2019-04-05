import com.sun.istack.internal.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class ResendPage extends PageObject {

    @FindBy(xpath = "/html/body/div[1]/main/div/div/div[2]/div/div[1]/p[3]/button")
    protected WebElement resendButton;

    @FindBy(id = "twitter")
    private WebElement twitterButton;

    @FindBy(xpath= "/html/body/div[1]/div/div[3]/div/div[1]/div/ul/li[1]/a") // wg-footer__social-link   /html/body/div[1]/div/div[3]/div/div[1]/div/ul/li[1]/a
    private WebElement twitter_link;

    @FindBy(className = "survey-form") ///html/body/div[1]/main/div/div/div[2]/div/div[2]/div/form
    private WebElement surveyForm;

    @FindBy(xpath = "/html/body/div[1]/main/div/div/div[2]/div/div[2]/div/form/button")
    private WebElement submitSurveyButton;

    //get list of survey buttons
    @NotNull
    private List<WebElement> allRadioButtons = surveyForm.findElements(By.xpath("//button[@class = 'switch__button']"));

    ResendPage(WebDriver driver) {
        super(driver);
    }

    @Step
    String getTwitterLink(){
        return twitter_link.getAttribute("href");
    }

    //check if survey form and resend button is ready
    boolean isInitialized() { return resendButton.isDisplayed() && surveyForm.isDisplayed();
    }

    boolean isAllSurveyButtonsReady(){
        for (WebElement e : allRadioButtons) {
            if(!e.isDisplayed() || !e.isEnabled()) {
                return false;
            }
        }

        return submitSurveyButton.isDisplayed();
    }
    /*
     *   Method that fill Q&A section of resend page.
     *   For the first question we can only choose one option (buttons with indexes 0 and 1).
     *   Method get random number in range between 0 and 1 and click it.
     *   For the second question we can choose 5 options (buttons with indexes 2 - 7 inclusive).
     *   Method do the same thing that it did for the first question
     *   For the third question we choose between 3 button like in the previous steps, but if we select
     *   the last one, we need to enter text to submit the form
     *
     */
    @Step
    public void randomSurvey(List<WebElement> elements) {
        if(isAllSurveyButtonsReady()){
            Random r1 = new Random();
            int i1 = r1.ints(0, 2).findFirst().getAsInt();
            elements.get(i1).click();
            Random r2 = new Random();
            int i2 = r2.ints(2, 7).findFirst().getAsInt();
            elements.get(i2).click();
            Random r3 = new Random();

            int i3 = r3.ints(7, 10).findFirst().getAsInt();
            if(i3 != 9) {
                elements.get(i3).click();
            }
            else {
                elements.get(i3).click();
                WebElement otherComments = surveyForm.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/div/div[2]/div/form/div[3]/label[3]/button/span/input"));
                otherComments.sendKeys(GetStartedPage.getRandomString(r1, 10));
            }

            //wait until submit button can be clicked
            (new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d){
                    return d.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/div/div[2]/div/form/button")).isEnabled();
                }
            });
            submitSurveyButton.click();
        }

    }

    boolean resendButtonIsClicked(){
        //if button was is clicked, it become hidden
        return !resendButton.isDisplayed();
    }
/*
    public String getTwPath() {
        return twitter_path.getAttribute("xlink:href");
    }
*/
    boolean surveyFormIsSubmitted() {
        //if form was submitted, it become hidden
        return !surveyForm.isDisplayed();
    }

    List<WebElement> getAllRadioButtons(){
        return allRadioButtons;
    }
}

