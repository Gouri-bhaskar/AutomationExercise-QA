package pages;

import com.aventstack.extentreports.Status;
import modules.ExtentTestManager;
import tests.BaseTest;

public class PaymentPage extends BaseTest {

    //Start: Field Label
    public final static String nameOnCardFieldLabel = "Name on Card";
    public final static String cardNumberFieldLabel = "Card Number";
    public final static String expirationLabel = "Expiration";
    public final static String cvcFieldLabel = "CVC";
    //End: Field Label

    //Start: Button Name
    public final static String payAndConfirmOrderButtonLabel = "Pay and Confirm Order";
    //End:   Button Name

    //Start: Url
    public final static String paymentUrlextention = "payment";
    public final static String paymentDoneUrlextention = "payment_done";
    //End: Url

    //Start: Section ID
    public final static String formSectionID = "form";
    //End: Section ID

    public void setTextInSpecificFieldInPaymentPage(String fieldLabel, String textValue){
        String fieldLocator = "//label[normalize-space(text())='"+fieldLabel+"']/../following::div/*[local-name()='input' or local-name()='textarea']";
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
}
