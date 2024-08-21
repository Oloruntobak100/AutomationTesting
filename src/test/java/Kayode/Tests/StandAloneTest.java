package Kayode.Tests;

import Kayode.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) {
        String productName = "ZARA COAT 3";

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");

        LandingPage landingPage = new LandingPage(driver);

        driver.findElement(By.id("userEmail")).sendKeys("toba@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Kayode888");
        driver.findElement(By.id("login")).click();
        // Select all the items listed -  To achieve this, select a common class name to all items
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-lg-4")));
        List<WebElement> products =  driver.findElements(By.cssSelector(".col-lg-4"));


        /*
        Iterate over each of the item, Stream will help iterate over each item
        Filter each iterator, and check if the text equals to name of product
        The .equals is the if statement that states that if the getText = to the text, find the first occurrence or else return null

        Prod variable selects a single item box with the text ZARA COAT 3, find element ADD to card in the box then click it
         */
        WebElement prod =  products.stream().filter(product->
              product.findElement(By.tagName("b")).getText().equals(productName)).findFirst().orElse(null);
               prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

               /*
                Now, we want to wait until the Successful Add to cart button is displayed before we get the text, here we will use explicit wait

               */
        // Wait until the successful button is displayed



        // Wait until the animation overlay disappears
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        //Click the Add to Cart button
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        /*
        Upon adding our items to cart, we want to verify that the selected items in the cart matches with the item name
        Now, at the top of the code, declare a String Variable, and pass the mame of the selected item into it

        - Firstly, load the items in the Cart into a List
         */
       List<WebElement> itemsInCart =  driver.findElements(By.cssSelector(".cartSection h3"));

        Boolean match =  itemsInCart.stream().anyMatch(cartItem-> cartItem.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        //Actions a = new Actions(driver);
        //a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"Nigeria").build().perform();

        driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("Nigeria");
        driver.findElement(By.cssSelector(".ta-item.list-group-item.ng-star-inserted")).click();
        driver.findElement(By.cssSelector(".btnn.action__submit.ng-star-inserted")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        System.out.println(driver.findElement(By.cssSelector("tr[class='ng-star-inserted']")).getText());
        driver.quit();
    }
}
