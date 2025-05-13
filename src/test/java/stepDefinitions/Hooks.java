package stepDefinitions;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import propconfig.ConfigReader;
import stepDefinitions.ExtentReportManager;

public class Hooks {

    public static ExtentReports extent;
    public static ExtentTest test;
    public static ConfigReader config;

    @Before(order = 0)
    public void setUpConfig() {
        // Load config file
        String filePath = System.getProperty("user.dir") + "/resources/config.properties";
        config = new ConfigReader(filePath);
    }

    @Before(order = 1)
    public void setUpReport(Scenario scenario) {
        if (extent == null) {
            // Timestamped report name
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/target/ExtentReport_" + timeStamp + ".html";

            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
            htmlReporter.config().setDocumentTitle("Automation Report");
            htmlReporter.config().setReportName("Login Feature Results");
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Your Name");
        }

        test = extent.createTest(scenario.getName());
        ExtentReportManager.setTest(test); // support access in step defs
    }

    @After
    public void tearDownReport(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentReportManager.getTest().fail("Scenario Failed: " + scenario.getName());
        } else {
            ExtentReportManager.getTest().pass("Scenario Passed: " + scenario.getName());
        }

        extent.flush();
    }
    
}
