import io.qameta.allure.*;;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Random;

public class GetStartedPage extends PageObject {
    @FindBy(className ="wg-header__free-trial-button")
    protected WebElement getStartedButton;

    @FindBy(xpath ="//*[@id=\"modal-pro\"]/form/label[2]/button")
    private WebElement createAccountButton;

    @FindBy(xpath = "//*[@id=\"modal-pro\"]/form/label[1]/input")
    private WebElement email;

    public GetStartedPage(WebDriver driver){
        super(driver);
    }

    @Step
    public void enterEmail(String email){
        this.email.clear();
        this.email.sendKeys(email);
    }

    public boolean isInitialized(){
        return email.isDisplayed() && getStartedButton.isDisplayed();
    }

    public boolean isCreateAccountInteractable() {
        return createAccountButton.isDisplayed() && createAccountButton.isEnabled();
    }

    @Step
    public ResendPage submit(){
        createAccountButton.click();
        return new ResendPage(driver);
    }

    public static String getRandomString(Random random, int randomStringLength){
        //97 - letter 'a' in UniCode
        //122 -letter 'z' in UniCode
        return random.ints(97,122)
                .mapToObj(i -> (char) i)
                .limit(randomStringLength)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
