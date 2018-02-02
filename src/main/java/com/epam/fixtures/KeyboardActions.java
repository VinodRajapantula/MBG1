package com.epam.fixtures;

//import io.appium.java_client.AppiumDriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.ObjectRepository;
import com.epam.utils.report.testsuite.TestStep;
/**
 * The Class WebActions.
 */
public class KeyboardActions extends CoreDriverScript {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(KeyboardActions.class);

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
	
	
	/** Object for Alert Class*/
	public Alert alert = null;

	/**
	 * Instantiates a new Automation command fixtures.
	 * 
	 */
	public KeyboardActions(WebDriver driver) throws Exception {
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
		CoreDriverScript.tStep
				.setActionDescription(CoreDriverScript.stepDescription + " : "
						+ CoreDriverScript.inputData);
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
		//if exception this should fail add condition later

		if (null != CoreDriverScript.test_step_result
				&& CoreDriverScript.test_step_result.equalsIgnoreCase("FAIL")) {
			try {
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
				takeScreenShot(CoreDriverScript.screenShotFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			LOGGER.info("Error screenshot collected");
			File file = new File(CoreDriverScript.screenShotFile);
			CoreDriverScript.tStep.setImageName(file.getPath());
			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("FAIL");
			CoreDriverScript.tcaseflag = true;
			CoreDriverScript.scenarioStatus = "false";
			CoreDriverScript.isTestSenario = false;
			CoreDriverScript.tStep
					.setErrorMessage("Error message of the failure condition");
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
					takeScreenShot(CoreDriverScript.screenShotFile);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				LOGGER.info("Error screenshot collected");
				File file = new File(CoreDriverScript.screenShotFile);
				CoreDriverScript.tStep.setImageName(file.getPath());
			}
			CoreDriverScript.testStatus = CoreDriverScript.test_step_result;
			CoreDriverScript.tStep.setResult("PASS");
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
	 * Uses Robot library to press the specified key on active window at the
	 * location of the cursor. If ObjectId is specified, it uses Selenium
	 * library to focus on the object before Robot library is used generate the
	 * key events
	 * 
	 * @param keyCode
	 *            Key to press. For special keys, use the following codes: Tab:
	 *            {[TAB]}, Enter: {[Enter]}, Esc: {[ESC]}, Space: {[SPACE]},
	 *            CAPS LOCK: {[CAPSLOCK]}, Alt: {[ALT]}, Control: {[CTRL]},
	 *            Shift: {[SHIFT]}, Function 4: {[F4]} Other keys supported
	 *            include: a-z, A-Z, 0-9, ;, /, <, :, >, @, {, $, }, (, #, ), &,
	 *            *, !, \, -, +
	 * @return boolean true/false
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public boolean keyPress(String keyCode) throws FrameworkExceptions {
		LOGGER.debug("Executing command: [keyPress] with value [" + keyCode
				+ "]");

		try {
			String description = "Executing keyPress action of value is:"
					+ keyCode;
			preTestStep(description);
			int i = 0;
			while (i < keyCode.length()) {
				char char1 = keyCode.charAt(i);
				char char2 = '\u0000';
				if ((i + 1) < keyCode.length()) {
					char2 = keyCode.charAt(i + 1);
				}
				if (char1 == '{' && char2 == '[') {

					if (((i + 2) >= keyCode.length())
							|| !(keyCode.substring(i + 2).indexOf("]}") > 0)) {
						LOGGER.error("Please specify correct format for keys to be pressed using KeyPress action.");
						throw new FrameworkExceptions(
								"Please specify correct format for keys to be pressed using KeyPress action.");
					}

					int k = i + 2;
					String subStr = "";
					boolean flag = true;
					while (flag) {
						subStr += keyCode.charAt(k);
						if (((k + 2) < keyCode.length())
								&& (keyCode.charAt(k + 1) == ']' && keyCode
										.charAt(k + 2) == '}')) {
							flag = false;
						} else if ((k + 2) >= keyCode.length()) {
							LOGGER.error("Please specify correct format for keys to be pressed using KeyPress action.");
							throw new FrameworkExceptions(
									"Please specify correct format for keys to be pressed using KeyPress action.");
						}
						k++;
					}

					LOGGER.trace("Special character [" + subStr + "]");
					if (subStr.compareToIgnoreCase("TAB") == 0) {
						LOGGER.debug("User specififed TAB KEY to click");
						robot.keyPress(KeyEvent.VK_TAB);
						LOGGER.debug("Clicked TAB KEY");
					} else if (subStr.compareToIgnoreCase("CAPSLOCK") == 0) {
						LOGGER.debug("User specififed CAPSLOCK KEY to click");
						Toolkit.getDefaultToolkit().setLockingKeyState(
								KeyEvent.VK_CAPS_LOCK, true);
						robot.delay(KEY_DELAY_MS);
						LOGGER.debug("Clicked CAPSLOCK KEY");
					} else if (subStr.compareToIgnoreCase("ENTER") == 0) {
						LOGGER.debug("User specififed ENTER KEY to click");
						robot.keyPress(KeyEvent.VK_ENTER);
						LOGGER.debug("Clicked ENTER KEY");
						break;
					} else if (subStr.compareToIgnoreCase("ESC") == 0) {
						LOGGER.debug("User specififed ESC KEY to click");
						robot.keyPress(KeyEvent.VK_ESCAPE);
						LOGGER.debug("Clicked ESC KEY");
						break;
					} else if (subStr.compareToIgnoreCase("SPACE") == 0) {
						LOGGER.debug("User specififed SPACE KEY to click");
						robot.keyPress(KeyEvent.VK_SPACE);
						LOGGER.debug("Pressed Space KEY");
						// break;
					} else if (subStr.compareToIgnoreCase("ALT") == 0) {
						LOGGER.debug("User specififed ALT KEY to click");
						robot.keyPress(KeyEvent.VK_ALT);
						LOGGER.debug("Clicked ALT KEY");
					} else if (subStr.compareToIgnoreCase("CTRL") == 0) {
						LOGGER.debug("User specififed CTRL KEY to click");
						robot.keyPress(KeyEvent.VK_CONTROL);
						LOGGER.debug("Clicked CTRL KEY");
					} else if (subStr.compareToIgnoreCase("SHIFT") == 0) {
						LOGGER.debug("User specififed SHIFT KEY to click");
						robot.keyPress(KeyEvent.VK_SHIFT);
						LOGGER.debug("Clicked Shift KEY");
					} else if (subStr.compareToIgnoreCase("F4") == 0) {
						LOGGER.debug("User specififed F4 KEY to click");
						robot.keyPress(KeyEvent.VK_F4);
						LOGGER.debug("Clicked F4 KEY");
					} else if (subStr.compareToIgnoreCase("HOME") == 0) {
						LOGGER.debug("User specififed HOME KEY to click");
						robot.keyPress(KeyEvent.VK_HOME);
						LOGGER.debug("Clicked HOME KEY");
					} else if (subStr.compareToIgnoreCase("END") == 0) {
						LOGGER.debug("User specififed END KEY to click");
						robot.keyPress(KeyEvent.VK_END);
						LOGGER.debug("Clicked END KEY");
					} else {

						LOGGER.error("Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
						throw new FrameworkExceptions(
								"Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
					}

					i = k + 2;

				} else {

					LOGGER.trace("Printing character [" + char1 + "]");

					LOGGER.debug("User specififed [" + char1 + "] to click");

					if (char1 >= 'A' && char1 <= 'Z') {
						robot.keyPress(KeyEvent.VK_SHIFT);
					}

					switch (char1) {
					case 'a':
					case 'A':
						robot.keyPress(KeyEvent.VK_A);
						break;
					case 'b':
					case 'B':
						robot.keyPress(KeyEvent.VK_B);
						break;
					case 'c':
					case 'C':
						robot.keyPress(KeyEvent.VK_C);
						break;
					case 'd':
					case 'D':
						robot.keyPress(KeyEvent.VK_D);
						break;
					case 'e':
					case 'E':
						robot.keyPress(KeyEvent.VK_E);
						break;
					case 'f':
					case 'F':
						robot.keyPress(KeyEvent.VK_F);
						break;
					case 'g':
					case 'G':
						robot.keyPress(KeyEvent.VK_G);
						break;
					case 'h':
					case 'H':
						robot.keyPress(KeyEvent.VK_H);
						break;
					case 'i':
					case 'I':
						robot.keyPress(KeyEvent.VK_I);
						break;
					case 'j':
					case 'J':
						robot.keyPress(KeyEvent.VK_J);
						break;
					case 'k':
					case 'K':
						robot.keyPress(KeyEvent.VK_K);
						break;
					case 'l':
					case 'L':
						robot.keyPress(KeyEvent.VK_L);
						break;
					case 'm':
					case 'M':
						robot.keyPress(KeyEvent.VK_M);
						break;
					case 'n':
					case 'N':
						robot.keyPress(KeyEvent.VK_N);
						break;
					case 'o':
					case 'O':
						robot.keyPress(KeyEvent.VK_O);
						break;
					case 'p':
					case 'P':
						robot.keyPress(KeyEvent.VK_P);
						break;
					case 'q':
					case 'Q':
						robot.keyPress(KeyEvent.VK_Q);
						break;
					case 'r':
					case 'R':
						robot.keyPress(KeyEvent.VK_R);
						break;
					case 's':
					case 'S':
						robot.keyPress(KeyEvent.VK_S);
						break;
					case 't':
					case 'T':
						robot.keyPress(KeyEvent.VK_T);
						break;
					case 'u':
					case 'U':
						robot.keyPress(KeyEvent.VK_U);
						break;
					case 'v':
					case 'V':
						robot.keyPress(KeyEvent.VK_V);
						break;
					case 'w':
					case 'W':
						robot.keyPress(KeyEvent.VK_W);
						break;
					case 'x':
					case 'X':
						robot.keyPress(KeyEvent.VK_X);
						break;
					case 'y':
					case 'Y':
						robot.keyPress(KeyEvent.VK_Y);
						break;
					case 'z':
					case 'Z':
						robot.keyPress(KeyEvent.VK_Z);
						break;

					case '0':
						robot.keyPress(KeyEvent.VK_0);
						break;
					case '1':
						robot.keyPress(KeyEvent.VK_1);
						break;
					case '2':
						robot.keyPress(KeyEvent.VK_2);
						break;
					case '3':
						robot.keyPress(KeyEvent.VK_3);
						break;
					case '4':
						robot.keyPress(KeyEvent.VK_4);
						break;
					case '5':
						robot.keyPress(KeyEvent.VK_5);
						break;
					case '6':
						robot.keyPress(KeyEvent.VK_6);
						break;
					case '7':
						robot.keyPress(KeyEvent.VK_7);
						break;
					case '8':
						robot.keyPress(KeyEvent.VK_8);
						break;
					case '9':
						robot.keyPress(KeyEvent.VK_9);
						break;

					case ';':
						robot.keyPress(KeyEvent.VK_SEMICOLON);
						break;
					case '/':
						robot.keyPress(KeyEvent.VK_SLASH);
						break;
					case '<':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_COMMA);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '>':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_PERIOD);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case ':':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SEMICOLON);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '@':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_2);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '{':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '}':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '$':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_4);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '(':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_9);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case ')':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_0);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '#':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_3);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '&':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_7);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '*':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_8);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '!':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_1);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					case '\\':
						robot.keyPress(KeyEvent.VK_BACK_SLASH);
						break;
					case '-':
						robot.keyPress(KeyEvent.VK_MINUS);
						break;
					case '+':
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_EQUALS);
						robot.keyRelease(KeyEvent.VK_SHIFT);
						break;
					default:
						LOGGER.error("Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
						throw new FrameworkExceptions(
								"Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
					}

					if (char1 >= 'A' && char1 <= 'Z') {
						robot.keyRelease(KeyEvent.VK_SHIFT);
					}

					LOGGER.debug("Clicked KEY [" + char1 + "]");

					i++;
				}

				robot.delay(KEY_DELAY_MS);
			}
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}

		return true;
	}

	/**
	 * Uses Robot library to press the specified Mouse Action on active window
	 * at the location of the cursor.
	 * 
	 * @param mouseAction
	 *            Mouse action to be performed (BUTTONPRESS or MOUSEMOVE)
	 * @param parameters
	 *            parameters for Mouse Actions
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void mouseEvent(String mouseAction, String parameters)
			throws FrameworkExceptions {
		LOGGER.debug("Executing command: [mouse] with value [" + parameters
				+ "]");

		String[] mouseEvtParams = null;
		String description = "Executing mouseEvent action of action:"
				+ mouseAction + " with parameters:" + parameters;
		preTestStep(description);

		// Checking whether mouse event value is passed or not
		if (parameters != null && !parameters.equals("")) {
			mouseEvtParams = parameters.split(",");

			// Let us create a little delay
			//
			robot.delay(KEY_DELAY_MS);

			// eliminating white spaces
			mouseAction.trim();

			if (mouseAction.equals(MOUSEACTION_BUTTONPRESS)) {
				LOGGER.trace("Click the mouse button");

				// Checking if parameters are null
				if (mouseEvtParams.length > 0) {
					// checking the parameter if it left or right click
					// Left=left click
					if (mouseEvtParams[0]
							.compareToIgnoreCase(MOUSE_CLICKBUTTON_LEFT) == 0) {
						robot.mousePress(InputEvent.BUTTON1_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
						LOGGER.debug("Clicked mouse left button");
						postTestStep("PASS");
					}
					// Center button click
					else if (mouseEvtParams[0]
							.compareToIgnoreCase(MOUSE_CLICKBUTTON_MIDDLE) == 0) {
						robot.mousePress(InputEvent.BUTTON2_MASK);
						robot.mouseRelease(InputEvent.BUTTON2_MASK);
						LOGGER.debug("Clicked mouse center button");
						postTestStep("PASS");
					} // Right button click
					else if (mouseEvtParams[0]
							.compareToIgnoreCase(MOUSE_CLICKBUTTON_RIGHT) == 0) {
						robot.mousePress(InputEvent.BUTTON3_MASK);
						robot.mouseRelease(InputEvent.BUTTON3_MASK);
						LOGGER.debug("Clicked mouse right button");
						postTestStep("PASS");
					} else {
						String errMsg = "Invalid parameter ["
								+ mouseEvtParams[0]
								+ "] specified for [mouse] action. Please refer to documentation on how to use [mouse] action.";
						LOGGER.error(errMsg);
						throw new FrameworkExceptions(errMsg);
					}
				} else {
					String errMsg = "Invalid parameters specified for [mouse] action. Please refer to documentation on how to use [mouse] action.";
					LOGGER.error(errMsg);
					postTestStep("FAIL");
					throw new FrameworkExceptions(errMsg);
				}
			}
			// This action takes mouse to specified Coordinates
			else if (mouseAction.equals(MOUSEACTION_MOUSEMOVE)) {
				LOGGER.trace("Mouse Action: MOUSEMOVE");
				// Checking values are not null
				if (mouseEvtParams.length >= 2) {
					robot.mouseMove(Integer.parseInt(mouseEvtParams[0]),
							Integer.parseInt(mouseEvtParams[1]));
					LOGGER.debug("Mouse Action: MOUSEMOVE completed successfully");
					postTestStep("PASS");
				} else {
					String errMsg = "Invalid parameters specified for [mouse] action. Please refer to documentation on how to use [mouse] action.";
					LOGGER.error(errMsg);
					throw new FrameworkExceptions(errMsg);
				}
			} else {
				String errMsg = "Invalid mouse action specified. Valid actions BUTTONPRESS and MOUSEMOVE. Please refer to documentation on how to use [mouse] action.";
				LOGGER.error(errMsg);
				postTestStep("FAIL");
				throw new FrameworkExceptions(errMsg);
			}
		} else {
			String errMsg = "No parameter passed for [mouse] action. Please refer to documentation on how to use [mouse] action.";
			LOGGER.error(errMsg);
			postTestStep("FAIL");
			throw new FrameworkExceptions(errMsg);
		}
	}

	/**
	 * Mouse over.
	 * 
	 * @param objectID
	 *            the object id
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void mouseOver(String objectID) throws FrameworkExceptions {
		String browserVersion = "";
		WebElement webElement = actions.findElement(objectID);
		String description = "Executing mouseOver action on object:" + objectID;
		preTestStep(description);

		try {
			if (browserVersion.matches("(?i)^safari.*")) {
				LOGGER.debug("Creating JavaScript Mouseover object");
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				JavascriptExecutor js = (JavascriptExecutor) driver;
				LOGGER.debug("Executing mouseOver command");
				js.executeScript(mouseOverScript, webElement);
			} else {

				LOGGER.debug("Executing mouseOver command");
				actionBuilder.moveToElement(webElement).build().perform();				
			}
			LOGGER.debug("Executed Mouse Over Action on [" + objectID + "]");
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error(
					"Unable to exeucte mouse over action on object: " + objectID + ". Excepton is: " + e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to exeucte mouse over action on object: " + objectID);
		}
	}

	
	/**
	 * MouseLeftClick.
	 * 
	 * @param objectID
	 *            the object id
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */

	public void mouseLeftClick(String objectID) throws FrameworkExceptions {

		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		try {
			String description = "Executing double click action on object:" + objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator + "] to be present");
			waitForElement = actions.waitForElementPresent(objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty("ElementWaitTime")));

			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [Mouse Left Click]");
				if (Config.getConfig().getConfigProperty("BrowserType").matches("(?i)^safari.*")) {
					LOGGER.trace("Creating JavaScript mouse click object");
					String clickScript = "if(document.createEvent){var evObj = "
							+ "document.createEvent('MouseEvents');evObj.initEvent('click', true, false); "
							+ "arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) "
							+ "{ arguments[0].fireEvent('onclick');}";
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript(clickScript, actions.findElement(objectID), objectLocator);
				} else {

					LOGGER.debug("Executing mouse left click command");
					Actions mouceLeftClick = actionBuilder.moveToElement(actions.findElement(objectID)).click();
					mouceLeftClick.build().perform();
				}
				LOGGER.info("mouse left click [" + objectID + "]");
				postTestStep("PASS");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");

			}
		} catch (Exception e) {
			LOGGER.error("Unable to exeucte mouse left click action on object: " + objectID + ". Excepton is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to exeucte mouse left click action on object: " + objectID);
		}

	}

	/**
	 * MouseRightClick.
	 * 
	 * @param objectID
	 *            the object id
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void mouseRightClick(String objectID) throws FrameworkExceptions {

		String objectLocator = null;
		try {
			objectLocator = ObjectRepository.getOR().getORIdentifierValue(objectID);
		
			String description = "Executing double click action on object:" + objectID;
			preTestStep(description);
			LOGGER.trace("Waiting for element [" + objectLocator + "] to be present");
			waitForElement = actions.waitForElementPresent(objectID,
					Integer.parseInt(Config.getConfig().getConfigProperty("ElementWaitTime")));

			if (waitForElement) {
				LOGGER.trace("Element [" + objectLocator + "] is found");
				LOGGER.trace("Executing command: [Mouse Right Click]");
				if (Config.getConfig().getConfigProperty("BrowserType").matches("(?i)^safari.*")) {
					LOGGER.trace("Creating JavaScript mouse click object");
					String rightClickScript = "if(document.createEvent){var evObj = "
							+ "document.createEvent('MouseEvents');evObj.initEvent('contextmenu', true, false); "
							+ "arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) "
							+ "{ arguments[0].fireEvent('oncontextmenu');}";
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript(rightClickScript, actions.findElement(objectID), objectLocator);
				} else {
					LOGGER.debug("Executing mouse right click command");
					Actions mouseRightClick = actionBuilder.contextClick(actions.findElement(objectID));
					mouseRightClick.build().perform();
				}
				LOGGER.info("mouse right click [" + objectID + "]");
				postTestStep("PASS");
			} else {
				LOGGER.error("Element [" + objectLocator + "] not found");
				postTestStep("FAIL");

			}
		} catch (Exception e) {
			LOGGER.error("Unable to exeucte mouse left click action on object: " + objectID + ". Excepton is: "
					+ e.getMessage());
			postTestStep("FAIL");
			throw new FrameworkExceptions("Unable to exeucte mouse left click action on object: " + objectID);
		}

	}

	/**
	 * Key Press and Release Using Actions class.
	 * 
	 * @param KeyValue
	 *            and action for type of action to be perform(PRESS/RELEASE)
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void actions_Keys(String action, String keyValue) throws FrameworkExceptions {

		try {
			if (keyValue.length() == 1) {
				action_SendKeys(keyValue);
			} else {
				Keys key = key(keyValue);
				String description = null;
				switch (keyValue.toUpperCase()) {
				case "SHIFT":
				case "ALT":
				case "CTRL":
				case "COMMAND": {
					switch (action.toUpperCase()) {

					case "PRESS":
						LOGGER.debug("Executing command: [keyPress] with value [" + keyValue + "]");
						description = "Executing keyPress action of value is:" + keyValue;
						preTestStep(description);
						actionBuilder.keyDown(key).build().perform();
						break;
					case "RELEASE":
						LOGGER.debug("Executing command: [keyRelease] with value [" + keyValue + "]");
						description = "Executing keyRelease action of value is:" + keyValue;
						preTestStep(description);
						actionBuilder.keyDown(key).build().perform();
						break;
					}
				}
					break;

				default:
					actionBuilder.sendKeys(key).build().perform();
				}
				postTestStep("PASS");
			}

		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
	}

	/**
	 * Key Press and Release Using Actions class.
	 * 
	 * @param WebElement,
	 *            KeyValue and action for type of action to be
	 *            perform(PRESS/RELEASE)
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public void actions_Keys(String objectID, String action, String keyValue) throws FrameworkExceptions {

		try {
			if (keyValue.length() == 1) {
				action_SendKeys(keyValue);
			} else {
				Keys key = key(keyValue);
				String description = null;
				switch (keyValue.toUpperCase()) {
				case "SHIFT":
				case "ALT":
				case "CTRL":
				case "COMMAND": {
					switch (action.toUpperCase()) {
					case "PRESS":
						LOGGER.debug("Executing command: [keyPress] with value [" + keyValue + "] on the ObjectID ["
								+ objectID + "]");
						description = "Executing keyPress action of value is:" + keyValue + " on the ObjectID ["
								+ objectID + "]";
						preTestStep(description);
						actionBuilder.keyDown(actions.findElement(objectID), key).build().perform();
						break;
					case "RELEASE":
						LOGGER.debug("Executing command: [keyRelease] with value [" + keyValue + "]");
						description = "Executing keyRelease action of value is:" + keyValue;
						preTestStep(description);
						actionBuilder.keyDown(actions.findElement(objectID), key).build().perform();
						break;
					}
				}
					break;

				default:
					actionBuilder.sendKeys(key).build().perform();
				}
				postTestStep("PASS");
			}
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
	}

	
	/**
	 *	Method to send keys using actions.
	 * 
	 * @param keyValue (Keys to send)
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	
	public void action_SendKeys(String keyValue) throws FrameworkExceptions {
		try {
			String description = null;
			switch (keyValue) {
			case "a":
			case "A":
			case "b":
			case "B":
			case "c":
			case "C":
			case "d":
			case "D":
			case "e":
			case "E":
			case "f":
			case "F":
			case "g":
			case "G":
			case "h":
			case "H":
			case "i":
			case "I":
			case "j":
			case "J":
			case "k":
			case "K":
			case "l":
			case "L":
			case "m":
			case "M":
			case "n":
			case "N":
			case "o":
			case "O":
			case "p":
			case "P":
			case "q":
			case "Q":
			case "r":
			case "R":
			case "s":
			case "S":
			case "t":
			case "T":
			case "u":
			case "U":
			case "v":
			case "V":
			case "w":
			case "W":
			case "x":
			case "X":
			case "y":
			case "Y":
			case "z":
			case "Z":
				LOGGER.debug("Executing command: [keyPress] with value [" + keyValue + "]");
				description = "Executing keyPress action of value is:" + keyValue;
				preTestStep(description);
				actionBuilder.sendKeys(keyValue).build().perform();
				break;
			default:
				LOGGER.debug("Executing command: [keyPress] with value [" + keyValue + "]");
				description = "Executing keyPress action of value is:" + keyValue;
				preTestStep(description);
				actionBuilder.sendKeys(keyValue).build().perform();
			}
			postTestStep("PASS");
		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
	}

	/**
	 * Method to return Keys object
	 * 
	 * @param KeyValue
	 * 
	 * @throws FrameworkExceptions
	 *             the framework exceptions
	 */
	public Keys key(String keyValue) throws FrameworkExceptions {

		Keys key = null;
		try {
			switch (keyValue.toUpperCase()) {
			case "SHIFT":
				key = Keys.SHIFT;
				break;
			case "ALT":
				key = Keys.ALT;
				break;
			case "DELETE":
				key = Keys.DELETE;
				break;
			case "CTRL":
				key = Keys.CONTROL;
				break;
			case "ARROW_LEFT":
				key = Keys.ARROW_LEFT;
				break;
			case "ARROW_RIGHT":
				key = Keys.ARROW_RIGHT;
				break;
			case "ARROW_DOWN":
				key = Keys.ARROW_DOWN;
				break;
			case "ARROW_UP":
				key = Keys.ARROW_UP;
				break;
			case "F1":
				key = Keys.F1;
				break;
			case "F2":
				key = Keys.F2;
				break;
			case "F3":
				key = Keys.F3;
				break;
			case "F4":
				key = Keys.F4;
				break;
			case "F5":
				key = Keys.F5;
				break;
			case "F6":
				key = Keys.F6;
				break;
			case "F7":
				key = Keys.F7;
				break;
			case "F8":
				key = Keys.F8;
				break;
			case "F9":
				key = Keys.F9;
				break;
			case "F10":
				key = Keys.F10;
				break;
			case "F11":
				key = Keys.F11;
				break;
			case "F12":
				key = Keys.F12;
				break;
			case "+":
				key = Keys.ADD;
				break;
			case "BACK_SPACE":
				key = Keys.BACK_SPACE;
				break;
			case "CANCEL":
				key = Keys.CANCEL;
				break;
			case "CLEAR":
				key = Keys.CLEAR;
				break;
			case "COMMAND":
				key = Keys.COMMAND;
				break;
			case "DECIMAL":
				key = Keys.DECIMAL;
				break;
			case "/":
				key = Keys.DIVIDE;
				break;
			case "DOWN":
				key = Keys.DOWN;
				break;
			case "UP":
				key = Keys.UP;
				break;
			case "END":
				key = Keys.END;
				break;
			case "ENTER":
				key = Keys.ENTER;
				break;
			case "EQUALS":
				key = Keys.EQUALS;
				break;
			case "ESCAPE":
				key = Keys.ESCAPE;
				break;
			case "HELP":
				key = Keys.HELP;
				break;
			case "HOME":
				key = Keys.HOME;
				break;
			case "INSERT":
				key = Keys.INSERT;
				break;
			case "LEFT":
				key = Keys.LEFT;
				break;
			case "LEFT_CONTROL":
				key = Keys.LEFT_CONTROL;
				break;
			case "LEFT_SHIFT":
				key = Keys.LEFT_SHIFT;
				break;
			case "META":
				key = Keys.META;
				break;
			case "MULTIPLY":
				key = Keys.MULTIPLY;
				break;
			case "NULL":
				key = Keys.NULL;
				break;
			case "0":
				key = Keys.NUMPAD0;
				break;
			case "1":
				key = Keys.NUMPAD1;
				break;
			case "2":
				key = Keys.NUMPAD2;
				break;
			case "3":
				key = Keys.NUMPAD3;
				break;
			case "4":
				key = Keys.NUMPAD4;
				break;
			case "5":
				key = Keys.NUMPAD5;
				break;
			case "6":
				key = Keys.NUMPAD6;
				break;
			case "7":
				key = Keys.NUMPAD7;
				break;
			case "8":
				key = Keys.NUMPAD8;
				break;
			case "9":
				key = Keys.NUMPAD9;
				break;
			case "PAGE_DOWN":
				key = Keys.PAGE_DOWN;
				break;
			case "PAGE_UP":
				key = Keys.PAGE_UP;
				break;
			case "RETURN":
				key = Keys.RETURN;
				break;
			case "RIGHT":
				key = Keys.RIGHT;
				break;
			case ";":
				key = Keys.SEMICOLON;
				break;
			case "SEPARATOR":
				key = Keys.SEPARATOR;
				break;
			case "SPACE":
				key = Keys.SPACE;
				break;
			case "-":
				key = Keys.SUBTRACT;
				break;

			default:
				LOGGER.error(
						"Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
				throw new FrameworkExceptions(
						"Invalid KEY SEQUENCE specified!!!! Please refer to user documentation for the supported special key sequences.");
				
			}

		} catch (Exception e) {
			LOGGER.error("Exception::", e);
			postTestStep("FAIL");
			throw new FrameworkExceptions(e);
		}
		return key;
	}
}