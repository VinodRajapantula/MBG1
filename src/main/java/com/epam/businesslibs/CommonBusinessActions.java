package com.epam.businesslibs;

import java.awt.Robot;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.FrameworkExceptions;


public class CommonBusinessActions extends CoreDriverScript {

	private static final Logger LOGGER = Logger.getLogger(NewQuotePage.class);	
	private static boolean blnStatus = false;

	ApplicantPage applicantionPage = new ApplicantPage();
	DriversPage driverspage = new DriversPage();
	VehiclesPage vehiclesPage = new VehiclesPage();
	CoveragesPage coveragesPage = new CoveragesPage();
	UnderWritingPage underwritingPage = new UnderWritingPage();
	GeneralInformationPage generalInformationPage = new GeneralInformationPage(); 
	SubmissionPage submissionPage = new SubmissionPage();
	ViolationsPage violationsPage = new ViolationsPage();
	PremiumDetailsPage premiumDetailsPage = new PremiumDetailsPage();
	AdditionalInterestPage additionalInterestPage = new AdditionalInterestPage();

	/* 	**********************Method Header******************************************************************
	Method Name					: ValidateRatingMessage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Validate the  Rating message.
	*List of arguments       	: String strExpectedMessage
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean ValidateRatingMessage(String strExpectedMessage) throws Exception
	{
		blnStatus = false;
		try
		{	
			List<WebElement> objRatingMessages = actions.findElements("eleCommon_RatingMessages");
			for(WebElement element:objRatingMessages)
			{
				try{
					element.click();
				}catch(WebDriverException wde){
					actions.jsExecutor("arguments[0].scrollIntoView()", element);
					element.click();
				}
				if(element.getText().trim().contains(strExpectedMessage.trim()))
				{
					blnStatus = true;
					blnExecuteReRef=true;
					LOGGER.info("Rating Message Triggered Successfully");
					actions.postTestStep("PASS","Rating Message Validation","Expected Rating Message : "+ strExpectedMessage +" Triggered Successfully");
					break;
				}
				else
				{
					blnStatus = false;
					blnExecuteReRef=false;
				}
				
			}
			if(!blnStatus)
			{
				LOGGER.error("Rating Message Validation Failed");
				actions.postTestStep("FAIL","Rating Message Validation","Expected Rating Message : "+ strExpectedMessage +" is not Triggered");			
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in ValidateRatingMessage method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "ValidateRatingMessage - Exit", "Exception occurred in ValidateRatingMessage method");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: strGetQuoteNumber	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To capture the Quote Number.
	*List of arguments       	: None
	*Return value            	: String
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public String strGetQuoteNumber() throws Exception
	{
		String strQuote = null;

		try
		{
			strQuote = 	actions.getText("spanQuoteNumber");
			if(strQuote != null && strQuote.length()>0)
			{
				actions.postTestStep("PASS", "Capture Quote/Policy Number", "Captured Quote number successfully ,Quote/Policy Number : - " + strQuote );
				blnExecuteReRef=true;
			}
			else
			{
				actions.postTestStep("FAIL", "Capture Quote/Policy Number", "Capturing Quote/Policy number failed ");
				blnExecuteReRef=false;
			}
		}
		catch (Exception e) {	
			blnExecuteReRef=false;
			LOGGER.error("Exception in strGetQuoteNumber method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "strGetQuoteNumber - Exit", "Exception occurred in strGetQuoteNumber method");
			throw e;
		}
		return strQuote.trim();
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SearchQuote	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Search the Quote.
	*List of arguments       	: String
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SearchQuote(String strQuoteNumber) throws Exception
	{
		boolean blnStatus = false;

		try
		{
			actions.type("txtSearch", strQuoteNumber);
			actions.click("btnSearch");
			actions.isLoaded("divQuotes");
			List<WebElement> lstQuotes = actions.findElements("divQuotes");
			for(WebElement element : lstQuotes)
			{
				if(element.getText().trim().equalsIgnoreCase(strQuoteNumber))
				{
					element.click();
					actions.WaitTillRender();

					actions.postTestStep("PASS", "Search Quote ", " Quote Searched  successfully ,Quote Number searched : - " + strQuoteNumber );
				}

				break;
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in SearchQuote method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "SearchQuote - Exit", "Exception occurred in SearchQuote method");
			throw e;
		}


		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitApplicantPage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 

	public boolean SubmitApplicantPage() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divApplicant_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitApplicantPage- Exit", "Applicant Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitApplicantPage - Exit", "Exception occurred in SubmitApplicantPage method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitDrivers	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitDrivers() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divDrivers_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitDrivers- Exit", "Drivers Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitDrivers - Exit", "Exception occurred in SubmitDrivers method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitViolations	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitViolations() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divViolations_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitViolations- Exit", "Violations Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitViolations - Exit", "Exception occurred in SubmitViolations method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitVehicles	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/
	public boolean SubmitVehicles() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divVehicals_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitVehicles- Exit", "Vehicles Page not found");
				throw new Exception();
			}

		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitVehicles - Exit", "Exception occurred in SubmitVehicles method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitCoverages	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitCoverages() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divCoverageDetails_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitCoverages- Exit", "Coverages Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitCoverages - Exit", "Exception occurred in SubmitCoverages method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitPremium	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitPremium() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divPremium_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}

			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitPremium- Exit", "Premium Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitPremium - Exit", "Exception occurred in SubmitPremium method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitAdditionalInterests	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitAdditionalInterests() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divAdditionalIntrest_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitAdditionalInterests- Exit", "Additional Interests Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitAdditionalInterests - Exit", "Exception occurred in SubmitAdditionalInterests method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitUnderWriting	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitUnderWriting() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divUnderWriting_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitUnderWriting- Exit", "UnderWriting Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitUnderWriting - Exit", "Exception occurred in SubmitUnderWriting method");
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitGeneralInformationPage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean SubmitGeneralInformationPage() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divGeneralInfo_PageTitle");
			if(blnStatus)
			{
				actions.click("btnNext");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "SubmitGeneralInformationPage- Exit", "General Information Page not found");
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			Reporter.log("Element Rendered successfully after waiting max time");
			blnStatus= false;
			actions.postTestStep("FAIL", "SubmitGeneralInformationPage - Exit", "Exception occurred in SubmitGeneralInformationPage method");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: ApproveQuote	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Approve the Quote.
	*List of arguments       	: NA
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean ApproveQuote() throws Exception
	{
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divSubmission_PageTitle");
			if(blnStatus)
			{
				actions.click("btnApprovedQuoteWithoutChanges");
			}
			else
			{
				LOGGER.error("User is not in Drivers Page");
				actions.postTestStep("FAIL", "ApproveQuote- Exit", "Submissions Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in ApproveQuote method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "ApproveQuote - Exit", "Exception occurred in ApproveQuote method");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: NavigateToPage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To navigate to required page.
	*List of arguments       	: String
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public static boolean NavigateToPage(String strPagename) throws Exception {

		try
		{
			List<WebElement> objWebPages = actions.findElements("LnkWebPages");
			for(WebElement element:objWebPages)
			{
				if(element.getText().trim().contains(strPagename.trim()))
				{
					blnStatus = true;
					element.click();
					actions.WaitTillRender();
					LOGGER.info("Clicked on "+ strPagename +"successfully.");
					actions.postTestStep("PASS","Navigating to Page "+ strPagename , "Navigating to Page "+ strPagename +" is Successfull");
					break;
				}
				else
				{
					blnStatus = false;
				}
			}
			if(!blnStatus)
			{
				LOGGER.error("Navigation to page "+strPagename );
				actions.postTestStep("FAIL","Navigating to Page "+ strPagename , "Navigating to Page "+ strPagename +" is failed");		
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in NavigateToPage method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "NavigateToPage - Exit", "Exception occurred in NavigateToPage method");
			throw e;
		}
		return blnStatus;

	}
	/* 	**********************Method Header******************************************************************
	Method Name					: LogoutMBG	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To close the MBG Appliaction
	*List of arguments       	: NA
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public void LogoutMBG() throws Exception
	{
		try
		{
			/*	actions.isLoaded("lnkLogOut");
			actions.click("lnkLogOut");*/
			actions.preTestStep("logout");
			actions.postTestStep("PASS", "LogOut From MBG", "LogOut from MBG Successfull");
		}

		catch (Exception e) {					
			LOGGER.error("Exception in LogoutMBG method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "LogOutMBG - Exit", "Exception occurred in LogoutMBG method");
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: NavigateTillSubmission_End	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Navigate through All the pages till submission.
	*List of arguments       	: NA
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public void NavigateTillSubmission() throws Exception {
		// TODO Auto-generated method stub
		try{
			actions.waitForElementPresent("divApplicant_PageTitle", 10);
			applicantionPage.Submit();
			actions.waitForElementPresent("divDrivers_PageTitle", 10);
			driverspage.Submit();
			actions.waitForElementPresent("divViolations_PageTitle", 10);
			violationsPage.Submit();
			actions.waitForElementPresent("divVehicals_PageTitle", 10);
			vehiclesPage.Submit();
			actions.waitForElementPresent("divCoverageDetails_PageTitle", 10);
			coveragesPage.Submit();
			actions.waitForElementPresent("divPremium_PageTitle", 10);
			premiumDetailsPage.Submit();
			actions.waitForElementPresent("divAdditionalIntrest_PageTitle", 10);
			additionalInterestPage.Submit();
			actions.waitForElementPresent("divUnderWriting_PageTitle", 10);
			underwritingPage.Submit();
			actions.waitForElementPresent("divGeneralInfo_PageTitle", 10);
			generalInformationPage.Submit();
			actions.waitForElementPresent("divSubmission_PageTitle", 10);

		}
		catch (Exception e) {					
			LOGGER.error("Exception in NavigateTillSubmission method in CommonBusinessActions");
			e.printStackTrace();
			actions.postTestStep("FAIL", "NavigateTillSubmission - Exit", "Exception occurred in NavigateTillSubmission method");
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SubmitApplicantPage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Next button and Navigate Next Page.
	*List of arguments       	: NA
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public void NavigateTillSubmission_End() throws Exception {
		// TODO Auto-generated method stub
		try{
			actions.waitForElementPresent("divApplicant_PageTitle", 10);
			applicantionPage.Submit();
			actions.waitForElementPresent("divDrivers_PageTitle", 10);
			driverspage.Submit();
			actions.waitForElementPresent("divViolations_PageTitle", 10);
			violationsPage.Submit();
			actions.waitForElementPresent("divVehicals_PageTitle", 10);
			vehiclesPage.Submit();
			actions.waitForElementPresent("divCoverageDetails_PageTitle", 10);
			coveragesPage.Submit();
			actions.waitForElementPresent("divAdditionalIntrest_PageTitle", 10);
			additionalInterestPage.Submit();

			actions.waitForElementPresent("divPremium_PageTitle", 10);
			premiumDetailsPage.Submit();

			//			actions.waitForElementPresent("divUnderWriting_PageTitle", 10);
			//			underwritingPage.Submit();
			//			actions.waitForElementPresent("divGeneralInfo_PageTitle", 10);
			//			generalInformationPage.Submit();
			actions.waitForElementPresent("divSubmission_PageTitle", 10);

		}
		catch (Exception e) {					
			LOGGER.error("Exception in NavigateTillSubmission method in CommonBusinessActions");
			e.printStackTrace();
			throw e;
		}
	}

	/* 	**********************Method Header******************************************************************
	Method Name					: ValidateUnexpectedRatingMessage	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To validate Rating message which should not be present.
	*List of arguments       	: String
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean ValidateUnexpectedRatingMessage(String strUnexpectedMessage) throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;
		try{
			List<WebElement> objRatingMessages= null;

			String[] arrUnexpRatingMessages = strUnexpectedMessage.split("#");

			for(int iCounter =0;iCounter < arrUnexpRatingMessages.length;iCounter++)
			{
				objRatingMessages = actions.findElements("eleCommon_RatingMessages");

				if(arrUnexpRatingMessages[iCounter].trim().length()>0)
				{
					for(WebElement element:objRatingMessages)
					{
						if(element.getText().trim().length()>0 && element.getText().trim().equalsIgnoreCase(arrUnexpRatingMessages[iCounter].trim()))
						{
							blnStatus = true;
							LOGGER.info("Unexpected Rating Message present");
							actions.postTestStep("FAIL","Unexpected Rating Message Validation","A Rating Message that is not expected is present : "+ arrUnexpRatingMessages[iCounter].trim());
							break;
						}
						
					}
					
				}
				if(!blnStatus)
				{
				LOGGER.info("Unexpected Rating Message not present");
				actions.postTestStep("PASS","Unexpected Rating Message Validation","A Rating Message that is not expected is not present : "+ arrUnexpRatingMessages[iCounter].trim());
				}
			}

		}catch(Exception e){
			LOGGER.error("Exception in ValidateRatingMessage method in CommonBusinessActions");
			e.printStackTrace();
			throw e;
		}
		return blnStatus;

	}
}
