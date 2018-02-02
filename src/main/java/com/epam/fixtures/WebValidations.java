package com.epam.fixtures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.ObjectRepository;


public class WebValidations extends CoreDriverScript {
	
	/** The wait for element. */
	private boolean waitForElement;

	/** The driver. */
	private WebDriver driver;

		
	  /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger
            .getLogger(WebValidations.class);

    /**
     * Instantiates a new aft validation fixtures.
     * 
     */
    public WebValidations(WebDriver driver) throws Exception{
    	try {
			this.driver = driver;
			} catch (WebDriverException e) {
			LOGGER.error("Exception::", e);
		}
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param objectID
     *            The element to retrieve the inner text OR path of .pdf file
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
    public boolean verifyText(String objectID, String textPattern) throws Exception {
        boolean bTextMatch = false;
        String objectLocator = null;
       
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
        	String description = "Clicking on the object: " + objectID;
        	actions.preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			
            driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

         			waitForElement = actions.waitForElementPresent(
         					objectID,
         					Integer.parseInt(Config.getConfig().getConfigProperty(
         							"ElementWaitTime")));
         			if (waitForElement) {
         				LOGGER.trace("Element [" + objectLocator + "] is found");
         				
            if ("novalue".equalsIgnoreCase(objectID)
                    || objectID.trim().length() <= 0) {

                bTextMatch = verifyTextSub(objectID, textPattern);

            } else {
                bTextMatch = isTextMatches(objectID, textPattern);
            }
		}
        } catch (WebDriverException e) {
            throw new FrameworkExceptions(e.getMessage(), e);
        } finally {
            
            final int pollTime = Integer.parseInt(Config.getConfig().getConfigProperty(
						"ElementWaitTime"));
            if (pollTime > 0) {
                driver.manage().timeouts().implicitlyWait(pollTime, TimeUnit.SECONDS);
            }
        }

        return bTextMatch;
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param testStepRunner
     *            testStepRunner
     * @param objectID
     *            The element to retrieve the inner text OR path of .pdf file
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * @param repositoryObject
     *            repositoryObject
     * 
     * @return boolean: true/false
     * 
     * 
     */
    private boolean isTextMatches(String objectID, String textPattern)
            throws Exception {
        boolean bTextMatch = false;
        if (objectID.isEmpty()) {
            final String errorMsg = "Invalid element [" + objectID
                    + "] specified";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }

        if (actions.isDisplayed(objectID)) {
            LOGGER.trace("Element [" + objectID + "] is found");
            final String innerText = actions.getText(objectID);
            if (innerText.contains(textPattern)) {
                LOGGER.info("Verify: Success, the expected inner text value ["
                        + textPattern + "] found on object [" + objectID
                        + "]");
                bTextMatch = true;
            } else {
                bTextMatch = false;
                LOGGER.error("Verify: Failed, the expected inner text value ["
                        + textPattern + "] not found on object ["
                        + objectID + "]");
                throw new FrameworkExceptions("Verify: Failed, the expected text ["
                        + textPattern + "] and the actual text [" + innerText
                        + "] does not match");
            }
        } else {
            final String errorMsg = "Element [" + objectID
                    + "] not found on page";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }
        return bTextMatch;
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param stepRunner
     *            stepRunner
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param elementValue
     *            The elementValue to validate
     * 
     * @return boolean: true/false
     * 
     * @throws 
     */
    public boolean verifyPdfText(final String elementName, final String elementValue)
            throws FrameworkExceptions {
	
	     boolean bTextMatch = false;
	     try {
		 LOGGER.debug("Executing command: [verifyText] on pdf document ["
                + elementName + "]");

		 LOGGER.info("Extracting text from pdf docucment [" + elementName + "]");
		 final String textPattern = elementValue;
       
		 	bTextMatch = performVerifyPdfAction(elementName, textPattern, true);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	return bTextMatch;
        
    }

    /**
     * This method is sub method of verifyText method.
     * 
     * @param objectID
     *            The element to retrieve the inner text OR path of .pdf file
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * @return boolean true/false
     * @throws Exception 
     * @throws FrameworkExceptions
     */
    public boolean verifyTextSub(String objectID, String textPattern)
            throws Exception {
        boolean bTextMatch = false;

        try {
            LOGGER.debug("Executing command: [verifyTextSub] for textPattern ["
                    + textPattern + "] on whole page");

            String htmlBodyText = "";
            if (Config.getConfig().getConfigProperty(
            		"BrowserType").toLowerCase().contains("safari")) {
            	
            
                htmlBodyText = driver.findElement(By.tagName("body")).getText();
            } else {
               
                htmlBodyText = (String) actions.jsExecutor("return document.getElementsByTagName('html')[0].innerHTML");
            }
            if (htmlBodyText.contains(textPattern)) {

                LOGGER.info("Verify: Success, value [" + textPattern
                        + "] is present on page");
                bTextMatch = true;
            } else {
                bTextMatch = false;
                LOGGER.error("Verify: Failed, value [" + textPattern
                        + "] is not present on page");
                throw new FrameworkExceptions("Verify: Failed, The text ["
                        + textPattern + "] is not present on the page");
            }

        } catch (WebDriverException e) {
            throw new FrameworkExceptions(e.getMessage(), e);
        }
        return bTextMatch;

    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param objectID
     *            The element to retrieve the inner text OR path of .pdf file
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
    public boolean verifyTextNotPresent(String objectID, String textPattern) throws Exception {

        boolean bTextNotMatch = true;
        String objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);
        LOGGER.trace("Executing command: [verifyTextNotPresent] with objectId ["
                + objectLocator + "], textPattern [" + textPattern + "]");
        
        if (objectLocator.toLowerCase().endsWith("pdf")) {
            bTextNotMatch = verifyPdfTextNotPresent(objectID, textPattern);
        } else if ("novalue".equalsIgnoreCase(objectID)
                || objectID.trim().length() <= 0) {

            LOGGER.debug("Executing command: [verifyTextNotPresent] for textPattern ["
                    + textPattern + "] on whole page");
            if (!driver.findElement(By.tagName("body")).getText().contains(textPattern)) {
                LOGGER.info("Verify: Success, value [" + textPattern
                        + "] is NOT present on page");
                bTextNotMatch = true;
            } else {
                bTextNotMatch = false;
                LOGGER.error("Verify: Failed, value [" + textPattern
                        + "] is present on page");
                throw new FrameworkExceptions("Verify: Failed, The text ["
                        + textPattern + "] is present on the page");
            }

        } else {
            bTextNotMatch = verifyTextNotPresentSub(objectID, textPattern, objectLocator);
        }

        return bTextNotMatch;
    }

    /**
     * This method is sub method of verifyTextNotPresent method.
     * 
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * @param objectLocator
     *            objectLocator
     * @param repositoryObject
     *            repositoryObject
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
    public boolean verifyTextNotPresentSub(String objectID, String textPattern, String objectLocator)
            throws Exception {
        boolean bTextNotMatch = true;

        if (objectLocator.isEmpty()) {
            final String errorMsg = "Invalid element [" + objectID
                    + "] specified";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }

        if (actions.isDisplayed(objectID)) {
            LOGGER.trace("Element [" + objectLocator + "] is found");
            final String innerText = actions.getText(objectID);
            if (!innerText.equalsIgnoreCase(textPattern)) {
                LOGGER.info("Verify: Success, the expected inner text value ["
                        + textPattern + "] not found on object ["
                        + objectLocator + "]");
                bTextNotMatch = true;
            } else {
                bTextNotMatch = false;
                LOGGER.error("Verify: Failed, the expected inner text value ["
                        + textPattern + "] found on object [" + objectLocator
                        + "]");
                throw new FrameworkExceptions("Verify: Failed, the expected text ["
                        + textPattern + "] and the actual text [" + innerText
                        + "] match");
            }
        } else {
            final String errorMsg = "Element [" + objectLocator
                    + "] not found on page";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }

        return bTextNotMatch;

    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */

    public boolean verifyPdfTextNotPresent(String objectID,
            final String txtPattern) throws Exception {
        boolean bTextNotMatch = true;

        LOGGER.debug("Executing command: [verifyPdfTextNotPresent] on pdf document ["
                + objectID + "]");

        LOGGER.info("Extracting text from pdf docucment [" + objectID + "]");
        bTextNotMatch = performVerifyPdfAction(objectID, txtPattern, false);

        return bTextNotMatch;
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
 private boolean performVerifyPdfAction(String objectID, String textPattern, boolean flag) throws Exception {
        PDFParser parser;
        COSDocument cosDoc = null;
        PDDocument pdDoc = null;
        String parsedText;
        boolean bTextMatch = false;
        final File pdfFile = new File(objectID);

        try {
            parser = new PDFParser(new FileInputStream(pdfFile));
            parser.parse();
            cosDoc = parser.getDocument();
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            if (parsedText == null) {
                LOGGER.error("Could not extract data from PDF document ["
                        + objectID + "]");
                throw new FrameworkExceptions(
                        "Could not extract data from PDF document ["
                                + objectID + "]");
            } else {
                LOGGER.debug("Data Extracted from PDF \n" + parsedText + "\n");
                if (flag) {
                    bTextMatch = performVerifyPdfPresent(parsedText,
                            textPattern, objectID);
                } else {
                    bTextMatch = performVerifyPdfNotPresent(parsedText,
                            textPattern, objectID);
                }
            }
            if (cosDoc != null) {
                cosDoc.close();
            }
            if (pdDoc != null) {
                pdDoc.close();
            }
        } catch (FileNotFoundException e) {
            throw new FrameworkExceptions(e.getMessage(), e);
        } catch (IOException e) {
            throw new FrameworkExceptions(e.getMessage(), e);
        }

        return bTextMatch;
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
    private boolean performVerifyPdfPresent(String parsedText, String textPattern, String objectID)
            throws Exception {
        boolean bTextMatch = false;
        if (parsedText.contains(textPattern)) {
            bTextMatch = true;
            LOGGER.info("Verify: Success, expected text value [" + textPattern
                    + "] found in the PDF document [" + objectID + "]");

        } else {
            bTextMatch = false;
            LOGGER.error("Verify: Failed, expected text value [" + textPattern
                    + "] not found in the PDF document [" + objectID + "]");
            throw new FrameworkExceptions("The expected text [" + textPattern
                    + "] could not be found in the PDF document ["
                    + objectID + "]");
        }
        return bTextMatch;
    }

    /**
     * Verifies the innertext of the element with the text pattern to be
     * validated OR verify the text present in a .pdf file
     * 
     * @param elementName
     *            element name of the control as provided by user OR path of the
     *            .pdf file
     * @param textPattern
     *            The text pattern to validate
     * 
     * @return boolean: true/false
     * 
     * @throws FrameworkExceptions
     */
    private boolean performVerifyPdfNotPresent(String parsedText, String textPattern, String objectID)
            throws Exception {
        boolean bTextNotMatch = true;
        if (!parsedText.contains(textPattern)) {
            bTextNotMatch = true;
            LOGGER.info("Verify: Success, expected text value [" + textPattern
                    + "] NOT found in the PDF document [" + objectID + "]");

        } else {
            bTextNotMatch = false;
            LOGGER.error("Verify: Failed, expected text value [" + textPattern
                    + "] found in the PDF document [" + objectID + "]");
            throw new FrameworkExceptions("The expected text [" + textPattern
                    + "] found in the PDF document [" + objectID + "]");

        }
        return bTextNotMatch;
    }

    /**
     * Validates the title of the Page/dialog against the expected title
     * 
     * @param expectedTitle
     *            Expected page/dialog title
     * 
     * @return boolean: true/false
     * @throws FrameworkExceptions
     */
    public boolean verifyTitle(String expectedTitle) throws Exception {
        boolean bTitleValueMatch = false;

        LOGGER.trace("Executing command: [verifyTitle] for expectedTitle ["
                + expectedTitle + "]");
        final String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle)) {
            LOGGER.info("Verify: Success, actual Title is [" + actualTitle
                    + "], expected Title is [" + expectedTitle + "]");
            bTitleValueMatch = true;
        } else {
            LOGGER.error("Verify: Failed, actual Title is [" + actualTitle
                    + "], expected Title is [" + expectedTitle + "]");
            bTitleValueMatch = false;
            throw new FrameworkExceptions("Verify: Failed, The expected title ["
                    + expectedTitle + "] and the actual title for the page ["
                    + actualTitle + "] does not match");
        }

        return bTitleValueMatch;
    }

    /**
     * Validates value of the ObjectId against the expected value
     * 
     * @param objectID
     *            unique object id of the control to verify selected value from
     * @param elementName
     *            element name of the control as provided by user
     * @param value
     *            the value to verify
     * 
     * @return boolean: true/false
     * @throws FrameworkExceptions
     */
    public boolean verifyValue(String objectID, String value)
            throws Exception {
        boolean bTextValueMatch = false;
        String objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);

        LOGGER.trace("Executing command: [verifyValue] for ObjectId ["
                + objectLocator + "], value [" + value + "]");
        if (actions.isDisplayed(objectID)) {
            LOGGER.trace("Element [" + objectLocator + "] is found");
            final String objectValue = actions.getAttribute(objectID, value);
            if (objectValue.equalsIgnoreCase(value)) {
                LOGGER.info("Verify: Success, value in object is ["
                        + objectValue + "], expected value is [" + value + "]");
                bTextValueMatch = true;
            } else {
                LOGGER.error("Verify: Failed, Value in object is  ["
                        + objectValue + "], expected value is [" + value + "]");
                bTextValueMatch = false;
            }
            if (!bTextValueMatch) {
                throw new FrameworkExceptions("Verify: Failed, The expected value ["
                        + value + "] for object [" + objectLocator
                        + "] does not match with actual value [" + objectValue
                        + "]");
            }

        } else {
            final String errorMsg = "Element [" + objectLocator + "] not found";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }

        return bTextValueMatch;
    }

    /**
     * Verify control state for editable objects like textbox, combobox, list
     * control, radi button etc
     * 
     * @param elementName
     *            element name of the control as provided by user
     * @param objectID
     *            unique object id of the control to verify selected value from
     * @param expectedValue
     *            the value to verify
     * 
     * @return Boolean value - true if count matches and false if count
     *         mismatches
     * @throws FrameworkExceptions
     */
    public boolean verifyState(String objectID, String expectedValue) throws Exception {
        boolean stateValue = false;
        if (actions.isDisplayed(objectID)) {
            stateValue = true;
        } else {
            final String errMsg = "Element [" + objectID + "] not found";
            LOGGER.error(errMsg);
            throw new FrameworkExceptions(errMsg);
        }

        return stateValue;
    }

    /**
     * This method is related to verifyState method Verify control state for
     * editable objects like textbox, combobox, list control, radio button etc
     * 
     * @param elementName
     *            element name of the control as provided by user
     * @param expectedValue
     *            the value to verify
     * @param objectLocator
     *            objectLocator
     * @param objType
     *            objType
     * @param repositoryObject
     *            repositoryObject
     * @return Boolean value - true if count matches and false if count
     *         mismatches
     * @throws FrameworkExceptions
     */

    /**
     * This method is related to objectState method Verify control state for
     * editable objects like textbox, combobox, list control, radi button etc
     * 
     * @param elementName
     *            element name of the control as provided by user
     * @param expectedValue
     *            the value to verify
     * @param objectLocator
     *            objectLocator
     * @param objType
     *            objType
     * @param repositoryObject
     *            repositoryObject
     * @return Boolean value - true if count matches and false if count
     *         mismatches
     * @throws FrameworkExceptions
     */

    public boolean objectType(String objectID,
            String expectedValue, String objectLocator,
           String objType)
            throws Exception {
        boolean stateValue = false;
        boolean isEditable = false;
        boolean isSelected = false;
        boolean isVisible = false;

        LOGGER.trace("Element [" + objectLocator + "], [" + objType
                + "] is of input type.");

        isVisible = actions.isDisplayed(objectID);
        if (!"spn".equalsIgnoreCase(objType)
                || !"img".equalsIgnoreCase(objType)) {
            isEditable = actions.isEnabled(objectID);
            isSelected = actions.isSelected(objectID);
        }
        stateValue = getStateValue(objectID, expectedValue, objectLocator,
                objType, isVisible, isSelected, isEditable);

        return stateValue;

    }

    /**
     * This method is related to objectState method Verify control state for
     * editable objects like textbox, combobox, list control, radio button etc
     * 
     * @param elementName
     *            element name of the control as provided by user
     * @param expectedValue
     *            the value to verify
     * @param objectLocator
     *            objectLocator
     * @param objType
     *            objType
     * @param isVisible
     *            isVisible
     * @param isSelected
     *            isSelected
     * @param isEditable
     *            isEditable
     * @return Boolean value - true if count matches and false if count
     *         mismatches
     * @throws FrameworkExceptions
     */
    private boolean getStateValue(String objectID,
            String expectedValue, String objectLocator,
            String objType, boolean isVisible,
            boolean isSelected, boolean isEditable)
            throws Exception {
        boolean stateValue = false;
        if (expectedValue.toLowerCase().contains("visible")
                || expectedValue.toLowerCase().contains("hidden")) {
            stateValue = verifyVisible(objectID, expectedValue,
                    objectLocator, objType, stateValue, isVisible);
        }

        if (expectedValue.toLowerCase().contains("selected")
                || expectedValue.toLowerCase().contains("checked")) {
            if ("selected".equalsIgnoreCase(expectedValue)
                    || "checked".equalsIgnoreCase(expectedValue)) {
                stateValue = verifySelected(objectID, expectedValue,
                        objectLocator, objType, stateValue, isSelected);
            } else {
                stateValue = verifySelected(objectID, expectedValue,
                        objectLocator, objType, stateValue, isSelected);
            }
        }

        stateValue = actions.isEnabled(objectID);
        return stateValue;
    }

    /**
     * This method is subMethod of objectType method
     * 
     * @param elementName
     *            element name of the control as provided by user
     * @param expectedValue
     *            the value to verify
     * @param objectLocator
     *            objectLocator
     * @param objType
     *            objType
     * @param stateValue
     *            stateValue
     * @param isVisible
     *            isVisible
     * @return Boolean value - true if count matches and false if count
     *         mismatches
     * @throws FrameworkExceptions
     */

    public boolean verifyVisible(final String objectID,
            final String expectedValue, final String objectLocator,
            final String objType, final boolean stateValue,
            final boolean isVisible) throws FrameworkExceptions {
        boolean objState = stateValue;

        objState = actions.isDisplayed(objectID);

        return objState;
    }

    public boolean verifySelected(final String elementName,
            final String expectedValue, final String objectLocator,
            final String objType, final boolean stateValue,
            final boolean isSelected) throws FrameworkExceptions {
        boolean objState = stateValue;

        if (isSelected
                && ("selected".equalsIgnoreCase(expectedValue) || "checked"
                        .equalsIgnoreCase(expectedValue))) {
            LOGGER.trace("Element [" + objectLocator + "] is selected");
            LOGGER.info("Verify: Success, State of [" + objType + "] element ["
                    + elementName + "] matches with expected value ["
                    + expectedValue + "]");

            objState = true;
        } else if (isUnSelected(isSelected, expectedValue)) {
            LOGGER.trace("Element [" + objectLocator + "] is unselected");
            LOGGER.info("Verify: Success, State of [" + objType + "] element ["
                    + elementName + "] matches with expected value ["
                    + expectedValue + "]");
            objState = true;
        } else {
            final String errMsg = "Verify: Failed, Element [" + elementName
                    + "] is [" + (isSelected ? "selected" : "unselected")
                    + "]. Expected value [" + expectedValue
                    + "] does not match actual value.";
            LOGGER.error(errMsg);
            throw new FrameworkExceptions(errMsg);
        }

        return objState;
    }

    private boolean isUnSelected(final boolean isSelected,
            final String expectedValue) {
        return !isSelected
                && ("unselected".equalsIgnoreCase(expectedValue) || "unchecked"
                        .equalsIgnoreCase(expectedValue));
    }

    /**
     * Verifies the specified item list exists in the list box or drop down
     * items.
     * 
     * @param objectID
     *            the objectID
     * @param value
     *            the list of items to be verified
     * @param elementName
     *            elementName
     * @return true or false based on the verification
     * @throws FrameworkExceptions
     */
    public boolean verifySelectOptions(final String objectID, final String value)
            throws FrameworkExceptions {
        boolean result = false;
        boolean isElementPresent = false;
        LOGGER.trace("Verifying that element [" + objectID + "] to be present");

        final WebElement element = actions.findElement(objectID);
        if (element != null) {
            isElementPresent = true;
        }

        if (isElementPresent) {
            LOGGER.trace("Element [" + objectID + "] is found");

            final Select select = new Select(element);
            final List<WebElement> items = select.getOptions();
            final ArrayList<String> listItems = new ArrayList<String>();
            for (WebElement option : items) {
                listItems.add(option.getText());
            }
            final String[] itemList = listItems.toArray(new String[listItems
                    .size()]);
            result = getResult(value, itemList);

        } else {
            final String errorMsg = "Element [" + objectID + "] not found";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }

        return result;
    }

    /**
     * Verifies the specified item list exists in the list box or drop down
     * items.
     * 
     * @param value
     *            the list of items to be verified
     * @param itemList
     *            itemList
     * @return true or false based on the verification
     * @throws FrameworkExceptions
     */
    private boolean getResult(final String value, final String[] itemList)
            throws FrameworkExceptions {
        int matchCount = 0;
        boolean result = false;
        final String[] inputItemList = value.split(",");
        final int expectedCount = inputItemList.length;
        final int actualCount = itemList.length;

        for (int subitem = 0; subitem < expectedCount; subitem++) {
            for (int item = 0; item < actualCount; item++) {
                if (itemList[item].trim().equals(inputItemList[subitem].trim())) {
                    matchCount++;
                    if (matchCount == expectedCount) {
                        LOGGER.info("Verify: Success, Expected list of items "
                                + "[" + value + "]" + "found in "
                                + Arrays.toString(itemList));
                        result = true;
                        break;
                    }
                }
            }
        }
        if (matchCount != expectedCount) {
            final String errMsg = "Verify: Failure, Expected list of items "
                    + "[" + value + "]" + "not found in "
                    + Arrays.toString(itemList);
            LOGGER.error(errMsg);
            result = false;
            throw new FrameworkExceptions(errMsg);
        }
        return result;
    }

    /**
     * Verifies the Visibility of the element
     * 
     * @param objectID
     *            The element to check the visibility
     * @param value
     *            Expected visibility of the element
     * @param elementName
     *            elementName
     * @return boolean: true/false
     * @throws FrameworkExceptions
     */
    public boolean verifyIsVisible(final String objectID, final String value)
            throws FrameworkExceptions {
        final boolean elementVisibility = Boolean.parseBoolean(value);
        boolean isVisible = false;
        boolean isElementPresent = false;

        LOGGER.trace("Executing command: [verifyIsVisible] with ObjectId ["
                + objectID + "], value [" + value + "]");

        final WebElement element = actions.findElement(objectID);
        if (element != null) {
            isElementPresent = true;
        }

        if (isElementPresent) {
            LOGGER.trace("Element [" + objectID + "] is found");
            final boolean visibility = element.isDisplayed();

            if (visibility == elementVisibility) {
                LOGGER.info("Verify: Success, expected element visibilty as  ["
                        + elementVisibility + "] but was [" + visibility + "]");
                isVisible = true;
            } else {
                LOGGER.error("Verify: Failed, expected element visibilty as  ["
                        + elementVisibility + "] but was [" + visibility + "]");
                isVisible = false;
            }
            if (!isVisible) {
                throw new FrameworkExceptions(
                        "Verify: Failed, The expected element visibilty ["
                                + elementVisibility
                                + "] for object ["
                                + objectID
                                + "] does not match the actual element visibilty ["
                                + visibility + "]");
            }
        } else {
            final String errorMsg = "Element [" + objectID + "] not found";
            LOGGER.error(errorMsg);
            throw new FrameworkExceptions(errorMsg);
        }
        return isVisible;
    }

    /**
     * Verifies the existence of the element
     * 
     * @param objectID
     *            The element to check the existence
     * @param value
     *            Expected existence of the element
     * @param elementName
     *            elementName
     * @return boolean: true/false
     * @throws FrameworkExceptions
     */
    public boolean verifyElement(final String objectID, final String value)
            throws FrameworkExceptions {
        boolean isElementPresent = false;

        if (value == null || value.length() <= 0) {
            LOGGER.error("User must specify if they are looking for existence or non-existence of the element. Refer to xAFT documentation for more details");
            throw new FrameworkExceptions(
                    "User must specify if they are looking for existence or non-existence of the element. Refer to xAFT documentation for more details");
        }

        final boolean expectedExistence = Boolean.parseBoolean(value);

        if (actions.isDisplayed(objectID)==false) {
            LOGGER.error("Element [" + objectID
                    + "] not found in Object Repository");
            throw new FrameworkExceptions("Element [" + objectID
                    + "] not found in Object Repository");
        }
        try {
            final WebElement element = actions.findElement(objectID);
            if (element != null) {
            	isElementPresent = true;
            }
        } catch (WebDriverException e) {
            if (expectedExistence) {
                LOGGER.error("Element [" + objectID + "] found ::");
            } else {
                LOGGER.info("Element [" + objectID + "] not found ::");
            }
            throw new FrameworkExceptions(e.getMessage(), e);
        }
        LOGGER.trace("Executing command: [verifyElement] with ObjectId ["
                + objectID + "], value [" + value + "]");
        isElementPresent = actions.isDisplayed(objectID);

        return isElementPresent;
    }

    /**
     * Verify attribute.
     * 
     * @param objectID
     *            the object id
     * @param value
     *            the value
     * @param elementName
     *            elementName
     * @return true, if successful
     * @throws FrameworkExceptions
     *             the FrameworkExceptions
     */
    public boolean verifyAttribute(final String objectID, final String value)
            throws FrameworkExceptions {
        String attriName, attriValue = null, actualValue = null;
        final String[] attributes = value.split("=");

        if (attributes.length < 2) {
            final String errMessage = "Invalid format specified in element value."
                    + " Please check the AFT Wiki";
            LOGGER.error(errMessage);
            throw new FrameworkExceptions(errMessage);
        }
        attriName = attributes[0];
        attriValue = attributes[1];
        LOGGER.info("Executing command: [verifyAttribute] with ObjectId ["
                + objectID + "], with attribute name [" + attriName
                + "] with attribute value [" + attriValue + "]");
        LOGGER.debug("Calling getAttribute method");
        actualValue = actions.getAttribute(objectID,attriName);

        boolean bMatch = false;
        if (attriValue.equalsIgnoreCase(actualValue)) {
            final String errMessage = "Expected AttributeValue [" + attriValue
                    + "] matches with Actual Attribute Value [" + actualValue
                    + "]";
            LOGGER.info(errMessage);
            bMatch = true;
        } else {
            final String errMessage = "Expected AttributeValue [" + attriValue
                    + "] does not match with Actual Attribute Value ["
                    + actualValue + "]";
            bMatch = false;
            LOGGER.error(errMessage);
            throw new FrameworkExceptions(errMessage);
        }
        return bMatch;

    }

 
}
