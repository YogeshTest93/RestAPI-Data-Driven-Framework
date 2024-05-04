package Report;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReport implements IReporter {
    private ExtentReports extent;
    private ExtentSparkReporter extentSparkReporter;
    private ExtentTest extentTest;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extentSparkReporter = new ExtentSparkReporter(outputDirectory + File.separator + "WeatherAPI-ExtentReport.html");
        extentSparkReporter.config().setDocumentTitle("API Automation Report");
        extentSparkReporter.config().setReportName("Extent Report");
        extentSparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(extentSparkReporter);

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                extentTest = extent.createTest(result.getMethod().getMethodName());

                if (result.getTestContext().getName() != null)
                    extentTest.assignCategory(result.getTestContext().getName());

                extentTest.log(status, "Test " + status.toString().toLowerCase() + "ed");

                extent.flush();
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
