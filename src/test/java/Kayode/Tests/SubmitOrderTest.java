package Kayode.Tests;
import Kayode.PageObjects.*;
import Kayode.TestComponent.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
    String  productName = "ZARA COAT 3";

    @Test(dataProvider = "getData", groups = {"PurchaseOrder"})
    public void submitOrder(HashMap<String,String> input ) throws IOException, InterruptedException {

        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        List<WebElement>products =  productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("product"));
        CartPage cartPage =  productCatalogue.goToCartPage();

        Boolean match =  cartPage.VerifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckOut();
        checkoutPage.selectCountry("Nigeria");
        ConfirmationPage confirmationPage =  checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    /*
     To verify ZARA COAT 3 is displaying in orders page
    The OrderHistoryTest method is dependent on the submitOrder Method, so i want to run the OrderHistoryTest only after the Submit Order. OrderHistoryTest is dependent on Submit Order method
     */

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest(){
        ProductCatalogue productCatalogue = landingPage.loginApplication("toba@gmail.com", "Kayode888");
        OrderPage orderPage = productCatalogue.goToOrderPage();
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));

    }


    @DataProvider
    public Object[][] getData() throws IOException {
       List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//Kayode//Data//purchaseOrder.json");
      return new Object[][] {{data.get(0)}, {data.get(1)}};

    }
}





