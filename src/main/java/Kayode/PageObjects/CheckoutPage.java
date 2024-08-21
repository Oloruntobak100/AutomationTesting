package Kayode.PageObjects;

import Kayode.Abstract.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;
    public CheckoutPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".action__submit")
    WebElement submit;

    @FindBy(css = "[placeholder='Select Country']")
    WebElement country;

    @FindBy(css = "button[type='button']")
    WebElement selectCountry;

   By results = By.cssSelector(".ta-result");

    public void selectCountry(String countryName){
        Actions a = new Actions(driver);
        a.sendKeys(country,countryName).build().perform();
       // waitForElementToAppear(By.cssSelector(".ta-result"));
        selectCountry.click();
    }
    public ConfirmationPage submitOrder(){
        submit.click();
        return new ConfirmationPage(driver);
    }
}
