package tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
public class ProductListingTest extends BaseTest {

    HomePage homePage = new HomePage();
    Assertion assertion = new Assertion();

    @Test(priority = 1003, dependsOnMethods = {"tests.SignupWithExcelTest.Test023_validLoginAfterInvalidAttempt"})
    void Test030_verifyProductListingAndSearchFeatures() {
        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        ExtentTestManager.getTest().log(Status.INFO, "🔎 Clicking on 'Products' tab...");
        homePage.clickOnButton("Products", false);
        wait.until(ExpectedConditions.urlContains("/products"));
        ExtentTestManager.getTest().log(Status.INFO, "✅ Reached Products page");

        js.executeScript("document.querySelectorAll('iframe, #aswift_1_host').forEach(e => e.style.display='none');");

        // Expand category toggles
        List<String> categoryIds = Arrays.asList("Women", "Men", "Kids");
        for (String id : categoryIds) {
            try {
                WebElement toggle = driver.findElement(By.cssSelector("a[href='#" + id + "']"));
                toggle.click();
                Thread.sleep(500);
                ExtentTestManager.getTest().log(Status.INFO, "📂 Toggled category: " + id);
            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Toggle failed for category: " + id);
            }
        }

        // Brand filter
        try {
            WebElement poloFilter = driver.findElement(By.xpath("//a[contains(@href,'/brand_products/Polo')]"));
            poloFilter.click();
            wait.until(ExpectedConditions.urlContains("/brand_products/Polo"));
            ExtentTestManager.getTest().log(Status.INFO, "✅ Brand filter applied: Polo");
        } catch (Exception e) {
            ExtentTestManager.getTest().log(Status.WARNING, "⚠️ Brand filter click failed");
        }

        // Search and verify Blue Top
        try {
            WebElement searchBox = driver.findElement(By.id("search_product"));
            searchBox.clear();
            searchBox.sendKeys("Blue Top", Keys.ENTER);
            Thread.sleep(2000);
            WebElement result = driver.findElement(By.xpath("//p[text()='Blue Top']"));
            assertion.assertEquals(result.isDisplayed(), true);
            ExtentTestManager.getTest().log(Status.INFO, "✅ Search result: Blue Top found");
        } catch (Exception e) {
            captureScreenshot("Test030_searchBlueTop");
            throw new RuntimeException("❌ Product search failed: " + e.getMessage(), e);
        }

        // Add Blue Top to cart
        try {
            js.executeScript("document.querySelectorAll('iframe, #aswift_1_host').forEach(e => e.style.display='none');");
            WebElement addToCartBtn = driver.findElement(By.xpath("//a[@data-product-id='1']"));
            js.executeScript("arguments[0].click();", addToCartBtn);
            Thread.sleep(2000);
            WebElement modalText = driver.findElement(By.xpath("//div[@class='modal-body']//p[contains(text(),'Your product has been added')]"));
            assertion.assertEquals(modalText.isDisplayed(), true);
            driver.findElement(By.cssSelector("button.close-modal")).click();
            ExtentTestManager.getTest().log(Status.INFO, "🛒 Added Blue Top to cart and closed modal");
        } catch (Exception e) {
            captureScreenshot("Test030_addToCart");
            throw new RuntimeException("❌ Add to cart failed: " + e.getMessage(), e);
        }

        // Search and open Saree detail
        try {
            WebElement searchBox = driver.findElement(By.id("search_product"));
            searchBox.clear();
            searchBox.sendKeys("Saree", Keys.ENTER);
            Thread.sleep(2000);
            js.executeScript("document.querySelectorAll('iframe, #aswift_1_host').forEach(e => e.style.display='none');");
            WebElement viewProduct = driver.findElement(By.xpath("//a[@href='/product_details/41']"));
            js.executeScript("arguments[0].click();", viewProduct);
            wait.until(ExpectedConditions.urlContains("/product_details/41"));
            ExtentTestManager.getTest().log(Status.INFO, "👗 Opened Saree product detail page");
        } catch (Exception e) {
            captureScreenshot("Test030_searchSaree");
            throw new RuntimeException("❌ Failed to search and view Saree: " + e.getMessage(), e);
        }

        // Wait for review section to load
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("review")));
            ExtentTestManager.getTest().log(Status.INFO, "📝 Review section loaded");
        } catch (Exception e) {
            captureScreenshot("Test030_reviewSectionTimeout");
            throw new RuntimeException("❌ Review section did not load: " + e.getMessage(), e);
        }

        // Submit review
        try {
            driver.findElement(By.id("name")).sendKeys("Test User");
            driver.findElement(By.id("email")).sendKeys("test@example.com");
            driver.findElement(By.id("review")).sendKeys("Elegant and stylish!");
            driver.findElement(By.id("button-review")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='review-section']//span[contains(text(),'Thank you')]")));
            WebElement successAlert = driver.findElement(By.xpath("//div[@id='review-section']//span[contains(text(),'Thank you')]"));
            assertion.assertEquals(successAlert.isDisplayed(), true);
            ExtentTestManager.getTest().log(Status.INFO, "📝 Review submitted for Saree");
        } catch (Exception e) {
            captureScreenshot("Test030_submitReview");
            throw new RuntimeException("❌ Review submission failed: " + e.getMessage(), e);
        }

        // Add Saree to cart and open cart
        try {
            js.executeScript("document.querySelectorAll('iframe, #aswift_1_host').forEach(e => e.style.display='none');");
            WebElement addToCartBtn = driver.findElement(By.cssSelector("button.cart"));
            js.executeScript("arguments[0].click();", addToCartBtn);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-body']//a[contains(@href,'/view_cart')]")));
            WebElement viewCartLink = driver.findElement(By.xpath("//div[@class='modal-body']//a[contains(@href,'/view_cart')]"));
            js.executeScript("arguments[0].click();", viewCartLink);
            wait.until(ExpectedConditions.urlContains("/view_cart"));
            ExtentTestManager.getTest().log(Status.INFO, "🛒 Navigated to cart page");
        } catch (Exception e) {
            captureScreenshot("Test030_addSareeAndViewCart");
            throw new RuntimeException("❌ Failed to add Saree or view cart: " + e.getMessage(), e);
        }

        // Final cart check
        try {
            WebElement blueTop = driver.findElement(By.id("product-1"));
            WebElement saree = driver.findElement(By.id("product-41"));
            assertion.assertEquals(blueTop.isDisplayed(), true);
            assertion.assertEquals(saree.isDisplayed(), true);
            ExtentTestManager.getTest().log(Status.INFO, "✅ Both Blue Top and Saree are present in cart");
        } catch (Exception e) {
            captureScreenshot("Test030_cartFinalVerification");
            throw new RuntimeException("❌ Cart verification failed: One or both items missing", e);
        }

        ExtentTestManager.getTest().log(Status.PASS, "✅ Test030_verifyProductListingAndSearchFeatures completed successfully");
    }
}
