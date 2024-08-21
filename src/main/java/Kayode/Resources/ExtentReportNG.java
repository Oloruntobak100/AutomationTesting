package Kayode.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
    static ExtentReports extent;
    public static ExtentReports getReportObject(){

        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Kayode Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Kayode Daniel");

        return extent;

    }

}
