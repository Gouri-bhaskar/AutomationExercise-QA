package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.Status;

import helper.Utilities;
import modules.ExtentTestManager;
import pages.HomePage;

@Listeners(modules.TestListener.class)
public class SubscriptionTest extends BaseTest {

    HomePage homePage = new HomePage();
    Assertion assertion = new Assertion();

    @Test(priority = 1004, dependsOnMethods = {"tests.ProductListingTest.Test030_verifyProductListingAndSearchFeatures"})
    public void Test031_subscribeWithValidEmail() {
        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String email = Utilities.generateEmail();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Subscribing with valid email: " + email);

        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("susbscribe_email")));
            driver.findElement(By.id("susbscribe_email")).sendKeys(email);
            driver.findElement(By.id("subscribe")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'You have been successfully subscribed!')]")));
            ExtentTestManager.getTest().log(Status.PASS, "✅ Subscription success alert verified");
        } catch (Exception e) {
            captureScreenshot("Test031_subscribeWithValidEmail");
            throw new RuntimeException("❌ Subscription with valid email failed: " + e.getMessage(), e);
        }
    }

    @Test(priority = 1005)
    public void Test032_subscribeWithInvalidEmail() {
        WebDriver driver = homePage.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        ExtentTestManager.getTest().log(Status.INFO, "🧪 Subscribing with invalid email: 'invalidemail'");

        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            driver.findElement(By.id("susbscribe_email")).clear();
            driver.findElement(By.id("susbscribe_email")).sendKeys("invalidemail");
            driver.findElement(By.id("subscribe")).click();
            Thread.sleep(2000);

            boolean isAlertPresent = driver.getPageSource().contains("You have been successfully subscribed");
            assertion.assertEquals(isAlertPresent, true); // Site wrongly accepts invalid email
            ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Success alert shown even for invalid email — site lacks validation.");
        } catch (Exception e) {
            captureScreenshot("Test032_subscribeWithInvalidEmail");
            throw new RuntimeException("❌ Unexpected failure during invalid email test: " + e.getMessage(), e);
        }
    }

    @Test(priority = 1006)
    public void Test033_subscribeWithDuplicateEmail() {
        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String email = "duplicate@example.com"; // known test value
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Subscribing with duplicate email: " + email);

        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            driver.findElement(By.id("susbscribe_email")).clear();
            driver.findElement(By.id("susbscribe_email")).sendKeys(email);
            driver.findElement(By.id("subscribe")).click();
            Thread.sleep(2000);

            boolean isSuccess = driver.getPageSource().contains("You have been successfully subscribed");
            assertion.assertEquals(isSuccess, true); // Site wrongly accepts duplicates
            ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Success alert shown even for duplicate email — site does not block it.");
        } catch (Exception e) {
            captureScreenshot("Test033_subscribeWithDuplicateEmail");
            throw new RuntimeException("❌ Unexpected failure during duplicate email test: " + e.getMessage(), e);
        }
    }
}
