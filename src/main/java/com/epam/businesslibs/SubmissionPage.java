package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;

public class SubmissionPage extends CoreDriverScript{
	VehiclesPage vehiclespage = new VehiclesPage();
	private static final Logger LOGGER = Logger.getLogger(SubmissionPage.class);
	
	/* 	**********************Method Header******************************************************************
	Method Name					: MakeChanges	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on MakeChanges button and handle frames.
	*List of arguments       	: Map-dataMap
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public void MakeChanges() throws Exception {
		
		try
		{	
			actions.postTestStep("PASS","Make Changes", "Clickging the Make Changes..");
			actions.click("btnMakeChanges");			
			if(Config.getConfig().getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("firefox") || Config.getConfig().getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("chrome")){
				actions.switchToFrame(0);
			}else{
				actions.switchToFrame(1);
			}			
			actions.FoucsOnElement("chkOther", "id");
			actions.check("chkOther");
			actions.click("btnOK");
			driver.switchTo().defaultContent();
		}
		catch (Exception e) {					
			LOGGER.error("Exception in MakeChanges method in SubmissionPage");
			e.printStackTrace();
			throw e;
		 }
		
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: ValidateVINS	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click Validate VINS button.
	*List of arguments       	: Map-dataMap
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean ValidateVINS(TreeMap<String,String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;
		
		try
		{
			if(actions.isLoaded("divSubmission_PageTitle"))
			{	actions.postTestStep("PASS","Validating VINS", "Clicking the Validate VINS...");
				// method to be added for capturing screenshot before submission
				actions.click("btnValidateVins");
				
				if(actions.isLoaded("divSubmission_PageTitle")){
					if(!actions.waitForElementPresent("btnValidateVins", 1)){
						blnStatus= true;
						actions.postTestStep("PASS","Validating VINS", "VINS validated successfully");
					}else
					{blnStatus= false;
					actions.postTestStep("FAIL","Validating VINS", "failed..");
					throw new Exception();
					}
				}					
				
			if(actions.isLoaded("divVehicals_PageTitle")){
					blnStatus= true;
					vehiclespage.UpdateVehicles(dataMap);
					CommonBusinessActions.NavigateToPage("Submission");
					actions.postTestStep("PASS","Validating VINS", "VINS validated successfully");
				}	
				
			}
		}
		
		catch (Exception e) {					
			LOGGER.error("Exception in ValidateVINS method in SubmissionPage");
			e.printStackTrace();
			throw e;
		 }
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: ReferToUnderWriting	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Refer to Underwriting button.
	*List of arguments       	: Map-dataMap
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 	
	public boolean ReferToUnderWriting() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;
		
		try
		{
			if(actions.isLoaded("divSubmission_PageTitle"))
			{	actions.postTestStep("PASS","Refer to UnderWriting", "Clicking the Referr to UnderWriting...");
				// method to be added for capturing screenshot before submission
				actions.click("btnRefertoUnderWriting");
				if(actions.isLoaded("divSubmission_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Refer to UnderWriting", "Referred to UnderWriting successfully");
				}				
			}
		}
		catch (Exception e) {	
			blnExecuteReRef=false;
			LOGGER.error("Exception in ReferToUnderWriting method in SubmissionPage");
			e.printStackTrace();
			throw e;
		 }
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: ApproveQuote	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Approve Quote button.
	*List of arguments       	: Map-dataMap
	*Return value            	: void
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
			{	actions.postTestStep("PASS", "Approve Quote", "Clicking the Approve... ");
				actions.isLoaded("btnApprovedQuoteWithoutChanges");
				actions.click("btnApprovedQuoteWithoutChanges");
				blnStatus = !(actions.waitForElementPresent("divSubmission_PageTitle", 10));
				if(blnStatus)
				{
					actions.postTestStep("PASS", "Approve Quote", "Quote Approved Successfully.");
				}
				else
				{
					actions.postTestStep("FAIL", "Approve Quote", "Quote Approval  Failed.");
				}
			
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in ApproveQuote method in SubmissionPage");
			e.printStackTrace();
			throw e;
		 }
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: BindAndSubmit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Bind and Submit button.
	*List of arguments       	: Map-dataMap
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 	
	public void BindAndSubmit() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;
		try
		{
			if(!actions.isLoaded("divSubmission_PageTitle")){
				actions.postTestStep("FAIL", "Submission page - BindAndSubmit", "Submission page expected but not available");
				throw new Exception();
			}
			actions.postTestStep("PASS","Bind And Submit", "Clicking the  Bind And Submit..");
			// method to be added for capturing screenshot before submission
			actions.click("btnSubmission_BindAndSubmit");
			actions.WaitTillRender();
			if(actions.isLoaded("divPaymentOptions_PageTitle")){
				blnStatus= true;
				actions.postTestStep("PASS","Bind And Submit", "Binded and Submitted successfully");
			}else{
				actions.postTestStep("FAIL","Bind And Submit", "Payment Options page not displayed");
				throw new Exception();
			}
			
		}
		catch (Exception e) {					
			LOGGER.error("Exception in ReferToUnderWriting method in SubmissionPage");
			e.printStackTrace();
			throw e;
		 }
	}
}
