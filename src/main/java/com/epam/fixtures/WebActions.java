package com.epam.fixtures;

//import io.appium.java_client.AppiumDriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.ObjectRepository;
import com.epam.utils.report.testsuite.TestStep;
import com.google.common.base.Function;
/**
 * The Class WebActions.
 */
public class WebActions extends CoreDriverScript {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(WebActions.class);

	/** The wait for element. */
	private boolean waitForElement;

	/** The driver. */
	private WebDriver driver;

	/** The elm. */
	private WebElement elm;

	/** The robot. */
	private Robot robot;

	/** The Actions */
	private Actions actionBuilder;

	/** The Constant KEY_DELAY_MS. */
	private static final int KEY_DELAY_MS = 500;

	/** The Constant MOUSEACTION_BUTTONPRESS. */
	public static final String MOUSEACTION_BUTTONPRESS = "BUTTONPRESS";

	/** The Constant MOUSEACTION_MOUSEMOVE. */
	public static final String MOUSEACTION_MOUSEMOVE = "MOUSEMOVE";

	/** The Constant MOUSE_CLICKBUTTON_LEFT. */
	public static final String MOUSE_CLICKBUTTON_LEFT = "LEFT";

	/** The Constant MOUSE_CLICKBUTTON_RIGHT. */
	public static final String MOUSE_CLICKBUTTON_RIGHT = "RIGHT";

	/** The Constant MOUSE_CLICKBUTTON_MIDDLE. */
	public static final String MOUSE_CLICKBUTTON_MIDDLE = "CENTER";

	/** The test step start time. */
	public long tStepStartTime = 0;

	/** The test step end time. */
	public long tStepEndTime = 0;

	/** The test step exec time. */
	public long tStepExecTime = 0;

	/** The test case execution time. */
	public long tCaseExecutionTime = 0;

	/** The test case flag. */
	public boolean tcaseflag = false;

	/** The is reusable. */
	public boolean isReusable;

	/** The test_step_result. */
	public String test_step_result = null;

	public boolean blnCaptureScreenShotOnFail = false;
	public boolean blnCaptureScreenShotOnPass = false;	

	String captureScreenShotOn = null;

	/** Object for Alert Class*/
	public Alert alert = null;

	/**
	 * Instantiates a new Automation command fixtures.
	 * 
	 */
	public WebActions(WebDriver driver) throws Exception {
		try {
			this.driver = driver;
			robot = new Robot();
		} catch (AWTException e) {
			LOGGER.error("Exception::", e);
		}
	}

	/**
	 * Pre test step.
	 * 
	 * @param description
	 *        description for the step.    
	 */
	public void preTestStep(String description) {
		tStepStartTime = 0;
		tStepEndTime = 0;
		tStepStartTime = System.currentTimeMillis();
		CoreDriverScript.testScipt.setTestStepId(1);
		CoreDriverScript.testScipt.setIndex(null);

		isReusable = true;

		// create a test step object for reporting purpose
		CoreDriverScript.tStep = new TestStep();
		CoreDriverScript.tStep.setReusable(isReusable);
		LOGGER.debug("Test step object created for reporting");

		CoreDriverScript.stepDescription = description;
		CoreDriverScript.inputData = null;
		LOGGER.info("keyword value is " + CoreDriverScript.keyword);
		CoreDriverScript.currentTSID = CoreDriverScript.stepDescription;
		CoreDriverScript.tStep.setActionDescription(CoreDriverScript.stepDescription);// + " : "	+ CoreDriverScript.inputData);
		CoreDriverScript.tStep.setAction(CoreDriverScript.stepDescription);
		CoreDriverScript.tStep.setElementName(CoreDriverScript.keyword);
		CoreDriverScript.tStep.setElementValue(CoreDriverScript.object);
		CoreDriverScript.tStep.setStepId(CoreDriverScript.currentTSID);
		CoreDriverScript.tStep.setTestCase(CoreDriverScript.tCase);
		LOGGER.debug("Test step " + CoreDriverScript.stepDescription
				+ " is added to the testcase " + CoreDriverScript.tCase);
	}

	/**
	 * Post test step.
	 * 
	 * @param result
	 *		  result of the executed step.
	 */
	public void postTestStep(String result) {
		String stepPath = null;

		CoreDriverScript.test_step_result = result;
		try {
			captureScreenShotOn = Config.getConfig().getConfigProperty("CaptureScreenShot");			
			if(captureScreenShotOn.equalsIgnoreCase("ALL"))
			{
				blnCaptureScreenShotOnFail = true;
				blnCaptureScreenShotOnPass = true;				
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//if exception this should fail add condition later

		if (null != CoreDriverScript.test_step_result && CoreDriverScript.test_step_result.equalsIgnoreCase("FAIL")) {
			try {
				if(captureScreenShotOn.equalsIgnoreCase("FAIL") || captureScreenShotOn.equalsIgnoreCase("BOTH"))
				{
					blnCaptureScreenShotOnFail = true;
				}
				SimpleDateFormat sdt = new SimpleDateFormat("ddMMyyyy_hhmmss");
				Date now = new Date();
				String dateTime = sdt.format(now);
				CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
						.getCurrentTest()
						+ "_TC"
						+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
						+ "_TS"
						+ CoreDriverScript.testScipt.getTestStepId()
						+ "_" + dateTime + CoreDriverScript.screenshotFileType;
				CoreDriverScript.screenShotFile = Config.getConfig()
						.getConfigProperty("screenshotPath")
						+ CoreDriverScript.screenshot_fileName;
				if(blnCaptureScreenShotOnFail || (blnCaptureScreenShotOnFail&&blnCaptureScreenShotOnPass))
				{ takeScreenShot(CoreDriverScript.screenShotFile); 
				LOGGER.info("Screenshot collected");
				File file = new File(CoreDriverScript.screenShotFile);
				CoreDriverScript.tStep.setImageName(file.getPath());
				CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
				CoreDriverScript.tStep.setResult("FAIL");				
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			CoreDriverScript.tcaseflag = true;
			CoreDriverScript.scenarioStatus = "false";
			CoreDriverScript.isTestSenario = false;
			CoreDriverScript.tStep
			.setErrorMessage("Required object not Found");
		} else if (null != CoreDriverScript.test_step_result && CoreDriverScript.test_step_result.equalsIgnoreCase("PASS")) {
			// if the result is pass then update in the report
			try {
				stepPath = Config.getConfig().getConfigProperty(
						Constants.STEPSCREENSHORTPATH);
			} catch (Exception e) {

				e.printStackTrace();
			}
			if (stepPath.equalsIgnoreCase("STEP")) {
				try {
					if(captureScreenShotOn.equalsIgnoreCase("PASS") || captureScreenShotOn.equalsIgnoreCase("BOTH"))
					{
						blnCaptureScreenShotOnPass = true;
					}
					SimpleDateFormat sdt = new SimpleDateFormat(
							"ddMMyyyy_hhmmss");
					Date now = new Date();
					String dateTime = sdt.format(now);
					CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
							.getCurrentTest()
							+ "_TC"
							+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
							+ "_TS"
							+ CoreDriverScript.testScipt.getTestStepId()
							+ "_"
							+ dateTime + CoreDriverScript.screenshotFileType;
					CoreDriverScript.screenShotFile = Config.getConfig()
							.getConfigProperty("screenshotPath")
							+ CoreDriverScript.screenshot_fileName;

					if(blnCaptureScreenShotOnPass || (blnCaptureScreenShotOnFail&&blnCaptureScreenShotOnPass)){
						takeScreenShot(CoreDriverScript.screenShotFile);
						LOGGER.info("Screenshot collected");
						File file = new File(CoreDriverScript.screenShotFile);
						CoreDriverScript.tStep.setImageName(file.getPath());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}


			}
			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("PASS");

		}

		else  {
			try {
				stepPath = Config.getConfig().getConfigProperty(
						Constants.STEPSCREENSHORTPATH);
			} catch (Exception e) {

				e.printStackTrace();
			}
			if (stepPath.equalsIgnoreCase("STEP")) {
				try {
					SimpleDateFormat sdt = new SimpleDateFormat(
							"ddMMyyyy_hhmmss");
					Date now = new Date();
					String dateTime = sdt.format(now);
					CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
							.getCurrentTest()
							+ "_TC"
							+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
							+ "_TS"
							+ CoreDriverScript.testScipt.getTestStepId()
							+ "_"
							+ dateTime + CoreDriverScript.screenshotFileType;
					CoreDriverScript.screenShotFile = Config.getConfig()
							.getConfigProperty("screenshotPath")
							+ CoreDriverScript.screenshot_fileName;

//					if((blnCaptureScreenShotOnFail&&blnCaptureScreenShotOnPass)){
					if((captureScreenShotOn.equalsIgnoreCase("ALL"))){						
						takeScreenShot(CoreDriverScript.screenShotFile);
						LOGGER.info("Screenshot collected");
						File file = new File(CoreDriverScript.screenShotFile);
						CoreDriverScript.tStep.setImageName(file.getPath());
					}


				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("INFO");

		}

		tStepEndTime = System.currentTimeMillis();
		tStepExecTime = tStepEndTime - tStepStartTime;
		CoreDriverScript.tStep.setTestStepExecutionTime(tStepExecTime);
		tCaseExecutionTime = tCaseExecutionTime + tStepExecTime;
		if (CoreDriverScript.tStep.isReusable()) {
			CoreDriverScript.tStep.getTestCase().addTestStep(
					CoreDriverScript.tStep);
		} else {
			CoreDriverScript.tStep.getTestCase().addTestStep(
					CoreDriverScript.tStep);
		}
	}

	/**
	 * Post test step.
	 *
	 * @param result
	 *		  result of the executed step.
	 * @param strMessage
	 *		  Success/Failure message for the test step.
	 */
	public void postTestStep(String result, String strDescription, String strMessage) throws Exception {
		String stepPath = null;
		CoreDriverScript.test_step_result = result;
		try {
			captureScreenShotOn = Config.getConfig().getConfigProperty("CaptureScreenShot");			
			if(captureScreenShotOn.equalsIgnoreCase("ALL"))
			{
				blnCaptureScreenShotOnFail = true;
				blnCaptureScreenShotOnPass = true;
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//if exception this should fail add condition later
		preTestStep(strDescription);
		if (null != CoreDriverScript.test_step_result
				&& CoreDriverScript.test_step_result.equalsIgnoreCase("FAIL")) {
			try {
				if(captureScreenShotOn.equalsIgnoreCase("FAIL") || captureScreenShotOn.equalsIgnoreCase("BOTH"))
				{
					blnCaptureScreenShotOnFail= true;
				}
				SimpleDateFormat sdt = new SimpleDateFormat("ddMMyyyy_hhmmss");
				Date now = new Date();
				String dateTime = sdt.format(now);
				CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
						.getCurrentTest()
						+ "_TC"
						+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
						+ "_TS"
						+ CoreDriverScript.testScipt.getTestStepId()
						+ "_" + dateTime + CoreDriverScript.screenshotFileType;
				CoreDriverScript.screenShotFile = Config.getConfig()
						.getConfigProperty("screenshotPath")
						+ CoreDriverScript.screenshot_fileName;
				if(blnCaptureScreenShotOnFail || (blnCaptureScreenShotOnFail&&blnCaptureScreenShotOnPass)){
					takeScreenShot(CoreDriverScript.screenShotFile);
					LOGGER.info("Screenshot collected");
					File file = new File(CoreDriverScript.screenShotFile);
					CoreDriverScript.tStep.setImageName(file.getPath());
					System.out.println("fail step - screenshot file path = " + file.getPath() );
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}


			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("FAIL");
			CoreDriverScript.tcaseflag = true;
			CoreDriverScript.scenarioStatus = "false";
			CoreDriverScript.isTestSenario = false;
			CoreDriverScript.tStep.setErrorMessage(strMessage);


		} else {
			// if the result is pass then update in the report
			try {
				stepPath = Config.getConfig().getConfigProperty(
						Constants.STEPSCREENSHORTPATH);


			} catch (Exception e) {

				e.printStackTrace();
			}
			if (stepPath.equalsIgnoreCase("STEP")) {
				try {
					if(captureScreenShotOn.equalsIgnoreCase("PASS") || captureScreenShotOn.equalsIgnoreCase("BOTH"))
					{
						blnCaptureScreenShotOnPass = true;
					}
					SimpleDateFormat sdt = new SimpleDateFormat(
							"ddMMyyyy_hhmmss");
					Date now = new Date();
					String dateTime = sdt.format(now);
					CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
							.getCurrentTest()
							+ "_TC"
							+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
							+ "_TS"
							+ CoreDriverScript.testScipt.getTestStepId()
							+ "_"
							+ dateTime + CoreDriverScript.screenshotFileType;
					CoreDriverScript.screenShotFile = Config.getConfig()
							.getConfigProperty("screenshotPath")
							+ CoreDriverScript.screenshot_fileName;
					if(blnCaptureScreenShotOnPass || (blnCaptureScreenShotOnFail&&blnCaptureScreenShotOnPass)){
						takeScreenShot(CoreDriverScript.screenShotFile);
						LOGGER.info("Screenshot collected");
						File file = new File(CoreDriverScript.screenShotFile);
						CoreDriverScript.tStep.setImageName(file.getPath());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("PASS");
			CoreDriverScript.tStep.setErrorMessage(strMessage);
		}

		tStepEndTime = System.currentTimeMillis();
		tStepExecTime = tStepEndTime - tStepStartTime;
		CoreDriverScript.tStep.setTestStepExecutionTime(tStepExecTime);
		tCaseExecutionTime = tCaseExecutionTime + tStepExecTime;
		if (CoreDriverScript.tStep.isReusable()) {
			CoreDriverScript.tStep.getTestCase().addTestStep(
					CoreDriverScript.tStep);
		} else {
			CoreDriverScript.tStep.getTestCase().addTestStep(
					CoreDriverScript.tStep);
		}
	}

	/**
	 * Takes screen shot.
	 * 
	 * @param filePath
	 *            path of the file.
	 * @throws InterruptedException
	 *             Signals that an Interrupted exception has occurred.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void takeScreenShot(String filePath) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile, new File(filePath));
			Thread.sleep(500);
			LOGGER.debug("Screenshot taken successfully");
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Click Command: Clicks on a link, button, check box or radio button.
	 * 
	 * @param objectID
	 *        Object ID in OR properties file.
	 * @throws Exception 
	 * @throws FrameworkExceptions
	 *             
	 */
	public void click(String objectID) throws Exception {

		String objectLocator = null;
		try {
			throwTDException(objectID);
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);

			String description = "Clicking on the object: " + objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [click]");
				elm = findElement(objectID);
				elm.click();
				LOGGER.info("[click] executed on [" + objectID + "]");
				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");
				throw new Exception();
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			postTestStep("FAIL");
		} catch (WebDriverException wde) {
			if(wde.getMessage().contains("Element is not clickable at point")){				
				jsExecutor("arguments[0].scrollIntoView()", objectID);
				elm.click();
			}else{
				throw wde;
			}
		}catch (FrameworkExceptions e) {
			e.printStackTrace();
			postTestStep("FAIL");
		} catch (Exception e) {			
			e.printStackTrace();
			postTestStep("FAIL");
			throw e;
		}
	}

	/**
	 * Check Command: Checks a check box or a radio button if it's in unchecked state.
	 * 
	 * @param objectID
	 *        Object ID in OR properties file.
	 * @throws FrameworkExceptions
	 *             
	 */
	public void check(String objectID) {

		try {
			throwTDException(objectID);
			String description = "Checking the object: " + objectID;
			String objectLocator = ObjectRepository.getOR()
					.getORIdentifierValue(objectID);
			preTestStep(description);

			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [check]");
				elm = findElement(objectID);
				if (elm.isSelected()) {
					LOGGER.debug("The object in [" + objectID
							+ "]is already Checked ");
				} else {
					elm.click();
					LOGGER.debug("Checked [" + objectID + "]");
				}
				LOGGER.info("[Check] executed on [" + objectID + "]");
				postTestStep("INFO");

			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
		}

	}

	/**
	 * Type Command: Types a certain text in editable controls like TextBox.
	 * 
	 * @param objectID
	 *        Object ID in OR properties file.
	 * @throws FrameworkExceptions
	 *            
	 */
	public void clearText(String objectID) throws FrameworkExceptions {

		String objectLocator = null;
		try {
			throwTDException(objectID);
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {			
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [clear Text]");
				elm = findElement(objectID);
				elm.clear();
				LOGGER.info("[ClearText] executed on [" + objectLocator + "]");
				//				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				//				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/**
	 * Double Click Command: Double clicks on an element to open it generally.
	 * 
	 * @param objectID
	 *        Object ID in OR properties file.
	 * @throws FrameworkExceptions
	 *             
	 */
	public void doubleClick(String objectID) throws FrameworkExceptions {

		String objectLocator = null;
		try {
			throwTDException(objectID);
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {			
			String description = "Double clicking on object: " + objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator + "] to be present");
			waitForElement = waitForElementPresent(objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty("ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [DoubleClick]");
				if (Config.getConfig().getConfigProperty("BrowserType").matches("(?i)^safari.*")) {
					LOGGER.trace("Creating JavaScript DoubleClick object");
					String dblClickScript = "if(document.createEvent){var evObj = "
							+ "document.createEvent('MouseEvents');evObj.initEvent('dblclick', true, false); "
							+ "arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) "
							+ "{ arguments[0].fireEvent('ondblclick');}";
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript(dblClickScript, findElement(objectID), objectLocator);
				} else {
					actionBuilder.doubleClick(findElement(objectID)); 
					actionBuilder.perform();
				}
				LOGGER.info("DoubleClicked [" + objectID + "]");
				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/**
	 * Gets the specified attribute of an element.
	 * 
	 * @param objectID
	 *        Object ID in OR properties file.
	 * @param attribute
	 *        Attribute name to get the value.
	 * @return Retrieved attribute value.
	 * @throws FrameworkExceptions
	 *             
	 */
	public String getAttribute(String objectID, String attribute)
			throws FrameworkExceptions {
		String attributeValue = "";
		String objectLocator = null;
		try {
			throwTDException(objectID, attribute);
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {			
//			String description = "Executing getAttribute action on object:"
//					+ objectID + " of attribute: " + attribute;
//			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [getAttribute]");
				elm = findElement(objectID);
				attributeValue = findElement(objectID).getAttribute(
						attribute.trim());
				LOGGER.debug("attributevalue: " + attributeValue);
				LOGGER.info("[getAttribute] executed on [" + objectLocator
						+ "]");
//				postTestStep("PASS");
				return attributeValue;
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");
				attributeValue="Element not found";
				return attributeValue;
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/**
	 * This method will click on Ok or Cancel button of Javascript
	 * Alert/Confirmation.It will return the prompt text.
	 * 
	 * @param action
	 *        Ok or Cancel action.
	 * @return Retrieved prompt text.
	 * @throws FrameworkExceptions
	 *             
	 */
	public String getConfirmation(String action) throws FrameworkExceptions {

		try {
			String description = "Executing getConfirmation on alert with an acton:" + action;
			preTestStep(description);

			boolean alertCheck = isAlertPresent();
			if (alertCheck) {
				alert = driver.switchTo().alert();
				LOGGER.debug("Clicking on alert dialoue");
				if (action.toLowerCase().contains("ok")) {
					alert.accept();
					LOGGER.debug("Clicked on Alert OK button");
					postTestStep("PASS");
				} else if (action.toLowerCase().contains("cancel")) {
					alert.dismiss();
					LOGGER.debug("Clicked on Alert CANCEL button");
					postTestStep("PASS");
				} else {
					LOGGER.debug("Invalid input action specified for Alert!");
					postTestStep("FAIL");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

		return alert.getText();
	}

	/**
	 * Retrieves the count of items from a drop down list and list box.
	 * 
	 * @param objectID
	 *            = Object ID in the properties file
	 * @return Integer value - count of the items in the specified object
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public int getOptionCount(String objectID) throws FrameworkExceptions {
		int itemCount = 0;
		try {
			throwTDException(objectID);
			//			String description = "Executing getOptionCount action on object :"	+ objectID;
			//			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectID + "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"defaultWaitTime")));

			if (waitForElement) {
				LOGGER.trace("Element [" + objectID + "] is found");
				LOGGER.trace("Executing command: [getItemCount]");
				WebElement element = findElement(objectID);
				Select select = new Select(element);
				List<WebElement> itemValues = select.getOptions();
				itemCount = itemValues.size();
				LOGGER.info("Value retrieved [" + itemCount + "] from ["
						+ objectID + "]");
				//				postTestStep("PASS");
			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");
				throw new FrameworkExceptions("Element [" + objectID
						+ "] not found");
			}
		} catch (WebDriverException se) {
			LOGGER.error("Exception::", se);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(se);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
		return itemCount;
	}

	/**
	 * Gets the selected item count.
	 * 
	 * @param objectID
	 *            the objectID
	 * @return the count of selected items from a list box/drop down list
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public int getSelectedOptionCount(String objectID)
			throws FrameworkExceptions {
		int itemCount = 0;
		try {
			throwTDException(objectID);
			//			String description = "Executing getSelectedOptionCount action on object :"	+ objectID;
			//			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectID + "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"defaultWaitTime")));

			if (waitForElement) {
				LOGGER.trace("Element [" + objectID + "] is found");
				LOGGER.trace("Executing command: [getSelectedOptionCount]");
				WebElement element = findElement(objectID);
				Select select = new Select(element);
				List<WebElement> seletedItemList = select
						.getAllSelectedOptions();
				itemCount = seletedItemList.size();
				LOGGER.info("Value retrieved [" + itemCount + "] from ["
						+ objectID + "]");
				//				postTestStep("PASS");
			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");
				throw new FrameworkExceptions("Element [" + objectID
						+ "] not found");
			}
		} catch (WebDriverException se) {
			LOGGER.error("Exception::", se);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(se);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			//			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
		return itemCount;
	}

	/**
	 * Checks if is alert is present or not. Returns TRUE if Alert is present
	 * Else Return FALSE
	 * 
	 * @return true, if is alert present
	 */
	public boolean isAlertPresent() {
		boolean isAlertPresent = false;
		try {
			String description = "Executing isAlertPresent action";
			preTestStep(description);
			alert = driver.switchTo().alert();
			isAlertPresent = true;
			LOGGER.debug("Found Alert Box with Message : " + alert.getText());
		} catch (Exception e) {
			LOGGER.debug("Alert Box Not Found");
			isAlertPresent = false;
		}
		return isAlertPresent;
	}

	/**
	 * isDisplayed Command: gets the state of the control if it is displayed or
	 * not.
	 * 
	 * @param objectID
	 *            = Object ID in properties file
	 * @return true, if is control is displayed
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public Boolean isDisplayed(String objectID) throws FrameworkExceptions {
		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		Boolean objectState = false;
		try {
			//			String description = "Executing isDisplayed action to get the state of the object: "+ objectID;
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			LOGGER.info("Checking for [" + findElement(objectID)
					+ "] visibility");
			if (findElement(objectID) != null
					&& findElement(objectID).isDisplayed()) {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [VISIBLE]");
				objectState = true;
			} else {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [HIDDEN]");
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			throw new FrameworkExceptions(e);
		}
		return objectState;
	}

	/**
	 * isEnabled Command: gets the state of the control if it is enabled or not.
	 * 
	 * @param objectID
	 *            = Object ID in properties file
	 * @return true, if is control is displayed
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public Boolean isEnabled(String objectID) throws FrameworkExceptions {
		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		Boolean objectState = false;
		try {
			//			String description = "Executing isEnabled action to get the state of the object: "	+ objectID;
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			LOGGER.info("Checking for [" + findElement(objectID)
					+ "] whether it is enabled or not");
			if (findElement(objectID) != null
					&& findElement(objectID).isEnabled()) {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [ENABLED]");
				objectState = true;
			} else {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [DISABLED]");
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			throw new FrameworkExceptions(e);
		}
		return objectState;
	}

	/**
	 * isDisplayed Command: gets the state of the control if it is displayed or
	 * now.
	 * 
	 * @param objectID
	 *            = Object ID in properties file
	 * @return true, if is control is displayed
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public boolean isSelected(String objectID) throws FrameworkExceptions {
		try {
			ObjectRepository.getOR().getORIdentifierValue(objectID);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		boolean objectState = false;
		try {
			//			String description = "Executing isSelected action to get the state of the object: "	+ objectID;
			LOGGER.info("Checking for [" + findElement(objectID)
					+ "] selection");
			if (findElement(objectID) != null
					&& findElement(objectID).isSelected()) {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [SELECTED]");
				objectState = true;
			} else {
				LOGGER.debug("Found the element + [" + findElement(objectID)
						+ "] state as [UNSELCTED]");
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			throw new FrameworkExceptions(e);
		}
		return objectState;
	}

	/**
	 * Java Script executor.
	 * 
	 * @param value
	 *            the value
	 * @return the string
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public String jsExecutor(String value) throws FrameworkExceptions {
		String returnValue;
		LOGGER.debug("Executing java script [" + value
				+ "] using JavascriptExecutor::executeScript");
		try {
			String description = "Executing jsExecutor action to execute the script: "
					+ value;
			preTestStep(description);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			returnValue = (String) js.executeScript(value);
			LOGGER.debug("Successfully executed the java script + [" + value
					+ "]");
			postTestStep("INFO");
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		}

		return returnValue;
	}

	/**
	 * Java Script executor.
	 * 
	 * @param value
	 *            the value
	 * @param welement
	 *            the web element
	 * @return the string
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void jsExecutor(String value, WebElement elm) throws NumberFormatException, Exception {
		LOGGER.debug("Executing java script [" + value + "] using JavascriptExecutor::executeScript");
		try {
//			String objectLocator = null;
//			objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);

			String description = "Clicking on the object: " + elm.toString();
			preTestStep(description);
//			LOGGER.trace("Waiting for element [" + objectLocator + "] to be present");
//			waitForElement = waitForElementPresent(objectID, Integer.parseInt(Config.getConfig().getConfigProperty("ElementWaitTime")));
			if (elm.isDisplayed()) {
//				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [click]");
				
				LOGGER.info("[click] executed on [" + elm.toString() + "]");
				postTestStep("INFO");
				description = "Executing jsExecutor action to execute the script: "+ value;
				preTestStep(description);			
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript(value, elm);
				LOGGER.debug("Successfully executed the java script + [" + value
						+ "]");
				postTestStep("INFO");
			}
			else {
				LOGGER.error("Element [" + elm.toString() + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		}
	}
	/**
	 * Java Script executor.
	 * 
	 * @param value
	 *            the value
	 * @param welement
	 *            the web element
	 * @return the string
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void jsExecutor(String value, String objectID) throws NumberFormatException, Exception {
		LOGGER.debug("Executing java script [" + value + "] using JavascriptExecutor::executeScript");
		try {
			String objectLocator = null;
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);

			String description = "Clicking on the object: " + objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [click]");
				elm = findElement(objectID);
				LOGGER.info("[click] executed on [" + objectID + "]");
				postTestStep("INFO");
				description = "Executing jsExecutor action to execute the script: "+ value;
				preTestStep(description);			
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript(value, elm);
				LOGGER.debug("Successfully executed the java script + [" + value
						+ "]");
				postTestStep("INFO");
			}
			else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		}
	}

	/**
	 * Select option by value.
	 * 
	 * @param objectID
	 *            the object id
	 * @param value
	 *            the value
	 * @return the string
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public String selectOptionByValue(String objectID, String value)
			throws FrameworkExceptions {

		String selectedValue = null;
		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			throwTDException(objectID, value);
			String description = "Selecting option in object: "
					+ objectID + ". The value is: " + value;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");

				LOGGER.debug("Executing command: [selectOptionByValue] for value ["
						+ value + "]");
				Select dropdown = new Select(findElement(objectID));

				dropdown.selectByValue(value);

				LOGGER.info("Selected the value [ " + value
						+ "] based in 'Value'[" + "on [" + objectLocator + "]");
				selectedValue = value;
				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");
				throw new FrameworkExceptions("Element [" + objectLocator
						+ "] not found");
			}

		} catch (WebDriverException e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

		return selectedValue;
	}




	/**
	 * Select option by Text.
	 * 
	 * @param objectID
	 *            the object id
	 * @param value
	 *            the value
	 * @return the string
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public String selectOptionByText(String objectID, String value)
			throws FrameworkExceptions {

		String selectedValue = null;
		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			throwTDException(objectID, value);
			String description = "Selecting option in object: "
					+ objectID + ". The text is: " + value;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.debug("Executing command: [selectOptionByText] for value ["
						+ value + "]");
				Select dropdown = new Select(findElement(objectID));
				System.out.println(objectLocator);
				System.out.println(value);

				//				dropdown.getOptions().contains(value);
				//				actions.waitForDropdownToLoad(objectID, value, 5);
				dropdown.selectByVisibleText(value);
				LOGGER.info("Selected the value [ " + value
						+ "] based in 'Value'[" + "on [" + objectLocator + "]");
				selectedValue = value;
				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");
				throw new FrameworkExceptions("Element [" + objectLocator
						+ "] not found");
			}

		} catch (WebDriverException e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

		return selectedValue;
	}

	/**
	 * Select option by value.
	 * 
	 * @param objectID
	 *            the object id
	 * @param value
	 *            the value
	 * @return the string
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public String selectOptionByIndex(String objectID, String value)
			throws FrameworkExceptions {
		String selectedValue = null;
		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			throwTDException(objectID, value);
			String description = "Select option in object: "
					+ objectID + ". The index is: " + value;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.debug("Executing command: [selectByIndex] for value ["
						+ value + "]");
				Select dropdown = new Select(findElement(objectID));

				dropdown.selectByIndex(Integer.parseInt(value));
				LOGGER.info("Selected the value [ " + value
						+ "] based in 'Value'[" + "on [" + objectLocator + "]");
				selectedValue = value;
				postTestStep("INFO");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");
				throw new FrameworkExceptions("Element [" + objectLocator
						+ "] not found");
			}

		} catch (WebDriverException e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

		return selectedValue;
	}


	public void throwTDException(String objectID) throws FrameworkExceptions{
		if(objectID.trim().length()==0){
			preTestStep("Object name passed is empty");
			throw new FrameworkExceptions("");
		}
	}
	
	public void throwTDException(String objectID, String sTextPattern) throws FrameworkExceptions{
		if(objectID.trim().length()==0){
			preTestStep("Object name passed is empty");
			throw new FrameworkExceptions("");
		}
		if(sTextPattern==null){
			preTestStep("Column not found in the TestData file for the object: "+ objectID);
			throw new FrameworkExceptions("");
		}

	}
	/**
	 * Type Command: Types a certain text in editable controls like TextBox.
	 * 
	 * @param objectID
	 *            = Object ID in properties file
	 * @param sTextPattern
	 *            the s text pattern
	 * @throws FrameworkExceptions
	 *             the application exception
	 */
	public void type(String objectID, String sTextPattern)
			throws FrameworkExceptions {

		String objectLocator = null;
		try {
			throwTDException(objectID,sTextPattern);
			String description = "Entering text in: " + objectID
					+ " with text: " + sTextPattern;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [Type]");
				elm = findElement(objectID);
				if (elm.isDisplayed()
						&& elm.getAttribute("type").equalsIgnoreCase("file")) {
					LOGGER.debug("Cannot Execute command: [Type] on disabled or file TextBoxes");
					postTestStep("FAIL");
				} else {
					JavascriptExecutor executor = (JavascriptExecutor)driver;
					String eleID=actions.getAttribute(objectID, "id");
					executor.executeScript("document.getElementById('"+ eleID + "').focus()");
					elm.clear();
					elm.sendKeys(sTextPattern);
					executor.executeScript("document.getElementById('"+ eleID + "').blur()");
					postTestStep("INFO");
				}
				LOGGER.info("[Type] executed on [" + objectID + "]");
			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/*
	 * Check Command: Clicks on a link, button, checkbox or radio button.
	 * 
	 * @param objectID = Object ID in properties file
	 * 
	 * @throws FrameworkExceptions the application exception
	 */
	/**
	 * Un check.
	 * 
	 * @param objectID
	 *            the object id
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void unCheck(String objectID) throws FrameworkExceptions {
		String objectLocator = null;
		try {
			throwTDException(objectID);
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(
					objectID);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		try {
			String description = "Executing unCheck action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator
					+ "] to be present");
			// wait here
			waitForElement = waitForElementPresent(
					objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty(
							"ElementWaitTime")));
			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [UnCheck]");
				elm = findElement(objectID);
				if (elm.isSelected()) {
					LOGGER.debug("The object in [" + objectID
							+ "]is already Checked ");
					elm.click();
					LOGGER.debug("UnChecked [" + objectID + "]");
				} else {
					LOGGER.debug("The object in [" + objectID
							+ "]is already UnChecked ");

				}
				postTestStep("INFO");
				LOGGER.info("[UnCheck] executed on [" + objectID + "]");
			} else {
				LOGGER.error("Element [" + objectID + "] not found");
				postTestStep("FAIL");

			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/**
	 * Find an element.
	 * 
	 * @param objectID
	 *            the element
	 * @return WebElement WebDriver web element
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public WebElement findElement(String objectID) throws FrameworkExceptions {
		WebElement webElement = null;
		if (objectID.isEmpty()) {
			LOGGER.error("Element [" + objectID
					+ "] not found in Object Repository");
			throw new FrameworkExceptions("Element [" + objectID
					+ "] not found in Object Repository");
		} else {
			try {
				String locator = ObjectRepository.getOR().getORIdentifier(
						objectID);
				String locatorValue = ObjectRepository.getOR()
						.getORIdentifierValue(objectID);
				if (locatorValue != null && locator.equalsIgnoreCase("xpath")) {
					try {
						LOGGER.trace("WebDriver : User has specified xpath");
						webElement = driver.findElement(By.xpath(locatorValue));
						LOGGER.trace("WebDriver : Found the element using xpath");
					} catch (WebDriverException we) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");						
						throw new FrameworkExceptions(we);	
					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("id")) {
					try {
						LOGGER.trace("WebDriver : User has specified Id");
						webElement = driver.findElement(By.id(locatorValue));
						LOGGER.trace("WebDriver : Found the element using ID");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("name")) {
					try {
						LOGGER.trace("User has specified Name");
						// locatorValue='"'+locatorValue+'"';
						webElement = driver.findElement(By.name(locatorValue));
						LOGGER.trace("WebDriver : Found the element using Name");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("css")) {
					try {
						LOGGER.trace("User has specified css");
						webElement = driver.findElement(By
								.cssSelector(locatorValue));
						LOGGER.trace("WebDriver : Found the element using css");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("link")) {
					try {
						LOGGER.trace("User has specified link");
						webElement = driver.findElement(By
								.linkText(locatorValue));
						LOGGER.trace("WebDriver : Found the element using link");
					} catch (Exception e) {
						LOGGER.trace("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("className")) {
					try {
						LOGGER.trace("WebDriver : User has specified ClassName");
						webElement = driver.findElement(By.className(locatorValue));
						LOGGER.trace("WebDriver : Found the element using ClassName");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				}else if (locatorValue != null
						&& locator.equalsIgnoreCase("tagName")) {
					try {
						LOGGER.trace("WebDriver : User has specified TagName");
						webElement = driver.findElement(By.tagName(locatorValue));
						LOGGER.trace("WebDriver : Found the element using TagName");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				}else if (locatorValue != null
						&& locator.equalsIgnoreCase("partialLink")) {
					try {
						LOGGER.trace("WebDriver : User has specified PartialLink");
						webElement = driver.findElement(By.partialLinkText(locatorValue));
						LOGGER.trace("WebDriver : Found the element using PartialLink");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				} else {

					// webElement =findElementSub(locator,locator);

				}
				if (webElement == null) {
					LOGGER.debug("Element [" + locatorValue + "]"
							+ " Not Found!");
				}
			} catch (WebDriverException we) {
				LOGGER.debug("Exception::", we);
				throw new FrameworkExceptions(we);
			} 
			catch (Exception e) {
				LOGGER.debug(e);
				throw new FrameworkExceptions("Object not found "+ objectID +" in the application");
			}
		}
		return webElement;
	}

	/**
	 * Find all elements.
	 * 
	 * @param objectID
	 *            the element
	 * @return List<WebElement> WebDriver web elements
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public List<WebElement> findElements(String objectID)
			throws FrameworkExceptions {
		List<WebElement> webElements = null;
		if (objectID.isEmpty()) {
			LOGGER.error("Element [" + objectID
					+ "] not found in Object Repository");
			throw new FrameworkExceptions("Element [" + objectID
					+ "] not found in Object Repository");
		} else {
			try {
				String locator = ObjectRepository.getOR().getORIdentifier(
						objectID);
				String locatorValue = ObjectRepository.getOR()
						.getORIdentifierValue(objectID);

				if (locatorValue != null && locator.equalsIgnoreCase("xpath")) {
					try {
						LOGGER.trace("WebDriver : User has specified xpath");
						webElements = driver.findElements(By
								.xpath(locatorValue));
						LOGGER.trace("WebDriver : Found the element using xpath");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("id")) {
					try {
						LOGGER.trace("WebDriver : User has specified Id");
						webElements = driver.findElements(By.id(locatorValue));
						LOGGER.trace("WebDriver : Found the element using ID");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");
					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("name")) {
					try {
						LOGGER.trace("User has specified Name");
						webElements = driver
								.findElements(By.name(locatorValue));
						LOGGER.trace("WebDriver : Found the element using Name");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("css")) {
					try {
						LOGGER.trace("User has specified css");
						webElements = driver.findElements(By
								.cssSelector(locatorValue));
						LOGGER.trace("WebDriver : Found the element using css");
					} catch (Exception e) {
						LOGGER.error("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("link")) {
					try {
						LOGGER.trace("User has specified link");
						webElements = driver.findElements(By
								.linkText(locatorValue));
						LOGGER.trace("WebDriver : Found the element using link");
					} catch (Exception e) {
						LOGGER.trace("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("className")) {
					try {
						LOGGER.trace("User has specified className");
						webElements = driver.findElements(By
								.className(locatorValue));
						LOGGER.trace("WebDriver : Found the element using className");
					} catch (Exception e) {
						LOGGER.trace("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("tagName")) {
					try {
						LOGGER.trace("User has specified tagName");
						webElements = driver.findElements(By
								.tagName(locatorValue));
						LOGGER.trace("WebDriver : Found the element using tagName");
					} catch (Exception e) {
						LOGGER.trace("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else if (locatorValue != null
						&& locator.equalsIgnoreCase("partialLink")) {
					try {
						LOGGER.trace("User has specified partialLink");
						webElements = driver.findElements(By
								.partialLinkText(locatorValue));
						LOGGER.trace("WebDriver : Found the element using partialLink");
					} catch (Exception e) {
						LOGGER.trace("Element [" + locatorValue + "]"
								+ "not found");

					}
				} else {

					// webElement =findElementSub(locator,locator);

				}

			} catch (WebDriverException we) {
				LOGGER.debug("Exception::", we);
				throw new FrameworkExceptions(we);
			} catch (Exception e) {
				LOGGER.debug(e);
			}
		}
		return webElements;
	}

	/**
	 * Wait for element present.
	 * 
	 * @param objectID
	 *            the object id
	 * @param waitTime
	 *            the wait time
	 * @return true/false if element is present or not
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public boolean waitForElementPresent(String objectID, int waitTime)
			throws FrameworkExceptions {
		boolean waitForElement = false;
		try {
			String objectLocator = ObjectRepository.getOR()
					.getORIdentifierValue(objectID);
			//			waitForElement = false;
			LOGGER.debug("Executing command [waitForElementPresent] with timeout ["
					+ waitTime + "], Element [" + objectLocator + "]");

			long startTime = Calendar.getInstance().getTimeInMillis();
			long timeDiff = 0;
			int iterationCnt = 1;

			elm = findElement(objectID);
			
			
			do {
				LOGGER.trace("Current attempt #" + iterationCnt
						+ " to find element [" + objectID + "]");
				try {

					if (elm != null && elm.isDisplayed()) {
						LOGGER.info("Found element [" + objectID + "] after ["
								+ iterationCnt + "] iterations");
						waitForElement = true;
						return true;
					}

				} catch (Exception e) {
					LOGGER.trace("Element [" + objectID + "] not found");
				} finally {
					long curTime = Calendar.getInstance().getTimeInMillis();
					timeDiff = curTime - startTime;
				}
			} while (timeDiff <= waitTime);

			LOGGER.debug("Element [" + objectID
					+ "] not found after waiting for [" + waitTime + "ms]!");
			throw new FrameworkExceptions("Element" + objectID + " not found");
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.debug(e);
		}
		return waitForElement;
	}

	/**
	 * Wait for frame present.
	 * 
	 * @param objectID
	 *            the object id
	 * @param waitTime
	 *            the wait time
	 * @return true/false if element is present or not
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public boolean waitForFramePresent(String objectID, int waitTime)
			throws FrameworkExceptions {
		boolean waitForFrame = false;
		try {
			String objectLocator = ObjectRepository.getOR()
					.getORIdentifierValue(objectID);

			LOGGER.debug("Executing command [waitForFramePresent] with timeout ["
					+ waitTime + "], Frame [" + objectLocator + "]");

			long startTime = Calendar.getInstance().getTimeInMillis();
			long timeDiff = 0;
			int iterationCnt = 1;

			elm = findElement(objectID);
			do {
				LOGGER.trace("Current attempt #" + iterationCnt
						+ " to find frame [" + objectID + "]");
				try {

					if (elm != null && elm.isDisplayed()) {
						LOGGER.info("Found frame [" + objectID + "] after ["
								+ iterationCnt + "] iterations");
						waitForFrame = true;
						return true;
					}

				} catch (Exception e) {
					LOGGER.trace("Frame [" + objectID + "] not found");
				} finally {
					long curTime = Calendar.getInstance().getTimeInMillis();
					timeDiff = curTime - startTime;
				}
			} while (timeDiff <= waitTime);

			LOGGER.debug("Frame [" + objectID
					+ "] not found after waiting for [" + waitTime + "ms]!");
			throw new FrameworkExceptions("Frame" + objectID + " not found");
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.debug(e);
		}
		return waitForFrame;
	}

	/**
	 * Check for the Text pattern till the specified timeout period.
	 * 
	 * @param objPattern
	 *            the obj pattern
	 * @param waitTime
	 *            the wait time parameter (msec)
	 * @return String pattern waiting for
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public String waitForText(String objPattern, int waitTime)
			throws FrameworkExceptions {
		int timeOut = waitTime / 1000;
		LOGGER.trace("Executing command [waitForTextPresent] with timeout ["
				+ timeOut + "], text pattern [" + objPattern + "]");
		String description = "Executing waitForText action of text pattern:"
				+ objPattern + " with wait time is: " + waitTime;
		preTestStep(description);
		try {
			for (int i = 0; i < timeOut; i++) {
				LOGGER.debug("Current attempt #" + i
						+ " to find text pattern [" + objPattern + "]");
				if (driver.findElement(By.tagName("body")).getText()
						.contains(objPattern)) {
					LOGGER.info("Found text pattern [" + objPattern
							+ "] after [" + i + "] iterations");
					postTestStep("PASS");
					return objPattern;
				}
				Thread.sleep(1000);
			}
			LOGGER.error("text pattern [" + objPattern
					+ "] not found after waiting for [" + timeOut + "ms]!");
			throw new FrameworkExceptions("text pattern [" + objPattern
					+ "] not found after waiting for [" + timeOut + "ms]!");
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

	}

	/**
	 * Wait for Window Present.
	 * 
	 * @param windowIdTitle
	 *            the window id
	 * @param waitTime
	 *            waitTime
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void waitForWindowPresent(String windowIdTitle, int waitTime)
			throws FrameworkExceptions {

		try {
			String description = "Executing waitForWindowPresent action of window title:"
					+ windowIdTitle + " with wait time is: " + waitTime;
			preTestStep(description);
			int startTime = 0;
			int timeOut = waitTime / 1000;
			int windowId = -1;
			String windowTitle = "";
			try {
				windowId = Integer.parseInt(windowIdTitle);
			} catch (Exception e) {
				windowId = -1;
				windowTitle = windowIdTitle;
			}

			boolean bFoundWindow = false;

			do {

				LOGGER.trace("Current attempt #" + startTime
						+ " to find  window");
				LOGGER.trace("Waiting For Window Present ..");

				Set<String> windowHandles = driver.getWindowHandles();
				Object[] handles = windowHandles.toArray();

				if ((handles.length - 1) == windowId) {
					bFoundWindow = true;
					LOGGER.debug("Found the window after [" + startTime
							+ "] iterations");
					postTestStep("PASS");
					break;
				} else {
					for (int i = 0; i < handles.length; i++) {
						String title = driver.switchTo()
								.window((String) handles[i]).getTitle();
						if (title.compareToIgnoreCase(windowTitle) == 0) {
							bFoundWindow = true;
							LOGGER.debug("Found the window after [" + startTime
									+ "] iterations");
							postTestStep("PASS");
							break;
						}
					}
				}
				Thread.sleep(500);
				startTime++;

			} while (startTime <= timeOut);

			if (!bFoundWindow) {
				throw new FrameworkExceptions("[Window" + windowIdTitle
						+ "] not found/displayed after waiting for [" + timeOut
						+ "sec]!");
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
			postTestStep("FAIL");
			throw new FrameworkExceptions(we);
		} catch (RuntimeException e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		} catch (InterruptedException ie) {
			LOGGER.error("Exception::", ie);
			postTestStep("FAIL");
			throw new FrameworkExceptions(ie);
		}
	}

	/**
	 * Uses Robot library to release the specified key. If ObjectId is
	 * specified, it uses Selenium library to focus on the object before Robot
	 * library is used generate the key event
	 * 
	 * @param keyCode
	 *            Key to press. For special keys, use the following codes: Alt:
	 *            {[ALT]}, Control: {[CTRL]}, Shift: {[SHIFT]}, Esc: {[ESC]}
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void keyRelease(String keyCode) throws FrameworkExceptions {
		LOGGER.debug("Executing command: [keyRelease] with value [" + keyCode
				+ "]");
		String description = "Executing keyRelease action of value is:"
				+ keyCode;
		preTestStep(description);
		// Let us create a little delay
		//
		robot.delay(KEY_DELAY_MS);

		// check the keycode. should be specified as {[keycode]} and if
		// matches
		// press the key else raise exception
		if (keyCode.compareToIgnoreCase("{[ALT]}") == 0) {
			LOGGER.trace("Release Alt KEY");
			robot.keyRelease(KeyEvent.VK_ALT);
			LOGGER.debug("Released Alt KEY");
		} else if (keyCode.compareToIgnoreCase("{[CTRL]}") == 0) {
			LOGGER.trace("Release Ctrl KEY");
			robot.keyRelease(KeyEvent.VK_CONTROL);
			LOGGER.debug("Released Ctrl KEY");
		} else if (keyCode.compareToIgnoreCase("{[CAPSLOCK]}") == 0) {
			LOGGER.trace("Release CAPSLOCK KEY");
			// Generating key release event for writing the Keyboard letter
			// robot.keyPress(KeyEvent.VK_CAPS_LOCK);
			Toolkit.getDefaultToolkit().setLockingKeyState(
					KeyEvent.VK_CAPS_LOCK, true);
			robot.delay(KEY_DELAY_MS);
			// robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
			LOGGER.debug("Released CAPSLOCK KEY");
		} else if (keyCode.compareToIgnoreCase("{[SHIFT]}") == 0) {
			LOGGER.trace("Release Shift KEY");
			// Generating key release event for writing the Keyboard letter
			robot.keyRelease(KeyEvent.VK_SHIFT);
			LOGGER.debug("Released Shift KEY");
		} else if (keyCode.compareToIgnoreCase("{[ESC]}") == 0) {
			LOGGER.trace("Release Escape KEY");
			// Generating key release event for writing the Keyboard letter
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			LOGGER.debug("Released Escape KEY");
		} else if (keyCode.compareToIgnoreCase("{[SPACE]}") == 0) {
			LOGGER.debug("Release SPACE KEY");
			robot.keyRelease(KeyEvent.VK_SPACE);
			LOGGER.debug("Released Space KEY");
		} else if (keyCode.compareToIgnoreCase("{[END]}") == 0) {
			LOGGER.debug("Release End KEY");
			robot.keyRelease(KeyEvent.VK_END);
			LOGGER.debug("Released End KEY");
		} else {
			LOGGER.error("Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
			throw new FrameworkExceptions(
					"Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
		}

	}



	/**
	 * Back.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void back() throws FrameworkExceptions {
		try {
			String description = "Executing back action";
			preTestStep(description);
			LOGGER.debug("Navigating to the previous history page");
			driver.navigate().back();
			LOGGER.debug("Executed back action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to navigate to the previous history page. The excetion is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to navigate to the previous history page");
		}
	}

	/**
	 * Forward.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void forward() throws FrameworkExceptions {
		try {
			String description = "Executing forward action";
			preTestStep(description);
			LOGGER.debug("Navigating to the forward history page");
			driver.navigate().forward();
			LOGGER.debug("Executed forward action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to navigate to the forward history page. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to navigate to the forward history page");
		}
	}

	/**
	 * Close.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void close() throws FrameworkExceptions {
		try {
			String description = "Executing close action";
			preTestStep(description);
			LOGGER.debug("Clolsing the current focusing window/page");
			driver.close();
			LOGGER.debug("Executed close action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to close the window. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to close the window.");
		}
	}

	/**
	 * Quit.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void quit() throws FrameworkExceptions {
		try {
			String description = "Executing quit action";
			preTestStep(description);
			LOGGER.debug("Clolsing the webdriver instance");
			driver.quit();
			LOGGER.debug("Executed quit action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to quit the window. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to quit the window.");
		}
	}

	/**
	 * Gets the select option by index.
	 * 
	 * @param objectID
	 *            the element name
	 * @return the select option by index
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public String getSelectOptionByIndex(String objectID)
			throws FrameworkExceptions {
		try {
			String description = "Executing getSelectOptionByIndex action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.debug("Getting the index of selected list option");
			Select select = new Select(findElement(objectID));
			String index = select.getAllSelectedOptions().get(0)
					.getAttribute("index");
			LOGGER.debug("Executed getSelecdtOptionByIndex action");
			postTestStep("PASS");
			return index;
		} catch (Exception e) {
			LOGGER.error("Unable to get the selected item from the object: "
					+ objectID + ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the selected item from the object: "
							+ objectID + ". The exception is: "
							+ e.getMessage());
		}
	}

	/**
	 * Gets the select option by label.
	 * 
	 * @param objectID
	 *            the element name
	 * @return the select option by label
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public String getSelectOptionByLabel(String objectID)
			throws FrameworkExceptions {
		try {
			String description = "Executing getSelectOptionByLabel action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.debug("Getting the text of selected list option");
			Select select = new Select(findElement(objectID));
			String value = select.getAllSelectedOptions().get(0).getText();
			LOGGER.debug("Executed getSelectOptionByLabel action");
			postTestStep("PASS");
			return value;
		} catch (Exception e) {
			LOGGER.error("Unable to get the selected item from the object: "
					+ objectID + ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the selected item from the object: "
							+ objectID + ". The exception is: "
							+ e.getMessage());
		}
	}

	/**
	 * Gets the select option by value.
	 * 
	 * @param objectID
	 *            the element name
	 * @return the select option by value
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public String getSelectOptionByValue(String objectID)
			throws FrameworkExceptions {
		try {
			String description = "Executing getSelectOptionByValue action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.debug("Getting the value of selected list option");
			Select select = new Select(findElement(objectID));
			String value = select.getAllSelectedOptions().get(0)
					.getAttribute("value");
			LOGGER.debug("Executed getSelectOptionByValue action");
			return value;
		} catch (Exception e) {
			LOGGER.error("Unable to get the selected item from the object: "
					+ objectID + ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the selected item from the object: "
							+ objectID + ". The exception is: "
							+ e.getMessage());
		}

	}

	/**
	 * Gets the text.
	 * 
	 * @param objectID
	 *            the element name
	 * @return the text
	 * @throws FrameworkExceptions
	 *             the framework exception
	 */
	public String getText(String objectID) throws FrameworkExceptions {
		String textContent = "";
		try {
//			String description = "Executing getText action on object: "
//					+ objectID;
//			preTestStep(description);
			LOGGER.debug("Getting the text from an element");
			textContent = findElement(objectID).getText();
			LOGGER.debug("Executed getText action");
//			postTestStep("INFO");
		} catch (Exception e) {
			LOGGER.error("Unable to get the text from the object: " + objectID
					+ ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the text from the object: " + objectID
					+ ". The exception is: " + e.getMessage());
		}
		return textContent;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public String getTitle() throws FrameworkExceptions {
		String title = "";
		try {
			String description = "Executing getTitle action";
			preTestStep(description);
			LOGGER.debug("Getting the browser title");
			title = driver.getTitle();
			LOGGER.debug("Executed getTitle action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to get the window title. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the window title. The exception is: "
							+ e.getMessage());
		}
		return title;

	}

	/**
	 * Gets the value.
	 * 
	 * @param objectID
	 *            the element name
	 * @return the value
	 * @throws FrameworkExceptions
	 *             the framework exception
	 */
	public String getValue(String objectID) throws FrameworkExceptions {
		String value = "";
		try {
			String description = "Executing getValue action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.debug("Getting the value from an element " + objectID);
			value = findElement(objectID).getAttribute("value");
			LOGGER.debug("Executed getValue action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to get the value from the object:" + objectID
					+ ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to get the value from the object:" + objectID
					+ ". The exception is: " + e.getMessage());
		}
		return value;
	}




	/**
	 * Refresh.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void refresh() throws FrameworkExceptions {
		try {
			String description = "Executing refresh action";
			preTestStep(description);
			LOGGER.debug("Refreshing the browser");
			driver.navigate().refresh();
			LOGGER.debug("Executed refresh action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to refresh the browser. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to refresh the browser. The exception is: "
							+ e.getMessage());
		}
	}

	/**
	 * Switch to frame.
	 * 
	 * @param frameName
	 *            the frame name
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void switchToFrame(String frameName) throws FrameworkExceptions {
		try {
			String description = "Executing switchToFrame action of frame: "
					+ frameName;
			preTestStep(description);
			LOGGER.debug("Switching the frame to " + frameName);
			driver.switchTo().frame(frameName);
			LOGGER.debug("Executed switchToFrame action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to switchToFrame of frame name:" + frameName
					+ ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to switchToFrame of frame name:" + frameName
					+ ". The exception is: " + e.getMessage());
		}
	}

	public void switchToFrame(int frameIndex) throws FrameworkExceptions {
		try {
			String description = "Executing switchToFrame action of frame: "
					+ frameIndex;
			preTestStep(description);
			LOGGER.debug("Switching the frame to " + frameIndex);
			Thread.sleep(5000);
			driver.switchTo().frame(frameIndex);
			LOGGER.debug("Executed switchToFrame action");
			postTestStep("INFO");
		} catch (Exception e) {
			LOGGER.error("Unable to switchToFrame of frame index:" + frameIndex
					+ ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to switchToFrame of frame index:" + frameIndex
					+ ". The exception is: " + e.getMessage());
		}
	}
	/**
	 * Switch to default content.
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void switchToDefaultContent() throws FrameworkExceptions {
		try {
			String description = "Executing switchToDefaultContent action";
			preTestStep(description);
			LOGGER.debug("Switching to the default content");
			driver.switchTo().defaultContent();
			LOGGER.debug("Executed switchToDefaultContent action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to switch to the default content. The exception is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to switch to the default content. The exception is: "
							+ e.getMessage());
		}
	}

	/**
	 * Select window.
	 * 
	 * @param windowTitle
	 *            the window title
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void selectWindow(String windowTitle) throws FrameworkExceptions {
		try {
			LOGGER.debug("Selecting the window of title: " + windowTitle);
			String description = "Executing selectWindow action of window title: "
					+ windowTitle;
			preTestStep(description);
			String winTitle = "";
			int winId = -1;
			Set<String> windowHandles = driver.getWindowHandles();
			Object[] handles = windowHandles.toArray();

			try {
				winId = Integer.parseInt(windowTitle);
			} catch (Exception e) {
				winId = -1;
				winTitle = windowTitle;
			}
			if (winId > 0) {
				driver.switchTo().window((String) handles[winId]);
				postTestStep("PASS");
			} else {
				//				boolean bFoundWindow = false;
				for (int i = 0; i < handles.length; i++) {
					winTitle = driver.switchTo().window((String) handles[i])
							.getTitle();
					if (winTitle.toLowerCase().contains(
							windowTitle.toLowerCase())) {
						driver.switchTo().window((String) handles[i]);
						//						bFoundWindow = true;
						postTestStep("PASS");
						break;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Unable to select the window of title:" + windowTitle
					+ ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to select the window of title:" + windowTitle
					+ ". The exception is: " + e.getMessage());
		}
	}

	/**
	 * Unselect all list options.
	 * 
	 * @param objectID
	 *            the element name
	 * @throws FrameworkExceptions
	 *             the framework exception
	 */
	public void unselectAllListOptions(String objectID)
			throws FrameworkExceptions {
		try {
			String description = "Executing unselectAllListOptions action on object: "
					+ objectID;
			preTestStep(description);
			LOGGER.debug("Deselecting all list options of: " + objectID);
			Select select = new Select(findElement(objectID));
			select.deselectAll();
			LOGGER.debug("Executed unselectAllListOptions action");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to unselect all list options of object:"
					+ objectID + ". The exception is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions(
					"Unable to unselect all list options of object:" + objectID
					+ ". The exception is: " + e.getMessage());
		}
	}



	/**
	 * Drag and Drop.
	 * 
	 * @param source
	 *            objectID and target objectID the object id
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */

	public void dragAndDrop(String sourceObjectID, String targetObjectID) throws FrameworkExceptions {
		WebElement sourceElement = findElement(sourceObjectID);
		WebElement targetElement = findElement(targetObjectID);
		String description = "Executing drag and drop action on object: " + sourceObjectID + "to target location"
				+ targetObjectID;
		preTestStep(description);
		try {
			LOGGER.debug("Creating WebDriver Actions object");
			actionBuilder = new Actions(driver);
			LOGGER.debug("Executing mouse left click command");
			Action dragAndDrop = actionBuilder.clickAndHold(sourceElement).moveToElement(targetElement)
					.release(targetElement).build();

			dragAndDrop.perform();
			LOGGER.debug("Executed drag and drop action on object: " + sourceObjectID + "to target location"
					+ targetObjectID);
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Unable to exeucte drag and drop action on object: " + sourceObjectID + "to target location"
					+ targetObjectID + ". Excepton is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to exeucte drag and drop action on object: " + sourceObjectID
					+ "to target location" + targetObjectID);
		}
	}

	public String generateRandomString()
	{
		robot.mouseMove(0, 0);
		StringBuilder sb = null ;
		try{
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 7; i++) {
				char c = chars[random.nextInt(chars.length)];
				sb.append(c);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return(sb.toString());
	}

	public  void  selectByPartOfVisibleText(String value, WebElement element) 
	{
		try{
			List<WebElement> optionElements = element.findElements(By.tagName("option"));
			Select dp_Recorder = new Select(element);
			for (WebElement optionElement: optionElements) {
				System.out.println("$"+optionElement.getText().toLowerCase()+"$");
				System.out.println("$"+value.toLowerCase()+"$");
				if (optionElement.getText().toLowerCase().equalsIgnoreCase(value.toLowerCase())) {
					String optionIndex = optionElement.getAttribute("index");            
					dp_Recorder.selectByIndex(Integer.parseInt(optionIndex));
					Reporter.log("Value " +value+ " is selected from the dropdown");		            
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Unable to select item from the dropdown "+value);
			Reporter.log("Unable to select item from the dropdown "+value);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public boolean isLoaded(final String objectID) throws Error {
		String objectLocator = null;

		boolean blnStatus = false;
		try
		{
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(1,  TimeUnit.SECONDS);
			wait.withTimeout(5, TimeUnit.SECONDS);
			Function<WebDriver, Boolean> function = new Function<WebDriver,Boolean>()
					{
				public Boolean apply(WebDriver driver) 
				{
					boolean blnFound = false;
					try{
						WebElement element = findElement(objectID);            			   
						if(element!= null && element.isEnabled() && element.isDisplayed())
						{
							blnFound = true;
						}
					}
					catch(NoSuchElementException | FrameworkExceptions nse){ }
					return blnFound;
				}
					};

					blnStatus = wait.until(function);
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
		}
		return blnStatus;

	}



	public void selectByIgnoringSpecialCharacters(String objectID, String value)
	{
		try{
			Select dp_Recorder = new Select(actions.findElement(objectID));
			List<WebElement> optionElements = dp_Recorder.getOptions();

			for (WebElement optionElement: optionElements) {
				String option = optionElement.getText().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
				String partvalue = value.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
				if (option.contains(partvalue)) {
					String optionIndex = optionElement.getAttribute("index");            
					dp_Recorder.selectByIndex(Integer.parseInt(optionIndex));
					Reporter.log("Value " +value+ " is selected from the dropdown");		            
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Unable to select item from the dropdown "+value);
			Reporter.log("Unable to select item from the dropdown "+value);
			e.printStackTrace();
		}
	}

	public Boolean waitForDropdownToLoad(final String objectID, final String textToBePresent, int waitTime) {
		Boolean waitForElement = false;
		try {
			Thread.sleep(2000);
			WebElement elm = findElement(objectID);
			if(elm != null && elm.isDisplayed() && elm.getTagName().equalsIgnoreCase("select")) {
				waitForElement = (Boolean)new WebDriverWait(driver, waitTime).
						until(new ExpectedCondition<Boolean>() {
							@Override
							public Boolean apply(WebDriver webDriver) {
								Boolean blnWait = false;
								try {
									//blnWait = actions.findElements(objectID).get(0).getText().split("\n").length > 1;
									blnWait = (Arrays.asList(actions.findElements(objectID).get(0).getText().split("\n"))).contains(textToBePresent);
								} catch (FrameworkExceptions frameworkExceptions) {
									frameworkExceptions.printStackTrace();
								}
								return blnWait;
							}
						});
			} else {
				LOGGER.error("waitForDropdownToLoad : Check the parameters");
			}
		} catch (WebDriverException we) {
			LOGGER.error("Exception::", we);
		} catch (Exception e) {
			LOGGER.debug(e);
		}
		return waitForElement;
	}

	public void jsSelectOption(String objectID, String Value, String Property) {
		String jsCommand;
		try {
			waitForDropdownToLoad(objectID,Value,Integer.parseInt(Config.getConfig().getConfigProperty("ElementWaitTime")));
			objectID =  ObjectRepository.getOR().getORIdentifierValue(objectID);
			if (Property.equalsIgnoreCase("value"))
			{
				jsCommand = "var sel = document.evaluate(\"" + objectID+ "\", document, null, XPathResult.ANY_TYPE, null ); " +
						"var sel1 = sel.iterateNext();var opts = sel1.options; " +
						"for(var opt, j = 0; opt = opts[j]; j++) {" +
						" if(opt.value == \"" + Value + "\") {sel1.selectedIndex = j;" +
						"if(typeof(sel1[\"onchange\"]  == \"function\")) {sel1[\"onchange\"].call(sel1);}break;}}";
			}
			else
			{
				jsCommand = "var sel = document.evaluate(\"" + objectID+ "\", document, null, XPathResult.ANY_TYPE, null ); " +
						"var sel1 = sel.iterateNext();var opts = sel1.options; " +
						"for(var opt, j = 0; opt = opts[j]; j++) {" +
						" if(opt.text == \"" + Value + "\") {sel1.selectedIndex = j;" +
						"if(typeof(sel1[\"onchange\"]  == \"function\")) {sel1[\"onchange\"].call(sel1);}break;}}";
			}

			jsExecutor(jsCommand);

		} catch (Exception e) {
			LOGGER.trace("unable to select options from object "+objectID);}
	}



	public  boolean WaitTillRender()
	{
		String objectID1 = "Spinner";
		String objectID2 = "Loading";
		WebElement elementProcessing = null;
		WebElement eleisRetriving = null;

		boolean blnStatus = false;
		try
		{

			elementProcessing = findElement(objectID1);
			eleisRetriving = findElement(objectID2);

			do {
				LOGGER.info("element is still loading.....!");
			}while(eleisRetriving.isDisplayed());

			String strVisibility = elementProcessing.getCssValue("visibility");
			do {
				strVisibility = elementProcessing.getCssValue("visibility");
				blnStatus = strVisibility.equalsIgnoreCase("visible");
			} while (blnStatus);

		}
		catch(Exception e)
		{
			LOGGER.trace("unable to identify the Lazy Loader");
		}
		return blnStatus;

	}

	public void FoucsOnElement(String ObjectID,String LocatorType) throws FrameworkExceptions
	{			
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		String eleID=actions.getAttribute(ObjectID, "id");
		executor.executeScript("document.getElementById('"+ eleID + "').focus()");
	}

}