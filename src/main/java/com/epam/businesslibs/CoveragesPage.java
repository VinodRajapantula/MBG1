package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.FrameworkExceptions;

public class CoveragesPage extends CoreDriverScript{


	private static final Logger LOGGER = Logger.getLogger(CoveragesPage.class);	
	CoverageDetailsPage coverageDetailsPage = new CoverageDetailsPage();
	
	/* 	**********************Method Header******************************************************************
	Method Name					: fillCoverageDetails	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To fill Coverage Details in Coverage Page.
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
	public void fillCoverageDetails(TreeMap<String,String> dataMap) throws Exception

	{
		try
		{
			if(!actions.isLoaded("divCoverageDetails_PageTitle"))
			{
				LOGGER.error("Object 'Coverage Detail Page' not found.");
				actions.postTestStep("FAIL", "Coverages Page - Exit", "Coverages Page not found");
				throw new Exception();
			}

			if(dataMap.get("STATE").equalsIgnoreCase("PA"))
			{
				if(dataMap.get("LIABILITY_BI") != "")
				{
					actions.selectOptionByText("lstBI",dataMap.get("LIABILITY_BI"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstBI' Data updated Successfully.");}
				}

				if(dataMap.get("LIABILITY_PD") != "")
				{
					actions.selectOptionByText("lstPD",dataMap.get("LIABILITY_PD"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPD' Data updated Successfully.");}
				}

				if(dataMap.get("LIABILITY_TORT_OPTIONS") != "")
				{
					actions.selectOptionByText("lstTortOption",dataMap.get("LIABILITY_TORT_OPTIONS"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstTortOption' Data updated Successfully.");}
				}

				if(dataMap.get("FIRSTPARTY_MED_PAY") != "")
				{
					actions.selectOptionByText("lstMedPay",dataMap.get("FIRSTPARTY_MED_PAY"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstMedPay' Data updated Successfully.");}
				}
				if(dataMap.get("FIRSTPARTY_WK_LOSS") != "")
				{
					actions.selectOptionByText("lstWKLoss",dataMap.get("FIRSTPARTY_WK_LOSS"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstWKLoss' Data updated Successfully.");}
				}
				if(dataMap.get("FIRSTPARTY_FUNERAL") != "")
				{
					actions.selectOptionByText("lstFuneral",dataMap.get("FIRSTPARTY_FUNERAL"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstFuneral' Data updated Successfully.");}
				}
				if(dataMap.get("FIRSTPARTY_ACC_DEATH") != "")
				{
					actions.selectOptionByText("lstAccDeath",dataMap.get("FIRSTPARTY_ACC_DEATH"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAccDeath' Data updated Successfully.");}
				}
				if(dataMap.get("FIRSTPARTY_EXTRAORDINARY_MEDICAL") != "")
				{
					actions.selectOptionByText("lstExtraordinaryMedical",dataMap.get("FIRSTPARTY_EXTRAORDINARY_MEDICAL"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExtraordinaryMedical' Data updated Successfully.");}
				}
				if(dataMap.get("UNDERINSURED_STACK_OPTION") != "")
				{
					actions.selectOptionByText("lstUnderInsuredStackOption",dataMap.get("UNDERINSURED_STACK_OPTION"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUnderInsuredStackOption' Data updated Successfully.");}
				}
				if(dataMap.get("UNDERINSURED_BI") != "")
				{
					actions.selectOptionByText("lstUnderInsuredBI",dataMap.get("UNDERINSURED_BI"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUnderInsuredBI' Data updated Successfully.");}
				}
				if(dataMap.get("UNINSURED_STACK_OPTION") != "")
				{
					actions.selectOptionByText("lstUnInsuredStackOption",dataMap.get("UNINSURED_STACK_OPTION"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUnInsuredStackOption' Data updated Successfully.");}
				}
				if(dataMap.get("UNINSURED_BI") != "")
				{
					actions.selectOptionByText("lstUnInsuredBI",dataMap.get("UNINSURED_BI"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUnInsuredBI' Data updated Successfully.");}
				}

			}

			else if (dataMap.get("STATE").equalsIgnoreCase("MD")) 
			{
				if(dataMap.get("LIABILITY_BI") != "")
				{
					actions.selectOptionByText("lstBI",dataMap.get("LIABILITY_BI"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstBI' Data updated Successfully.");}
				}

				if(dataMap.get("LIABILITY_PD") != "")
				{
					actions.selectOptionByText("lstPD",dataMap.get("LIABILITY_PD"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPD' Data updated Successfully.");}
				}


				if(dataMap.get("LIABILITY_PIP") != "")
				{
					actions.selectOptionByText("lstPersonalInjuryProtection",dataMap.get("LIABILITY_PIP"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPersonalInjuryProtection' Data updated Successfully.");}
				}

				if(dataMap.get("LIABILITY_MEDICALPAYMENTS") != "")
				{
					actions.selectOptionByText("lstMedicalPayments",dataMap.get("LIABILITY_MEDICALPAYMENTS"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstMedicalPayments' Data updated Successfully.");}
				}

				if(dataMap.get("UNINSURED_BI") != "")
				{
					actions.selectOptionByText("lstUnInsuredBI",dataMap.get("UNINSURED_BI"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUnInsuredBI' Data updated Successfully.");}
				}

				if(dataMap.get("UNINSURED_PD") != "")
				{
					actions.selectOptionByText("lstUninsuredPD",dataMap.get("UNINSURED_PD"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUninsuredPD' Data updated Successfully.");}
				}


			}


			if(dataMap.get("COVERAGE_DETAILS").contains("ADD"))			{
				coverageDetailsPage.AddCoverageDetails(dataMap);
				actions.isLoaded("lnkCoverageDetails");
			}
			if(dataMap.get("COVERAGE_DETAILS").contains("UPDATE"))
			{
				coverageDetailsPage.updateCoverageDetails(dataMap);
			}
			actions.WaitTillRender();
			actions.waitForElementPresent("COVERAGE_PRIOR_BI_LIMIT", 10);

			if(dataMap.get("COVERAGE_PRIOR_BI_LIMIT") != "")
			{
				actions.selectOptionByText("lstPriorBILimit",dataMap.get("COVERAGE_PRIOR_BI_LIMIT"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPriorBILimit' Data updated Successfully.");}
			}

			if(dataMap.get("COVERAGE_ACCOUNT_CREDIT") != "")
			{
				if(dataMap.get("COVERAGE_ACCOUNT_CREDIT").equalsIgnoreCase("YES"))
				{
					actions.check("chkAccountCredit");
					if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAccountCredit' Data updated Successfully.");}
				}
				else
				{
					if(dataMap.get("COVERAGE_ACCOUNT_CREDIT").equalsIgnoreCase("NO"))
					{
						actions.unCheck("chkAccountCredit");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAccountCredit' Data updated Successfully.");}
					}
				}
			}

			if(dataMap.get("COVERAGE_POLICY_NUMBER") != "")
			{               
				actions.type("txtAccountCreditPolicynumber", dataMap.get("COVERAGE_POLICY_NUMBER"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'txtAccountCreditPolicynumber' Data updated Successfully.");}
			}

			if(dataMap.get("IS_HOMEOWNER_POLICY") != "")
			{
				actions.selectOptionByText("lstIsHomeOwnerPolicy",dataMap.get("IS_HOMEOWNER_POLICY"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPriorBILimit' Data updated Successfully.");}
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in fillCoverageDetails method in CoveragesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Coverages Page - Exit", "Exception occurred in fillCoverageDetails method");
			throw e;
		}
	}
	
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Premium page.
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
			blnStatus = actions.isLoaded("divCoverageDetails_PageTitle");
			if(blnStatus)
			{	
				actions.postTestStep("PASS","Coverage page Submission", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divPremium_PageTitle") || actions.isLoaded("divAdditionalIntrest_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Coverage page Submission", "Coverage page submitted successfully");
				}
				else
				{
					LOGGER.error("Object 'divPremium_PageTitle' not found.");
					actions.postTestStep("FAIL", "Coverages Page Submit - Exit", "Premium Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divCoverageDetails_PageTitle' not found.");
				actions.postTestStep("FAIL", "Coverages Page Submit - Exit", "Coverages Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in CoveragesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Coverages Page Submit - Exit", "Exception occurred in Coverages Page Submit method");
			throw e;
		}
		return blnStatus;
	}

}
