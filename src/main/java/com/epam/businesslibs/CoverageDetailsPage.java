package com.epam.businesslibs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.TestDataReader;

public class CoverageDetailsPage extends CoreDriverScript {


	private static final Logger LOGGER = Logger.getLogger(CoverageDetailsPage.class);
	/* 	**********************Method Header******************************************************************
	Method Name					: AddCoverageDetails	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Add Coverage Details
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

	public void AddCoverageDetails(TreeMap<String,String> dataMap) throws Exception 
	{
		List<WebElement> lstDetails	= actions.findElements("lnkCoverageDetails");

		try
		{

			String strCoverageCount = dataMap.get("COVERAGE_DATA");
			if(strCoverageCount.contains("#")){
				String[] arrCoverageDetails=strCoverageCount.split("#");
				for(int arrIndex=0; arrIndex<arrCoverageDetails.length;arrIndex++)
				{
					if(arrCoverageDetails[arrIndex].toUpperCase().contains("ADD="))
					{
						strCoverageCount=arrCoverageDetails[arrIndex].split("=")[1];
						break;
					}
				}
			}
			String[] arrCoverageCount = strCoverageCount.split("-");

			for(int iCounter = 0;iCounter<arrCoverageCount.length;iCounter++)
			{
				Map<String, String> coverageDetailsMap = new HashMap<String,String>();
				coverageDetailsMap = TestDataReader.gFunc_ReadTestData("Vehicles",arrCoverageCount[iCounter]);
				System.out.println(coverageDetailsMap);

				List<WebElement> lstDrivers	= actions.findElements("divAssignedDriver");

				List<WebElement> lstLinkDetails = actions.findElements("lnkCoverageDetails");

				/*for(int i=0;i<lstDrivers.size();i++)
				{*/
				if(lstDrivers.get(iCounter).getText().length() == 0)
				{

					lstLinkDetails.get(iCounter).click();

					if(coverageDetailsMap.get("COVERAGE_ASSIGNED_DRIVER") != "")
					{
						actions.selectOptionByText("lstAssignedDriver",coverageDetailsMap.get("COVERAGE_ASSIGNED_DRIVER"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAssignedDriver' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_OPERATOR_STATUS") != "")
					{
						actions.selectOptionByText("lstOperatorStatus",coverageDetailsMap.get("COVERAGE_OPERATOR_STATUS"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstOperatorStatus' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_COMP_DED") != "")
					{
						actions.selectOptionByText("lstcompDed",coverageDetailsMap.get("COVERAGE_COMP_DED"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstcompDed' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_COLL_DED") != "")
					{
						actions.selectOptionByText("lstCollDed",coverageDetailsMap.get("COVERAGE_COLL_DED"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstCollDed' Data updated Successfully.");}
					}

					/*	if(coverageDetailsMap.get("COVERAGE_AU-00-15_AUTO_ADVANTAGE") != "")
						{
							actions.check("chkAU0015AutoAdvantage");
							if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAU0015AutoAdvantage' Data updated Successfully.");}
						}*/

					if(coverageDetailsMap.get("COVERAGE_TOWING") != "")
					{
						actions.selectOptionByText("lstTowing",coverageDetailsMap.get("COVERAGE_TOWING"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstTowing' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_RENTAL") != "")
					{
						actions.selectOptionByText("lstRental",coverageDetailsMap.get("COVERAGE_RENTAL"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstRental' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_EXCLUDE_LIABILITY").equalsIgnoreCase("YES"))
					{
						actions.check("chkExcludeLibility");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkExcludeLibility' Data updated Successfully.");}
						
						actions.WaitTillRender();
						if(coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON")!=""){
							actions.selectOptionByText("lstExcludeReason",coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExcludeReason' Data updated Successfully.");}
						}
						
						actions.WaitTillRender();
						
						if(coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON_Description")!=""){
							actions.type("txtExcludeReasonDescription",coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON_Description"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExcludeReason' Data updated Successfully.");}
						}
						
					}
					

					if(coverageDetailsMap.get("COVERAGE_REPLACEMENT_COST_COVERAGE") != "")
					{
						actions.check("chkReplacementCostcoverage");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkReplacementCostcoverage' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_LIMITED_MEXICO_COVERAGE") != "")
					{
						actions.check("chkLimitedMexicoCoverage");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkLimitedMexicoCoverage' Data updated Successfully.");}
					}
					
					actions.postTestStep("PASS", "Coverage Details Page - AddCoverageDetails", "clicking OK...");
					
					actions.click("btnOK");
					
					if(! actions.isLoaded("lnkCoverageDetails")){
						actions.postTestStep("FAIL", "Coverage Details Page - AddCoverageDetails", "After clicking OK, application is not in Coverage Details page");
					}else{
						actions.postTestStep("PASS", "Coverage Details Page - AddCoverageDetails", "After clicking OK, application is in Coverage Details page");
					}
						

					//}
				}
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in AddCoverageDetails method in CoverageDetailsPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Coverage Details Page - AddCoverageDetails", "Exception occurred in Coverage Details Page AddCoverageDetails method");
			throw e;
		}

	}
	/* 	**********************Method Header******************************************************************
	Method Name					: updateCoverageDetails	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To update Coverage Details
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
	public void updateCoverageDetails(TreeMap<String,String> dataMap) throws Exception
	{
		boolean blnStatus = false;

		try
		{
			
			blnStatus = actions.isLoaded("lnkCoverageDetails");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Coverages Page");
				actions.postTestStep("FAIL", "Coverages Details Page - Exit", "Coverage Details Page not found");
				throw new Exception();
			}
			
			String strCoverageCount = dataMap.get("COVERAGE_DATA");
			if(strCoverageCount.contains("#")){
				String[] arrCoverageDetails=strCoverageCount.split("#");
				for(int arrIndex=0; arrIndex<arrCoverageDetails.length;arrIndex++)
				{
					if(arrCoverageDetails[arrIndex].toUpperCase().contains("UPDATE="))
					{
						strCoverageCount=arrCoverageDetails[arrIndex].split("=")[1];
						break;
					}
				}
			}
			String[] arrCoverages= strCoverageCount.split("-");

			for(int iCounter = 0;iCounter<arrCoverages.length;iCounter++)
			{
				Map<String, String> coverageDetailsMap = new HashMap<String,String>();
				coverageDetailsMap = TestDataReader.gFunc_ReadTestData("Vehicles",arrCoverages[iCounter]);
				System.out.println(coverageDetailsMap);

				List<WebElement> lstDrivers	= actions.findElements("divAssignedDriver");

				List<WebElement> lstLinkDetails = actions.findElements("lnkCoverageDetails");

				/*for(int i=0;i<lstDrivers.size();i++)
				{*/

				if(lstDrivers.get(iCounter).getText().contains(coverageDetailsMap.get("COVERAGE_ASSIGNED_DRIVER")))
				{

					lstLinkDetails.get(iCounter).click();
					actions.isLoaded("lstOperatorStatus");

					if(coverageDetailsMap.get("COVERAGE_ASSIGNED_DRIVER") != "")
					{
						actions.selectOptionByText("lstAssignedDriver",coverageDetailsMap.get("COVERAGE_ASSIGNED_DRIVER"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAssignedDriver' Data updated Successfully.");}
					}
					
					if(coverageDetailsMap.get("NEW_ASSIGNED_DRIVER") != "")
					{
						actions.selectOptionByText("lstAssignedDriver",coverageDetailsMap.get("NEW_ASSIGNED_DRIVER"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAssignedDriver' Data updated Successfully.");}
					}


					if(coverageDetailsMap.get("COVERAGE_OPERATOR_STATUS") != "")
					{
						actions.selectOptionByText("lstOperatorStatus",coverageDetailsMap.get("COVERAGE_OPERATOR_STATUS"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstOperatorStatus' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_COMP_DED") != "")
					{
						actions.selectOptionByText("lstcompDed",coverageDetailsMap.get("COVERAGE_COMP_DED"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstcompDed' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_COLL_DED") != "")
					{
						actions.selectOptionByText("lstCollDed",coverageDetailsMap.get("COVERAGE_COLL_DED"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstCollDed' Data updated Successfully.");}
					}

					/*	if(coverageDetailsMap.get("COVERAGE_AU-00-15_AUTO_ADVANTAGE") != "")
						{
							actions.check("chkAU0015AutoAdvantage");
							if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAU0015AutoAdvantage' Data updated Successfully.");}
						}*/

					if(coverageDetailsMap.get("COVERAGE_TOWING") != "")
					{
						actions.selectOptionByText("lstTowing",coverageDetailsMap.get("COVERAGE_TOWING"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstTowing' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_RENTAL") != "")
					{
						actions.selectOptionByText("lstRental",coverageDetailsMap.get("COVERAGE_RENTAL"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstRental' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_EXCLUDE_LIABILITY").equalsIgnoreCase("YES"))
					{
						actions.check("chkExcludeLibility");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkExcludeLibility' Data updated Successfully.");}
						
						actions.WaitTillRender();
						if(coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON")!=""){
							actions.selectOptionByText("lstExcludeReason",coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExcludeReason' Data updated Successfully.");}
						}
						
						actions.WaitTillRender();
						
						if(coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON_Description")!=""){
							actions.type("txtExcludeReasonDescription",coverageDetailsMap.get("COVEREAGE_EXCLUDE_REASON_Description"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstExcludeReason' Data updated Successfully.");}
						}
						
					}

					if(coverageDetailsMap.get("COVERAGE_REPLACEMENT_COST_COVERAGE") != "")
					{
						actions.check("chkReplacementCostcoverage");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkReplacementCostcoverage' Data updated Successfully.");}
					}

					if(coverageDetailsMap.get("COVERAGE_LIMITED_MEXICO_COVERAGE") != "")
					{
						actions.check("chkLimitedMexicoCoverage");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'chkLimitedMexicoCoverage' Data updated Successfully.");}
					}
					
					actions.postTestStep("PASS", "Coverage Details Page - AddCoverageDetails", "Clicking OK...");
					
					actions.click("btnOK");
					actions.WaitTillRender();

					if(! actions.isLoaded("lnkCoverageDetails")){
						actions.postTestStep("FAIL", "Coverage Details Page - AddCoverageDetails", "After clicking OK, application is not in Coverage Details page");
					}else{
						actions.postTestStep("PASS", "Coverage Details Page - AddCoverageDetails", "After clicking OK, application is in Coverage Details page");
					}
				}
			}
		//}
	}
	catch (Exception e) {					
		LOGGER.error("Exception in updateCoverageDetails method in CoverageDetailsPage");
		e.printStackTrace();
		actions.postTestStep("FAIL", " Coverages Details Page - Exit", "Exception occurred in Update Coverage Details method");
		throw e;
	}
}
}

