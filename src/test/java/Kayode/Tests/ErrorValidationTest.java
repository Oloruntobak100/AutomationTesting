package Kayode.Tests;
import Kayode.PageObjects.*;

import Kayode.TestComponent.BaseTest;

import Kayode.TestComponent.RetryFailedTests;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import java.util.List;

public class ErrorValidationTest extends BaseTest {

    //@Test(groups = {"ErrorHandling"},retryAnalyzer = RetryFailedTests.class) for retry
    @Test(groups = {"ErrorHandling"})
    public void loginErrorValidation() throws InterruptedException, IOException {
        landingPage.loginApplication("kayode@gmail.com", "Kayod88");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

    }

    @Test (groups = {"ErrorHandling"})
    public void productErrorValidation() throws InterruptedException, IOException {
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("toba@gmail.com", "Kayode888");
        List<WebElement>products =  productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage =  productCatalogue.goToCartPage();
        Boolean match =  cartPage.VerifyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);

    }

    }

