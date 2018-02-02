package com.epam.businesslibs;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;

public class PremiumDetailsPage extends CoreDriverScript{
	private static final Logger LOGGER = Logger.getLogger(PremiumDetailsPage.class);

	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Coverage page.
	*List of arguments       	: None
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean Submit() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divPremium_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Premium page Submission", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divAdditionalIntrest_PageTitle") || actions.isLoaded("divSubmission_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Premium page Submission", "Premium page submitted successfully");
				}else
				{
					LOGGER.error("Object 'divPremium_PageTitle' not found.");
					actions.postTestStep("FAIL", "Premium page Submission - Exit", "Additional intrests Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divCoverageDetails_PageTitle' not found.");
				actions.postTestStep("FAIL", "Premium page Submission Submit - Exit", "Premium Page not found");
				throw new Exception();
			}
		}

		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in PremiumDetailsPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Premium Details Page Submit - Exit", "Exception occurred in Premium Details Page Submit method");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: ClickonButtonCompleteApplication	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on complete Application button.
	*List of arguments       	: None
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public boolean ClickonButtonCompleteApplication() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divPremium_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Premium page Complete Application Button", "Clicking the Complete Application...");
				// method to be added for capturing screenshot before submission
				actions.click("btnCompleteApplication");
				if(actions.isLoaded("divAdditionalIntrest_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Premium page Complete Application Button", "Premium page submitted successfully");
				}
				else
				{
					LOGGER.error("Object 'divPremium_PageTitle' not found.");
					actions.postTestStep("FAIL", "Premium page Complete Application Button - Exit", "Additional intrests Page not found");
					throw new Exception();
				}

			}
			else
			{
				LOGGER.error("Object 'divPremium_PageTitle' not found.");
				actions.postTestStep("FAIL", "Premium page Complete Application Button - Exit", "Premium Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in ClickonButtonCompleteApplication method in PremiumDetailsPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Premium Details Page ClickonButtonCompleteApplication - Exit", "Exception occurred in ClickonButtonCompleteApplication method");
			throw e;
		}
		return blnStatus;
	}

}
