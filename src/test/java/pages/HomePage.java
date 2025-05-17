package pages;

import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import modules.ExtentTestManager;
import tests.BaseTest;

public class HomePage extends BaseTest {

    //Start: Tab Label name
    public final static String homeTabLabel = "Home";
    public final static String cartTabLabel = "Cart";
    public final static String signUpLoginTabLabel = "Signup / Login";
    public final static String loggedInAsTabLabel = "Logged in as";
    public final static String productsTabLabel = "Products";
    public final static String logOutTabLabel = "Logout";
    public final static String deleteAccountLabel = "Delete Account";
    //End: Tab Label name

    //Start: Url
    public final static String baseUrl = "https://www.automationexercise.com/";
    //End: Url

    //Start: tag Name
    public final static String uTag = "u";
    public final static String pTag = "p";
    public final static String h4Tag = "h4";
    public final static String bTag = "b";
    public final static String liTag = "li";
    //End: tag Name

    //Start: DivID
    public final static String cartModalDivID = "cartModal";
    //End: DivID

    //Start: Class Name
    public final static String productOverlayClassName = "product-overlay";
    //End:  Class Name

    //Start: Button Name
    public final static String addToCartButtonLabel = "Add to cart";
    public final static String viewCartButtonLabel = "View Cart";
    public final static String viewProductButtonLabel = "View Product";
    //End:   Button Name

    public String getTabAttribute(String tabName, String attributeName){
        String tabLocator = "//li//a[normalize-space(text())='"+ tabName +"']";
        String value="";
        try{
            waitForElementPresence(tabLocator);
            value = getAttributeFromElement(tabLocator, attributeName);
            ExtentTestManager.getTest().log(Status.PASS, tabName+"'s "+ attributeName + " attribute value is "+value);
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
        return value;
    }

    public void dismissCookieConsentIfVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement consentBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".fc-button.fc-cta-consent")));
            if (consentBtn.isDisplayed()) {
                consentBtn.click();
                System.out.println("✅ Cookie consent popup dismissed.");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ Cookie consent popup not shown or already dismissed.");
        }
    }

    public void clickOnAddToCartButtonForSpecificItem(String itemName){
        String viewProductLocator = "//p[normalize-space(text())='"+itemName+"']/../../..//li//a[normalize-space(text())='"+viewProductButtonLabel+"']";
        String itemImageLocator = "//p[normalize-space(text())='"+itemName+"']/..//img";
        String buttonLocator = "//div[@class='"+productOverlayClassName+"']//p[normalize-space(text())='"+ itemName+"']/..//a[normalize-space(text())='"+addToCartButtonLabel+"']";
        try{
            waitForElementPresence(viewProductLocator);
            moveToSpecificElement(viewProductLocator);
            waitForElementVisibility(itemImageLocator);
            moveToSpecificElement(itemImageLocator);
            waitForElementVisibility(buttonLocator);
            clickOnElement(buttonLocator, true);
            ExtentTestManager.getTest().log(Status.PASS, itemName+" item is added to cart");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
    }

    public String getTextFromModal(String modalDivID, String tagName){
        String elementLocator = "//*[local-name()='div' or local-name()='section'][@id='"+modalDivID+"']//"+tagName;
        String value="";
        try{
            waitForElementVisibility(elementLocator);
            value = StringUtils.normalizeSpace(getTextFromElement(elementLocator));
            ExtentTestManager.getTest().log(Status.PASS, value + " value is retrived");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
        return value;
    }

    public void clickOnButton(String buttonName, boolean scriptExecutor){
        String buttonLocator = "//*[local-name()='a' or local-name()='button'][normalize-space(text())='"+buttonName+"']";
        try{
            waitForElementPresence(buttonLocator);
            clickOnElement(buttonLocator, scriptExecutor);
            ExtentTestManager.getTest().log(Status.PASS, buttonName +" button is clicked");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
    }

    public void setTextInSpecificField(String fieldLabel, String textValue){
        String fieldLocator = "//label[normalize-space(text())='"+fieldLabel+"']/..//*[local-name()='input' or local-name()='textarea']";
        try{
            waitForElementPresence(fieldLocator);
            setTextInField(fieldLocator, textValue);
            ExtentTestManager.getTest().log(Status.PASS, textValue +" value is set for field "+fieldLabel);
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
    }

    public void clickOnElementWithTagName(String tagName, String buttonName, boolean scriptExecutor){
        String elementLocator = "//*[local-name()='"+tagName+"'][normalize-space(text())='"+buttonName+"']";
        try{
            waitForElementPresence(elementLocator);
            clickOnElement(elementLocator, scriptExecutor);
            ExtentTestManager.getTest().log(Status.PASS, buttonName +" element is clicked");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
    }

    public String getLoggedInUserName(){
        String tabLocator = "//a[normalize-space(text())='"+ loggedInAsTabLabel +"']/..//b";
        String value="";
        try{
            waitForElementPresence(tabLocator);
            value = getTextFromElement(tabLocator);
            ExtentTestManager.getTest().log(Status.PASS, value + " user is loggedIn right now");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
        return value;
    }
}
