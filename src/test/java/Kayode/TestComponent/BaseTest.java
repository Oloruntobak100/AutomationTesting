package Kayode.TestComponent;

import Kayode.PageObjects.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver() throws IOException {

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream( System.getProperty("user.dir")+"//src//main//java//Kayode//Resources//GlobalData.properties");
        properties.load(fileInputStream);

        String browserName =  System.getProperty("browser")!=null ?  System.getProperty("browser") :properties.getProperty("browser");
       // properties.getProperty("browser");

        if (browserName.contains("chrome")){
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
             driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));
        } else if (browserName.equalsIgnoreCase("firefox")){
            // firefox driver path
            WebDriverManager.firefoxdriver().setup();
              driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")){
            //edge driver path
            WebDriverManager.edgedriver().setup();
             driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

        public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
           //Read json to String
           String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

           // Convert JsonContent to HashMap,Use Dependency => Jackson dataBinder

           ObjectMapper objectMapper = new ObjectMapper();
           List<HashMap<String, String>> data = objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
           });
           return data;

   }

       public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
       File source =  screenshot.getScreenshotAs(OutputType.FILE);
       File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
         driver = initializeDriver();
         landingPage = new LandingPage(driver);
         landingPage.goTo();
         return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
    

}
