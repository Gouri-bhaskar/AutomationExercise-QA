package modules;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import tests.BaseTest;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " started ***");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " ending ***");
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("*** Running test method " + result.getMethod().getMethodName() + "...");
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());

            // Optional: Attach screenshot
            String screenshotPath = BaseTest.captureScreenshot(result.getMethod().getMethodName());
            try {
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                System.out.println("Screenshot attachment failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within success percentage % " + result.getMethod().getMethodName());
    }
}
