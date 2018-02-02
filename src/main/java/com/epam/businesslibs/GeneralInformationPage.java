package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.FrameworkExceptions;

public class GeneralInformationPage extends CoreDriverScript {

	private static final Logger LOGGER = Logger.getLogger(GeneralInformationPage.class);
	
	/* 	**********************Method Header******************************************************************
	Method Name					: PriorCoverageDetails	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To fill Prior Coverage details
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
	public void PriorCoverageDetails(TreeMap<String,String> dataMap) throws Exception
	{
		try{
			if(!actions.isLoaded("divGeneralInfo_PageTitle"))
			{
				LOGGER.error("Object 'General Information Page' not found.");
				actions.postTestStep("FAIL", "General Information Page - Exit", "General Information Page not found");
				throw new Exception();
			}
			

			if(dataMap.get("GENERALINFORMATION_CARRIER") != "")
			{
				actions.selectOptionByText("lstCarrier",dataMap.get("GENERALINFORMATION_CARRIER"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstCarrier' Data updated Successfully.");}
			}

			if(dataMap.get("GENERALINFORMATION_#_YEARS_WITH_COMPANY") != "")
			{
				actions.selectOptionByText("lstYearsWithCompany",dataMap.get("GENERALINFORMATION_#_YEARS_WITH_COMPANY"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstYearsWithCompany' Data updated Successfully.");}
			}


			if(dataMap.get("GENERALINFORMATION_EXPIRATION_DATE") != "")
			{
				actions.selectOptionByText("lstExpirationDate",dataMap.get("GENERALINFORMATION_EXPIRATION_DATE"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExpirationDate' Data updated Successfully.");}
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in PriorCoverageDetails method in  GeneralInformationPage page");			
			e.printStackTrace();
			actions.postTestStep("FAIL", "General Information Page - Exit", "Exception occurred in PriorCoverageDetails method");
			throw e;
		}

	}
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Submission page.
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
			blnStatus = actions.isLoaded("divGeneralInfo_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","General Information page Submission", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divSubmission_PageTitle"))
				{
					blnStatus= true;
					actions.postTestStep("PASS","General Information page Submission", "General Information page submitted successfully");
				}
				else
				{
					LOGGER.error("Object 'divSubmission_PageTitle' not found.");
					actions.postTestStep("FAIL", "General Information page Submit - Exit", "Submission Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divGeneralInfo_PageTitle' not found.");
				actions.postTestStep("FAIL", "General Information page Submit - Exit", "General Information Page not found");
				throw new Exception();
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in PriorCoverageDetails method in  GeneralInformationPage page");			
			e.printStackTrace();	
			actions.postTestStep("FAIL", "General Information Page Submit - Exit", "Exception occurred in General Information Page Submit method");
			throw e;
		}
		return blnStatus;
	}

}
