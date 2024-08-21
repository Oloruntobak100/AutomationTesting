package Kayode.TestComponent;

import Kayode.Resources.ExtentReportNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements  ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReportNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
      @Override
    public void onTestStart(ITestResult result) {
        // Code to execute before a test starts
       test = extent.createTest(result.getMethod().getMethodName());
       extentTest.set(test); // It assigns a unique thread ID
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS,"Hi, the test passed successfully :)");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Code to execute when a test fails
        //test.log(Status.FAIL,"Hi, the test failed :)");
         extentTest.get().fail(result.getThrowable());
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Screenshots, Attach to report
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

    }


    @Override
    public void onTestSkipped(ITestResult result) {
        // Code to execute when a test is skipped
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Code to execute for tests that fail but within success percentage
    }

    @Override
    public void onStart(ITestContext context) {
        // Code to execute before any test starts in the context
    }

    @Override
    public void onFinish(ITestContext context) {
        // Code to execute after all tests are finished in the context
        extent.flush();
    }


}
