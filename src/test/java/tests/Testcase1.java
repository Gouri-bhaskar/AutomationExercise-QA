package tests;

import java.time.Duration;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import helper.Utilities;
import modules.ExtentTestManager;
import pages.CartPage;
import pages.HomePage;
import pages.LoginSignUpPage;
import pages.PaymentPage;

@Listeners(modules.TestListener.class)
public class Testcase1 extends BaseTest{
    HomePage homePage = new HomePage();
    LoginSignUpPage loginSignUpPage = new LoginSignUpPage();
    PaymentPage paymentPage = new PaymentPage();
    CartPage cartPage = new CartPage();
    Assertion assertion = new Assertion();
    SoftAssert softAssert = new SoftAssert();

    String userName = Utilities.generateUserName();
    String emailAddress = Utilities.generateEmail();
    String commentForTextArea = Utilities.generateComment();
    String cvcNumber = Utilities.generateZip();
    String cardNumber = Utilities.generateMobile();
    String currentMonth = Utilities.getCurrentMonth();
    String cardExpirationYear = String.valueOf(Integer.parseInt(Utilities.getCurrentYear()) + 1);
    String yearOfBirth = String.valueOf(Integer.parseInt(Utilities.getCurrentYear()) - 20);
    String currentDay = Utilities.getCurrentDay();
    String firstName=  Utilities.generateFirstName();
    String lastName=  Utilities.generateLastName();
    String companyName = userName+ " Company";
    String address = Utilities.generateAddress();
    String address2 = Utilities.generateAddress2();
    String state = Utilities.generateState();
    String city = Utilities.generateCity();
    String zip = Utilities.generateZip();
    String mobileNumber = Utilities.generateMobile();
    String title = "Mr.";
    String country = "India";
    String itemName= "Fancy Green Top";
    String expectedProductLink = HomePage.baseUrl+"product_details/8";

    List<String> textFieldList = Arrays.asList(LoginSignUpPage.passwordFieldLabel, LoginSignUpPage.firstNameFieldLabel, LoginSignUpPage.lastNameFieldLabel,
            LoginSignUpPage.companyFieldLabel, LoginSignUpPage.addressFieldLabel, LoginSignUpPage.address2FieldLabel, LoginSignUpPage.stateFieldLabel,
            LoginSignUpPage.cityFieldLabel, LoginSignUpPage.zipCodeFieldLabel, LoginSignUpPage.mobileNumberFieldLabel);
    List<String> fieldValue = Arrays.asList(Utilities.generatePassword(),  firstName,  lastName, companyName, address, address2, state, city, zip, mobileNumber);
    List<String> expectedAddressValues = Arrays.asList(title+" "+firstName+" "+lastName, companyName, address, address2, city+" "+state+" "+zip,country, mobileNumber);
    List<String> expectedCartTableValueList  = Arrays.asList("Women > Tops", "Rs. 700", "1", "Rs. 700");

    @Test(groups = {"coreTests"})
    void Test001_002_validateUserLandedOnProperBaseUrl() {
        homePage.dismissCookieConsentIfVisible();  // 👈 Add here at the beginning

        ExtentTestManager.getTest().log(Status.INFO, "Start: validate user navigated to url 'http://automationexercise.com'");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl), true, "Base Url '"+ 
                HomePage.baseUrl +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        ExtentTestManager.getTest().log(Status.INFO, "End: validate user navigated to url 'http://automationexercise.com'");
    }


    @Test(groups = {"coreTests"})
    void Test003_validateHomePageIsLoaded() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify that home page is visible successfully");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.homeTabLabel, "style"), "color: orange;",  HomePage.homeTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify that home page is visible successfully");
    }

    @Test(groups = {"coreTests"})
    void Test004_validateAddProductsToCart() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Add products to cart");
        homePage.clickOnAddToCartButtonForSpecificItem(itemName);
        assertion.assertEquals(homePage.getTextFromModal(HomePage.cartModalDivID, HomePage.h4Tag), "Added!",  "'Added!' text is not displayed");
        assertion.assertEquals(homePage.getTextFromModal(HomePage.cartModalDivID, HomePage.pTag), "Your product has been added to cart.",  "'Your product has been added to cart.' text is not displayed");
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify Add products to cart");
    }

    @Test(groups = {"coreTests"})
    void Test005_006_validateViewCart() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on View Cart");
        homePage.clickOnElementWithTagName(HomePage.uTag, HomePage.viewCartButtonLabel,true);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on View Cart");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify  cart page is displayed");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.viewCartUrlextention), true, "Cart Url '"+
                HomePage.baseUrl+CartPage.viewCartUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify  cart page is displayed");
    }

    @Test(groups = {"coreTests"})
    void Test007_validateProceedTOCheckOutWithoutLogin() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on Proceed To Checkout");
        homePage.clickOnButton(CartPage.proceedToCheckoutButtonLabel, false);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on Proceed To Checkout");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify checkout modal is displayed");
        assertion.assertEquals(homePage.getTextFromModal(CartPage.checOutModalDivID, HomePage.h4Tag), "Checkout",  "'Checkout' text is not displayed");
        assertion.assertEquals(homePage.getTextFromModal(CartPage.checOutModalDivID, HomePage.pTag), "Register / Login account to proceed on checkout.",  "'Register / Login account to proceed on checkout.' is not displayed");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify checkout modal is displayed");
    }

    @Test(groups = {"coreTests"})
    void Test008_validateClickOnRegisterLogin() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on Register / Login");
        homePage.clickOnElementWithTagName(HomePage.uTag, CartPage.registerLoginButtonLabel,true);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on Register / Login");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Register / Login page is displayed");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+LoginSignUpPage.loginUrlextention), true,
                HomePage.baseUrl+LoginSignUpPage.loginUrlextention +"' url is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.signUpLoginTabLabel, "style"), "color: orange;",  HomePage.signUpLoginTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Register / Login page is displayed");
    }

    @Test(groups = {"coreTests"})
    void Test009_validateSignUpAndCreateAccount() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify New User Signup!");

        // Step 1: Fill name and email
        loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.nameFieldPlaceholderText, userName);
        loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.emailAddressFieldPlaceholderText, emailAddress);
        homePage.clickOnButton(LoginSignUpPage.signUpButtonLabel, false);

        WebDriver driver = homePage.getDriver();

        // Wait for the form to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1")));

        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl + LoginSignUpPage.signupUrlextention), true,
                "Redirection failed. Current URL: " + homePage.getCurrentURL());

        // Fill the form
        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys(Utilities.generatePassword());

        // Convert numeric month to full month name
        String monthName = Month.of(Integer.parseInt(currentMonth)).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        new Select(driver.findElement(By.id("days"))).selectByVisibleText(currentDay);
        new Select(driver.findElement(By.id("months"))).selectByVisibleText(monthName);
        new Select(driver.findElement(By.id("years"))).selectByVisibleText(yearOfBirth);

        // Checkboxes
        WebElement newsletterCheckbox = driver.findElement(By.id("newsletter"));
        if (!newsletterCheckbox.isSelected()) newsletterCheckbox.click();
        WebElement optinCheckbox = driver.findElement(By.id("optin"));
        if (!optinCheckbox.isSelected()) optinCheckbox.click();

        // Address info
        driver.findElement(By.id("first_name")).sendKeys(firstName);
        driver.findElement(By.id("last_name")).sendKeys(lastName);
        driver.findElement(By.id("company")).sendKeys(companyName);
        driver.findElement(By.id("address1")).sendKeys(address);
        driver.findElement(By.id("address2")).sendKeys(address2);
        new Select(driver.findElement(By.id("country"))).selectByVisibleText(country);
        driver.findElement(By.id("state")).sendKeys(state);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("zipcode")).sendKeys(zip);
        driver.findElement(By.id("mobile_number")).sendKeys(mobileNumber);

        // Submit
        driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

        ExtentTestManager.getTest().log(Status.INFO, "End: ENTER ACCOUNT & ADDRESS INFORMATION");
    }






    @Test(groups = {"coreTests"})
    void Test010_validateAccountIsCreated() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate ACCOUNT is Created");

        System.out.println("🔍 Checking if 'Account Created' URL is loaded...");
        String expectedUrl = HomePage.baseUrl + LoginSignUpPage.accountCreatedUrlextention;
        String actualUrl = homePage.getCurrentURL();
        System.out.println("🔗 Expected: " + expectedUrl);
        System.out.println("🔗 Actual: " + actualUrl);
        assertion.assertEquals(homePage.isProperUrlLoaded(expectedUrl), true,
                "❌ URL mismatch: Expected '" + expectedUrl + "' but got '" + actualUrl + "'");

        System.out.println("🔍 Verifying bold confirmation text...");
        String actualBText = homePage.getTextFromModal(LoginSignUpPage.formSectionID, HomePage.bTag);
        System.out.println("✅ Found <b> tag text: " + actualBText);
        assertion.assertEquals(actualBText, "ACCOUNT CREATED!", "❌ 'ACCOUNT CREATED!' text is missing");

        System.out.println("🔍 Verifying paragraph confirmation message...");
        String actualPText = homePage.getTextFromModal(LoginSignUpPage.formSectionID, HomePage.pTag);
        System.out.println("✅ Found <p> tag text: " + actualPText);
        assertion.assertEquals(actualPText, "Congratulations! Your new account has been successfully created!",
                "❌ 'Congratulations!' message not found");

        ExtentTestManager.getTest().log(Status.INFO, "End: Validate ACCOUNT is Created");

        // Step before next action
        System.out.println("🕒 Pausing for 5 seconds before clicking Continue...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate click on Continue button");
        System.out.println("🖱️ Clicking on 'Continue' button...");
        homePage.clickOnButton(LoginSignUpPage.continueLabel, false);

        // Confirm redirection
        String newUrl = homePage.getCurrentURL();
        System.out.println("🔗 New redirected URL: " + newUrl);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl), true,
                "❌ After clicking 'Continue', base URL not loaded. Got: '" + newUrl + "'");

        ExtentTestManager.getTest().log(Status.INFO, "End: Validate click on Continue button");
    }


    @Test(groups = {"coreTests"})
    void Test011_validateLoggedInUsername() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate Logged in as username' at top");
        assertion.assertEquals(homePage.getLoggedInUserName(), userName,  "Logged user name is not "+userName);
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate Logged in as username' at top");
    }

    @Test(groups = {"coreTests"})
    void Test012_validateClickOnCartButton() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Cart' button");
        homePage.clickOnButton(HomePage.cartTabLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.viewCartUrlextention), true,
                HomePage.baseUrl+CartPage.viewCartUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Cart' button");
    }

    @Test(groups = {"coreTests"})
    void Test013_validateClickOnProceedToCheckButton() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Proceed To Check' button");
        homePage.clickOnButton(CartPage.proceedToCheckoutButtonLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.checkoutUrlextention), true,
                HomePage.baseUrl+CartPage.checkoutUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Proceed To Check' button");
    }

    @Test(groups = {"coreTests"})
    void Test014_validateAddressDetailsAndReviewOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate DELIVERY Address Details");
        List <String> deliveryAddressValues = cartPage.getAllTextFromArea(CartPage.addressDeliveryDivID, HomePage.liTag);
        for (int i=1; i<deliveryAddressValues.size() ;i++) {
            assertion.assertEquals(deliveryAddressValues.get(i), expectedAddressValues.get(i-1),   deliveryAddressValues.get(i)+ " is shown instead of "+ expectedAddressValues.get(i-1));
        }
        ExtentTestManager.getTest().log(Status.INFO, "End:  Validate DELIVERY Address Details");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate BILLING Address Details");
        List <String> billingAddressValues = cartPage.getAllTextFromArea(CartPage.addressInvoiceDivID, HomePage.liTag);
        for (int i=1; i<billingAddressValues.size() ;i++) {
            assertion.assertEquals(billingAddressValues.get(i), expectedAddressValues.get(i-1),   billingAddressValues.get(i)+ " is shown instead of "+ expectedAddressValues.get(i-1));
        }
        ExtentTestManager.getTest().log(Status.INFO, "End:  Validate BILLING Address Details");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate Review Your Order");
        String actualValue;
        List<String> columnList = Arrays.asList(CartPage.descriptionColumnLabel, CartPage.priceColumnLabel, CartPage.quantityColumnLabel, CartPage.totalColumnLabel);
        actualValue = cartPage.getSpecificTableValue(CartPage.cartInfoDivID, CartPage.itemColumnLabel, itemName, "src");
        softAssert.assertEquals(actualValue, expectedProductLink, expectedProductLink+ " is not shown. Instead shown value is "+ actualValue);
        for (int i = 0; i < columnList.size(); i++) {
            actualValue = cartPage.getSpecificTableValue(CartPage.cartInfoDivID, columnList.get(i), itemName, "");
            softAssert.assertEquals(actualValue, expectedCartTableValueList.get(i), expectedCartTableValueList.get(i)+ " is not shown. Instead shown value is "+ actualValue);
        }
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate Review Your Order");
    }

    @Test(groups = {"coreTests"})
    void Test015_validateEnterDescriptionAndPlaceOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Enter description in comment text area and click 'Place Order'");

        WebDriver driver = homePage.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Step 1: Wait for comment <textarea> and enter the comment
            WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("message")));
            commentBox.clear();  // Optional: clear existing text
            commentBox.sendKeys(commentForTextArea);
            System.out.println("✅ Comment entered: " + commentForTextArea);

            // Step 2: Locate "Place Order" button
            WebElement placeOrderBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.btn.btn-default.check_out[href='/payment']")
            ));

            // Step 3: Scroll into view (ensure visible in viewport)
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderBtn);
            Thread.sleep(1000);  // Let scroll settle

            // Step 4: Click the button
            placeOrderBtn.click();
            System.out.println("✅ Clicked on 'Place Order' button");

            // Step 5: Validate URL redirection to /payment
            assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl + PaymentPage.paymentUrlextention), true,
                    "❌ Redirection to payment page failed. Current URL: " + homePage.getCurrentURL());

            ExtentTestManager.getTest().log(Status.INFO, "End: Enter description and click Place Order");

        } catch (Exception e) {
            captureScreenshot();
            ExtentTestManager.getTest().log(Status.FAIL, "❌ Failed to place order: " + e.getMessage());
            throw new RuntimeException("❌ Exception in Test015: " + e.getMessage(), e);
        }
    }


    @Test(groups = {"coreTests"})
    void Test016_017_018_validatePayConfirmAndSuccessOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Enter payment details: Name on Card, Card Number, CVC, Expiration date");
        homePage.setTextInSpecificField(PaymentPage.nameOnCardFieldLabel, userName);
        homePage.setTextInSpecificField(PaymentPage.cardNumberFieldLabel, cardNumber);
        homePage.setTextInSpecificField(PaymentPage.cvcFieldLabel, cvcNumber);
        homePage.setTextInSpecificField(PaymentPage.expirationLabel, currentMonth);
        paymentPage.setTextInSpecificFieldInPaymentPage(PaymentPage.expirationLabel, cardExpirationYear);
        ExtentTestManager.getTest().log(Status.INFO, "End: Enter payment details: Name on Card, Card Number, CVC, Expiration date");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Pay and Confirm Order' button");
        homePage.clickOnButton(PaymentPage.payAndConfirmOrderButtonLabel, false);
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Pay and Confirm Order' button");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate the success message 'Your order has been placed successfully!");
        assertion.assertEquals(homePage.getTextFromModal(PaymentPage.formSectionID, HomePage.pTag), "Congratulations! Your order has been confirmed!",
                "'Congratulations! Your order has been confirmed!' text is not displayed");
        assertion.assertEquals(homePage.getCurrentURL().contains(HomePage.baseUrl+PaymentPage.paymentDoneUrlextention), true,
                HomePage.baseUrl+PaymentPage.paymentDoneUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate the success message 'Your order has been placed successfully!");
    }

    @Test(enabled = false)
    void Test999_forceScreenshotFailureForDemo() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Forced failure test to demo screenshot capture");

        try {
            // Intentionally fail this test
            assertion.assertEquals("expected", "actual", "❌ Forced failure: Strings do not match");

        } catch (AssertionError e) {
            captureScreenshot();
            ExtentTestManager.getTest().log(Status.FAIL, "❌ Expected failure captured for demo: " + e.getMessage());
            throw e;  // rethrow to mark test as failed
        }

        ExtentTestManager.getTest().log(Status.INFO, "End: Forced failure test (this line won't be reached)");
    }



}

