package pages;

import com.aventstack.extentreports.Status;
import modules.ExtentTestManager;
import org.openqa.selenium.WebElement;
import tests.BaseTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartPage extends BaseTest {

    //Start: Button Name
    public final static String proceedToCheckoutButtonLabel = "Proceed To Checkout";
    public final static String registerLoginButtonLabel = "Register / Login";
    public final static String placeOrderButtonLabel = "Place Order";
    //End:   Button Name

    //Start: DivID
    public final static String cartInfoDivID = "cart_info";
    //End: DivID

    //Start: Field label
    public final static String totalAmountFieldLabel = "Total Amount";
    //End: Field label

    //Start: Table Column Header
    public final static String itemColumnLabel = "Item";
    public final static String descriptionColumnLabel = "Description";
    public final static String priceColumnLabel = "Price";
    public final static String quantityColumnLabel = "Quantity";
    public final static String totalColumnLabel = "Total";
    //End: Table Column Header

    //Start: Url
    public final static String viewCartUrlextention = "view_cart";
    public final static String checkoutUrlextention = "checkout";
    //End: Url

    //Start: DivID
    public final static String checOutModalDivID = "checkoutModal";
    public final static String addressDeliveryDivID = "address_delivery";
    public final static String addressInvoiceDivID = "address_invoice";
    //End: DivID

    //Start: TextArea label
    public final static String ifYouWouldLikeToAddACommentAboutYourOrderPleaseWriteItInTheFieldBelowTextAreaLabel = "If you would like to add a comment about your order, please write it in the field below.";
    //End: TextArea label

    public List<String> getAllTextFromArea(String modalDivID, String tagName){
        String elementLocator = "//*[local-name()='ul'][@id='"+modalDivID+"']//"+tagName;
        List <String> values = new ArrayList<>(Collections.emptyList());
        try{
            waitForElementsPresence(elementLocator);
            values = getTextFromAllElements(elementLocator);
            for (String value: values) {
                ExtentTestManager.getTest().log(Status.PASS, "'"+ value+"' values are retrived from line no  "+ String.valueOf(values.indexOf(value) +1));
            }
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
        return values;
    }

    public String getSpecificTableValue(String modalDivID, String columnIdentifier, String rowIdentifier, String atrributeName){
        String allColumnHeader = "//*[local-name()='div'][@id='"+modalDivID+"']//thead//td";
        String targetColumnHeader = "//*[local-name()='div'][@id='"+modalDivID+"']//thead//td[normalize-space()='"+columnIdentifier+"']";
        String value="";
        try{
            List <WebElement> allColumnHeaderElements = getAllElementsFromLocator(allColumnHeader);
            WebElement element = getElementFromLocator(targetColumnHeader);
            int coulumnIndex = allColumnHeaderElements.indexOf(element)+1;
            String gridDataLocator = "//*[local-name()='div'][@id='"+modalDivID+"']//tbody//td//a[normalize-space()='"+rowIdentifier+"']/../../../td["+coulumnIndex+"]" +
                    "//*[local-name()='p' or local-name()='button' or local-name()='img']";
            if(!atrributeName.equals("")){
                value = getAttributeFromElement(gridDataLocator, atrributeName);
            }
            else value = getTextFromElement(gridDataLocator);
            ExtentTestManager.getTest().log(Status.PASS, "'"+value+"' value is retrived from column '"+columnIdentifier+ "' for item '"+ rowIdentifier+"'");
        }
        catch (Exception e){
            ExtentTestManager.getTest().log(Status.FAIL, e.getMessage());
            captureScreenshot();
        }
        return value;
    }
}
