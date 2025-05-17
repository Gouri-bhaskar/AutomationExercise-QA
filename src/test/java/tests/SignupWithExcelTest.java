package tests;

import java.time.Duration;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.Status;

import helper.AccountLogger;
import helper.ExcelReader;
import helper.ExcelWriter;
import modules.ExtentTestManager;
import pages.HomePage;
import pages.LoginSignUpPage;

@Listeners(modules.TestListener.class)
public class SignupWithExcelTest extends BaseTest {

    HomePage homePage = new HomePage();
    LoginSignUpPage loginSignUpPage = new LoginSignUpPage();
    Assertion assertion = new Assertion();
    String validEmail, validPassword, validFirstName;

    @Test(dependsOnGroups = {"coreTests"}, priority = 999)
    void Test020_signupWithExcelData() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Excel-based signup");

        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.automationexercise.com/");
            homePage.dismissCookieConsentIfVisible();

            try {
                WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/logout']")));
                if (logoutBtn.isDisplayed()) {
                    logoutBtn.click();
                    wait.until(ExpectedConditions.urlContains("/login"));
                }
            } catch (Exception ignored) {}

            homePage.clickOnButton(HomePage.signUpLoginTabLabel, false);
            wait.until(ExpectedConditions.urlContains("/login"));

            String excelPath = System.getProperty("user.dir") + "/src/test/resources/test_users_data.xlsx";
            int rowIndex = ExcelReader.getNextUnusedRow(excelPath, "Sheet1");
            if (rowIndex == -1) throw new SkipException("All Excel rows used");

            validFirstName = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "firstName");
            String lastName = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "lastName");
            validEmail = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "email");
            validPassword = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "password");
            String title = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "title");
            String day = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "dobDay");
            String month = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "dobMonth");
            String year = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "dobYear");
            String company = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "company");
            String address1 = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "address1");
            String address2 = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "address2");
            String state = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "state");
            String city = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "city");
            String zip = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "zip");
            String mobile = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "mobile");
            String country = ExcelReader.getCellValue(excelPath, "Sheet1", rowIndex, "country");

            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.nameFieldPlaceholderText, validFirstName);
            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.emailAddressFieldPlaceholderText, validEmail);
            homePage.clickOnButton(LoginSignUpPage.signUpButtonLabel, false);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1")));

            if (title.equalsIgnoreCase("Mr")) {
                driver.findElement(By.id("id_gender1")).click();
            } else {
                driver.findElement(By.id("id_gender2")).click();
            }

            driver.findElement(By.id("password")).sendKeys(validPassword);

            String monthName = Month.of((int) Double.parseDouble(month)).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            new Select(driver.findElement(By.id("days"))).selectByVisibleText(String.valueOf((int) Double.parseDouble(day)));
            new Select(driver.findElement(By.id("months"))).selectByVisibleText(monthName);
            new Select(driver.findElement(By.id("years"))).selectByVisibleText(String.valueOf((int) Double.parseDouble(year)));

            if (!driver.findElement(By.id("newsletter")).isSelected()) driver.findElement(By.id("newsletter")).click();
            if (!driver.findElement(By.id("optin")).isSelected()) driver.findElement(By.id("optin")).click();

            driver.findElement(By.id("first_name")).sendKeys(validFirstName);
            driver.findElement(By.id("last_name")).sendKeys(lastName);
            driver.findElement(By.id("company")).sendKeys(company);
            driver.findElement(By.id("address1")).sendKeys(address1);
            driver.findElement(By.id("address2")).sendKeys(address2);
            new Select(driver.findElement(By.id("country"))).selectByVisibleText(country);
            driver.findElement(By.id("state")).sendKeys(state);
            driver.findElement(By.id("city")).sendKeys(city);
            driver.findElement(By.id("zipcode")).sendKeys(zip);
            driver.findElement(By.id("mobile_number")).sendKeys(mobile);

            driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
            wait.until(ExpectedConditions.urlContains("/account_created"));

            String confirmation = homePage.getTextFromModal(LoginSignUpPage.formSectionID, HomePage.bTag);
            assertion.assertEquals(confirmation, "ACCOUNT CREATED!", "Confirmation text mismatch");

            ExcelWriter.markRowUsed(excelPath, "Sheet1", rowIndex);
            AccountLogger.logAccount(validFirstName, validEmail);
            ExtentTestManager.getTest().log(Status.PASS, "✅ Signup completed for " + validEmail);

            homePage.clickOnButton(LoginSignUpPage.continueLabel, false);
            Thread.sleep(5000);

        } catch (Exception e) {
            captureScreenshot("Test020_signupWithExcelData");
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 1001, dependsOnMethods = {"Test020_signupWithExcelData"})
    void Test022_invalidLoginAfterLogout() {
        ExtentTestManager.getTest().log(Status.INFO, "🔄 Attempting invalid login after logout");

        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            homePage.clickOnButton("Logout", false);
            wait.until(ExpectedConditions.urlContains("/login"));

            String fakeEmail = "wrong" + System.currentTimeMillis() + "@example.com";
            String fakePassword = "invalid123";

            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.loginToYourAccountText, "Email Address", fakeEmail);
            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.loginToYourAccountText, "Password", fakePassword);
            homePage.clickOnButton(LoginSignUpPage.loginButtonLabel, false);

            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p[style='color: red;']")));
            String errorText = error.getText();
            ExtentTestManager.getTest().log(Status.INFO, "❌ Login failed with: " + errorText);

            Thread.sleep(3000);

        } catch (Exception e) {
            captureScreenshot("Test022_invalidLoginAfterLogout");
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 1002, dependsOnMethods = {"Test022_invalidLoginAfterLogout"})
    void Test023_validLoginAfterInvalidAttempt() {
        ExtentTestManager.getTest().log(Status.INFO, "🔁 Logging in with valid credentials after invalid attempt");

        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement emailField = driver.findElement(By.name("email"));
            WebElement passwordField = driver.findElement(By.name("password"));
            emailField.clear();
            passwordField.clear();
            ExtentTestManager.getTest().log(Status.INFO, "✏️ Cleared email and password input fields");

            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.loginToYourAccountText, "Email Address", validEmail);
            loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.loginToYourAccountText, "Password", validPassword);
            homePage.clickOnButton(LoginSignUpPage.loginButtonLabel, false);

            boolean loginSuccess = false;
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/logout']")));
                loginSuccess = true;
            } catch (Exception e) {
                ExtentTestManager.getTest().log(Status.WARNING, "🔎 Logout button not found. Trying fallback...");
            }

            if (!loginSuccess) {
                String actualName = homePage.getLoggedInUserName();
                if (actualName != null && actualName.equalsIgnoreCase(validFirstName)) {
                    ExtentTestManager.getTest().log(Status.INFO, "✅ Fallback match using username: " + actualName);
                    loginSuccess = true;
                } else {
                    captureScreenshot("Test023_validLoginAfterInvalidAttempt");
                    throw new RuntimeException("❌ Login failed. 'logout' link and fallback check both failed.");
                }
            }

            ExtentTestManager.getTest().log(Status.PASS, "✅ Successfully logged back in as: " + validEmail);
            Thread.sleep(8000);

        } catch (Exception e) {
            captureScreenshot("Test023_validLoginAfterInvalidAttempt");
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
