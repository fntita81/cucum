package stepDefinitions;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportName = "TestReport_" + timeStamp + ".html";
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/" + reportName);

            htmlReporter.config().setDocumentTitle("Automation Test Report");
            htmlReporter.config().setReportName("Login Feature Test");
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Host Name", "Localhost");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", "Automation Tester");
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getInstance().createTest(testName);
        return test;
    }

    
	public static void setTest(ExtentTest extentTest) {
	test=extentTest;
		
	}public static ExtentTest getTest() {
        return test;
    }

}