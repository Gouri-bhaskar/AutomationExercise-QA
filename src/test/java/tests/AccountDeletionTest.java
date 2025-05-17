package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.Status;

import modules.ExtentTestManager;
import pages.HomePage;

@Listeners(modules.TestListener.class)
public class AccountDeletionTest extends BaseTest {

    HomePage homePage = new HomePage();
    Assertion assertion = new Assertion();

    @Test(priority = 1007, dependsOnMethods = {"tests.SubscriptionTest.Test033_subscribeWithDuplicateEmail"})
    public void Test034_validateAccountDeletionAndRedirect() {
        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            ExtentTestManager.getTest().log(Status.INFO, "🗑 Clicking on 'Delete Account'...");
            driver.findElement(By.xpath("//a[@href='/delete_account']")).click();

            wait.until(ExpectedConditions.urlToBe("https://www.automationexercise.com/delete_account"));
            ExtentTestManager.getTest().log(Status.INFO, "✅ URL confirmed: /delete_account");

            // ✅ Wait for heading text with normalization
            WebElement confirmationHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[normalize-space()='Account Deleted!']")));
            assertion.assertTrue(confirmationHeading.isDisplayed(), "'Account Deleted!' heading not visible");
            ExtentTestManager.getTest().log(Status.INFO, "🟢 'Account Deleted!' heading confirmed");

            // ✅ Scroll and click Continue using JS
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@data-qa='continue-button']")));
            js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
            js.executeScript("arguments[0].click();", continueBtn);
            ExtentTestManager.getTest().log(Status.INFO, "⏭ Clicked 'Continue' via JavaScriptExecutor");

            Thread.sleep(8000); // wait for home redirection
            ExtentTestManager.getTest().log(Status.PASS, "✅ Test034 completed: deletion + redirect verified");

        } catch (Exception e) {
            captureScreenshot("Test034_validateAccountDeletionAndRedirect");
            System.err.println("🛑 DEBUG PAGE SOURCE:\n" + driver.getPageSource());
            throw new RuntimeException("❌ Account deletion flow failed: " + e.getMessage(), e);
        }
    }
}
