package tests;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.Alert;
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
public class ContactUsTest extends BaseTest {

    HomePage homePage = new HomePage();
    Assertion assertion = new Assertion();

    @Test(priority = 1008, dependsOnMethods = {"tests.AccountDeletionTest.Test034_validateAccountDeletionAndRedirect"})
    public void Test035_validateContactUsFormSubmission() {
        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            ExtentTestManager.getTest().log(Status.INFO, "📨 Navigating to Contact Us page...");
            WebElement contactLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/contact_us']")));
            contactLink.click();

            wait.until(ExpectedConditions.urlContains("/contact_us"));
            ExtentTestManager.getTest().log(Status.INFO, "✅ Contact Us page loaded");

            driver.findElement(By.name("name")).sendKeys("Anandhu Bhaskar");
            driver.findElement(By.name("email")).sendKeys("anandhu@example.com");
            driver.findElement(By.name("subject")).sendKeys("Testing Contact Form");
            driver.findElement(By.name("message")).sendKeys("This is an automated message for testing.");

            ExtentTestManager.getTest().log(Status.INFO, "📝 Form fields filled successfully");

            // Upload file
            String uploadPath = new File("src/test/uploads/image001.jpg").getAbsolutePath();
            WebElement fileInput = driver.findElement(By.name("upload_file"));
            fileInput.sendKeys(uploadPath);
            ExtentTestManager.getTest().log(Status.INFO, "📎 File uploaded: image001.jpg");

            // Submit the form
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@data-qa='submit-button']")));
            js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
            js.executeScript("arguments[0].click();", submitButton);
            ExtentTestManager.getTest().log(Status.INFO, "📤 Form submitted");

            // Accept alert
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            ExtentTestManager.getTest().log(Status.INFO, "🔔 Alert accepted");

            // Confirm success message
            WebElement successText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Success! Your details have been submitted successfully.')]")));
            assertion.assertTrue(successText.isDisplayed());
            ExtentTestManager.getTest().log(Status.PASS, "✅ Success message verified");

        } catch (Exception e) {
            captureScreenshot("Test035_validateContactUsFormSubmission");
            throw new RuntimeException("❌ Contact Us form submission failed: " + e.getMessage(), e);
        }
    }
}
