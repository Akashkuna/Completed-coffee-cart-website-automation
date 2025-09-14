package com.coffeecart.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.coffeecart.managers.DriverManager;
import com.coffeecart.utils.TestUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.nio.file.Paths;

public class TestListener implements ITestListener, ISuiteListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        String reportPath = Paths.get("output", "test-output", "ExtentReport.html").toString();
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onFinish(ISuite suite) {
        if (extent != null) extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        String path = TestUtil.captureScreenshot(driver, result.getMethod().getMethodName());
        test.get().fail(result.getThrowable());
        test.get().addScreenCaptureFromPath(path);
    }

    @Override public void onTestSkipped(ITestResult result) { test.get().skip("Skipped"); }
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
