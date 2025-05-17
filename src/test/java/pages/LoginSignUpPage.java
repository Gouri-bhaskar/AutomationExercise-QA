package pages;

import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import modules.ExtentTestManager;
import tests.BaseTest;

public class LoginSignUpPage extends BaseTest {

    // Start: Field Label
    public final static String passwordFieldLabel = "Password";
    public final static String firstNameFieldLabel = "First name";
    public final static String lastNameFieldLabel = "Last name";
    public final static String companyFieldLabel = "Company";
    public final static String addressFieldLabel = "Address";
    public final static String address2FieldLabel = "Address 2";
    public final static String stateFieldLabel = "State";
    public final static String cityFieldLabel = "City";
    public final static String zipCodeFieldLabel = "Zipcode";
    public final static String mobileNumberFieldLabel = "Mobile Number";
    // End: Field Label

    // Start: Placeholder text
    public final static String nameFieldPlaceholderText = "Name";
    public final static String emailAddressFieldPlaceholderText = "Email Address";
    // End: Placeholder text

    // Start: Button Name
    public final static String signUpButtonLabel = "Signup";
    public final static String loginButtonLabel = "Login";
    public final static String createAccountLabel = "Create Account";
    public final static String continueLabel = "Continue";
    // End: Button Name

    // Start: Section Header text
    public final static String newUserSignupText = "New User Signup!";
    public final static String loginToYourAccountText = "Login to your account";

    // ✅ Added for compatibility — matches your previous call
    public final static String loginText = loginToYourAccountText;
    // End: Section Header text

    // Start: Url
    public final static String loginUrlextention = "login";
    public final static String signupUrlextention = "signup";
    public final static String accountCreatedUrlextention = "account_created";
    // End: Url

    // Start: Section ID
    public final static String formSectionID = "form";
    // End: Section ID

    // Start: Dropdown Label
    public final static String dateOfBirthDropdownLabel = "Date of Birth";
    public final static String dayDropdownLabel = "Day";
    public final static String monthDropdownLabel = "Month";
    public final static String yearDropdownLabel = "Year";
    // End: Dropdown Label

    public void setTextInSpecificFieldWithPalceHolder(String sectionHeader, String placeholderText, String textValue) {
        String fieldLocator = "//h2[normalize-space(.)='" + sectionHeader + "']/..//input[@placeholder ='" + placeholderText + "']";
        try {
            waitForElementVisibility(fieldLocator);
            setTextInField(fieldLocator, textValue);
            ExtentTestManager.getTest().log(Status.PASS,
                    textValue + " value is set for field with " + placeholderText + " placeholder");
        } catch (Exception e) {
            captureScreenshot();
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
        }
    }

    public void selectRadioButtonOption(String optionLabel) {
        String elementLocator = "//label[normalize-space(.)='" + optionLabel + "']/..//input";
        try {
            waitForElementVisibility(elementLocator);
            clickOnElement(elementLocator, false);
            ExtentTestManager.getTest().log(Status.PASS, optionLabel + " Radio button is selected");
        } catch (Exception e) {
            captureScreenshot();
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
        }
    }

    public void selectValueFromDropdown(String dropdownLabel, String defaultValue, String value) {
        String elementLocator = "//label[normalize-space(.)='" + dropdownLabel + "']/..//option[normalize-space(text())='" + defaultValue + "']/../..//select";
        try {
            waitForElementVisibility(elementLocator);
            Select select = new Select(getElementFromLocator(elementLocator));
            select.selectByValue(value);
            ExtentTestManager.getTest().log(Status.PASS,
                    value + " value is selected for " + defaultValue + " dropdown");
        } catch (Exception e) {
            captureScreenshot();
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
        }
    }
}
