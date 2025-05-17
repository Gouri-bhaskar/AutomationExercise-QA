package tests;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import modules.ExtentManager;
import pages.HomePage;

@Listeners(modules.TestListener.class)
public class BaseTest {

    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static final int defaultImpliciteWait = 15;
    private static final int defaultWait = 60;

    @BeforeSuite(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("disable-notifications");
        chromeOptions.addArguments("test-type");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addExtensions(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Extentions\\AdBlock.crx"));

        driver = new ChromeDriver(chromeOptions);
        driver.get(HomePage.baseUrl);
        closeAllWindowExceptCurrentOne();
        wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWait));
    }

    public WebDriver getDriver() {
        return driver;
    }


    public void goToUrl(String pageUrl) {
        driver.get(pageUrl);
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public boolean isProperUrlLoaded(String expectedUrl) {
        return expectedUrl.contains(getCurrentURL());
    }

    public WebElement getElementFromLocator(String elementLocator) {
        return driver.findElement(By.xpath(elementLocator));
    }

    public List<WebElement> getAllElementsFromLocator(String elementLocator) {
        return driver.findElements(By.xpath(elementLocator));
    }

    public static void closeAllWindowExceptCurrentOne() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    public void clickOnElement(String elementLocator, boolean withJSExecutor) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        if (withJSExecutor) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } else {
            element.click();
        }
    }

    public void moveToSpecificElement(String elementLocator) {
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(elementLocator));
        actions.moveToElement(element).perform();
    }

    public void moveToSpecificElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public String getTextFromElement(String elementLocator) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        return element.getText();
    }

    public List<String> getTextFromAllElements(String elementsLocator) {
        List<WebElement> webElements = driver.findElements(By.xpath(elementsLocator));
        List<String> values = new ArrayList<>();
        for (WebElement element : webElements) {
            values.add(StringUtils.normalizeSpace(element.getAttribute("innerText")));
        }
        return values;
    }

    public String getAttributeFromElement(String elementLocator, String attributeName) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        return element.getAttribute(attributeName);
    }

    public void setTextInField(String fieldLocator, String textValue) {
        WebElement element = driver.findElement(By.xpath(fieldLocator));
        moveToSpecificElement(element);
        element.click();
        element.sendKeys(textValue);
    }

    public boolean isElementDisplayed(String elementLocator) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isDisplayed();
    }

    public boolean isElementSelected(String elementLocator) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isSelected();
    }

    public boolean isElementEnabled(String elementLocator) {
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isEnabled();
    }

    public void waitForElementVisibility(String locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void waitForElementsVisibility(String locator) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
    }

    public void waitForElementPresence(String locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void waitForElementsPresence(String locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
    }

    public static void captureScreenshot() {
        try {
            if (driver == null) {
                System.out.println("⚠️ WebDriver is null. Skipping screenshot.");
                return;
            }

            // Defensive: check session validity
            byte[] dummy = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            if (dummy == null || dummy.length == 0) {
                System.out.println("⚠️ Invalid WebDriver session. Cannot take screenshot.");
                return;
            }

            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File src = screenshot.getScreenshotAs(OutputType.FILE);

            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
            String timeStamp = sdf.format(ts);
            String screenshotName = timeStamp + ".jpg";

            File screenshotDir = new File(ExtentManager.reportFilepath + ExtentManager.fileSeperator + "Screenshots");
            if (!screenshotDir.exists()) screenshotDir.mkdirs();

            FileUtils.copyFile(src, new File(screenshotDir, screenshotName));
            System.out.println("✅ Screenshot captured: " + screenshotName);

        } catch (org.openqa.selenium.NoSuchSessionException e) {
            System.out.println("⚠️ No active session for screenshot: " + e.getMessage());
        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("⚠️ WebDriver error during screenshot: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("⚠️ Screenshot file IO failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error during screenshot capture: " + e.getMessage());
        }
    }



    public static String captureScreenshot(String testName) {
        try {
            if (driver == null) {
                System.out.println("⚠️ WebDriver is null. Skipping screenshot.");
                return null;
            }

            // Defensive: check session validity
            byte[] dummy = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            if (dummy == null || dummy.length == 0) {
                System.out.println("⚠️ Invalid WebDriver session. Cannot take screenshot.");
                return null;
            }

            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File src = screenshot.getScreenshotAs(OutputType.FILE);

            Date d = new Date();
            Timestamp ts = new Timestamp(d.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
            String timeStamp = sdf.format(ts);
            String screenshotName = testName + "_" + timeStamp + ".png";

            File screenshotDir = new File(ExtentManager.reportFilepath + ExtentManager.fileSeperator + "Screenshots");
            if (!screenshotDir.exists()) screenshotDir.mkdirs();

            File dest = new File(screenshotDir, screenshotName);
            FileUtils.copyFile(src, dest);
            System.out.println("✅ Screenshot captured for test: " + screenshotName);
            return dest.getAbsolutePath();

        } catch (org.openqa.selenium.NoSuchSessionException e) {
            System.out.println("⚠️ No active session for screenshot: " + e.getMessage());
            return null;
        } catch (org.openqa.selenium.WebDriverException e) {
            System.out.println("⚠️ WebDriver error during screenshot: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("⚠️ Screenshot file IO failed: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error during screenshot capture: " + e.getMessage());
            return null;
        }
    }



    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
