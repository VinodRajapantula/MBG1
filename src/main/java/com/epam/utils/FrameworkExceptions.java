package com.epam.utils;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;
import com.epam.driver.SetupSelenium;

/**
 * Class for displaying user-friendly custom exceptions.
 * 
 */
public class FrameworkExceptions extends Exception {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(FrameworkExceptions.class);
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error message. */
	private String errorMessage = null;

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static void startTestCase(String sTestCaseName){
	    LOGGER.info("****************************************************************************************");
	    LOGGER.info("|||||||||||||||||| Test Case: "+sTestCaseName+ " Started ||||||||||||||||||||||||");
	    LOGGER.info("****************************************************************************************");
	    }
	
	    //This is to print log for the ending of the test case
	public static void endTestCase(String sTestCaseName){
	    LOGGER.info("****************************************************************************************");
		LOGGER.info("|||||||||||||||||||||| Test Case: "+sTestCaseName+ " Ended   ||||||||||||||||||||||||");
	    LOGGER.info("****************************************************************************************");
	    }
	
	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static void sPrintMessage(String sMessage){
	    LOGGER.info("****************************************************************************************");
	    LOGGER.info("||||||||||||||||||          "+sMessage+ "               ||||||||||||||||||||||||");
	    LOGGER.info("****************************************************************************************");
	    }
	
	/**
	 * Creates an instance of the class.
	 * 
	 * @param page
	 *            the page
	 * @param object
	 *            the object
	 */
	public FrameworkExceptions(String page, String object) {
		super();
		StringBuffer message = new StringBuffer();
		try {
			if (page != null && !"".equals(page) && object != null
					&& !"".equals(object)) {
				String key = page.toUpperCase() + "." + object.toUpperCase();
				message.append(key);
			}
		} catch (Exception e) {
			message.append(e.getMessage());
			LOGGER.error("Exception occurred in framework exception constructor", e);
		}

		this.errorMessage = message.toString();
	}

	/**
	 * Instantiates a new framework exception.
	 * 
	 * @param message
	 *            the message
	 */
	public FrameworkExceptions(String message) {
		super(message);
		errorMessage = message;
//		CoreDriverScript.driver.quit();
		
	}

	/**
	 * Instantiates a new framework exception.
	 * 
	 * @param e
	 *            the e
	 */
	public FrameworkExceptions(Throwable e) {
		super(e.getLocalizedMessage().split("\n")[0]);
		errorMessage = e.getMessage();
//		CoreDriverScript.driver.quit();
		}

	public FrameworkExceptions(String page, Throwable e) {
		super(page, e);
		errorMessage = e.getMessage();
//		CoreDriverScript.driver.quit();
	}
}
