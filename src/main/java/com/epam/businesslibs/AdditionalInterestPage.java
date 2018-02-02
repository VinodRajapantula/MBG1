package com.epam.businesslibs;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;

public class AdditionalInterestPage extends CoreDriverScript{

	private static final Logger LOGGER = Logger.getLogger(AdditionalInterestPage.class);
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to underwriting page.
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
			blnStatus = actions.isLoaded("divAdditionalIntrest_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Additional Interest page Submission", "Submitting the page");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divUnderWriting_PageTitle")){
					blnStatus= false;
					actions.postTestStep("PASS","Additional Interest page Submission", "Additional Interest page submitted successfully");
				}				
			}
			else
			{
				LOGGER.error("Object 'divAdditionalIntrest_PageTitle' not found.");
				actions.postTestStep("FAIL", "Additional Intrests Page - Exit", "Additional Intrests Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in AdditionalInterestPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "AdditionalInterest Page - Exit", "Exception occurred in AdditionalInterestPage Submit method");
			throw e;
		 }
		return blnStatus;
	}

}
