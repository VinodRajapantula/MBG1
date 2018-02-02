package com.epam.businesslibs;

import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.epam.driver.CoreDriverScript;

public class ViolationsPage extends CoreDriverScript{

	private static final Logger LOGGER = Logger.getLogger(ViolationsPage.class);
	
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Vehicles page.
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
			blnStatus = actions.isLoaded("divViolations_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Violations page Submit", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divVehicals_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Violations page Submit", "Violations page submitted successfully");
				}
				else
				{
					LOGGER.error("Object 'divVehicals_PageTitle' not found.");
					actions.postTestStep("FAIL", "Violations Page Submit - Exit", "Vehicles Page not found");
					throw new Exception();
				}

			}
			else
			{
				LOGGER.error("Object 'divViolations_PageTitle' not found.");
				actions.postTestStep("FAIL", "Violations Page Submit - Exit", "Violations Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in ViolationsPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Violations Page Submit - Exit", "Exception occurred...");
			throw e;
		}

		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: Continue	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Continue button in Vehicles page.
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
	public boolean Continue() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divViolations_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Violations page Submit", "Clicking Continue...");
				actions.isLoaded("btnContinue");
				actions.click("btnContinue");
				if(actions.isLoaded("divViolations_PageTitle")){
					blnStatus= false;
					actions.postTestStep("PASS","Violations page Continue", "Violations page Continued successfully");
				}
				else
				{
					LOGGER.error("Object 'divViolations_PageTitle' not found.");
					actions.postTestStep("FAIL", "Violations Page Continue - Exit", "Violations Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divViolations_PageTitle' not found.");
				actions.postTestStep("FAIL", "Violations Page Continue - Exit", "Violations Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Continue method in ViolationsPage");
			actions.postTestStep("FAIL", "Violations Page Continue - Exit", "Exception occurred...");
			e.printStackTrace();
			throw e;
		}

		return blnStatus;
	}

	/* 	**********************Method Header******************************************************************
	Method Name					: AddViolationsAndLoss	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Add Violation and Loss
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
	public void AddViolationsAndLoss(TreeMap<String,String> dataMap)throws Exception{
		try{
			List<WebElement> lstDate,lstViolationType,lstViolationDrDesc,lstLossCoverage,lstLossDamageAmnt,lstLossStatus,lstFaultType,lstLossDetails,lstLossExclude,lstLossExcludeReason,btnLoss,btnAdd;

			Select oSelect;

			if(dataMap.get("VIOLATIONS_PAGE_LOSS").equalsIgnoreCase("YES"))
			{
				
				btnLoss=actions.findElements("btnAddLoss");
				btnLoss.get(btnLoss.size()-1).click();

				
				//actions.click("btnAddLoss");
				actions.WaitTillRender();

				lstDate=actions.findElements("txtLossDate");
				lstLossCoverage=actions.findElements("lstLossCoverage");
				lstLossDamageAmnt=actions.findElements("txtLossCollisionAmt");
				lstLossStatus=actions.findElements("lstLossStatus");
				lstFaultType=actions.findElements("lstLossFaultType");
				lstLossDetails=actions.findElements("txtLossDetails");
				lstLossExclude=actions.findElements("chkExcludeReason");
				lstLossExcludeReason=actions.findElements("txtExcludeReason");

				if(dataMap.get("LOSS_DATE")!= "")
				{
					lstDate.get(lstDate.size()-1).sendKeys(dataMap.get("LOSS_DATE"));
					actions.WaitTillRender();
				}
				//LOSS_EXCLUDE

				if(dataMap.get("LOSS_EXCLUDE")!= "")
				{
					lstLossExclude=actions.findElements("chkExcludeReason");
					if(!lstLossExclude.get(lstLossExclude.size()-1).isSelected())
					{
						lstLossExclude.get(lstLossExclude.size()-1).click();
						actions.WaitTillRender();
					}


				}


				if(dataMap.get("LOSS_EXCLUSIONREASON")!= "")
				{
					lstLossExcludeReason=actions.findElements("txtExcludeReason");
					lstLossExcludeReason.get(lstLossExcludeReason.size()-1).sendKeys(dataMap.get("LOSS_EXCLUSIONREASON"));
					actions.WaitTillRender();
				}

				//
				if(dataMap.get("LOSS_COVERAGETYPE")!= "")
				{
					lstLossCoverage=actions.findElements("lstLossCoverage");
					oSelect = new Select(lstLossCoverage.get(lstLossCoverage.size()-1));
					oSelect.selectByVisibleText(dataMap.get("LOSS_COVERAGETYPE"));
					actions.WaitTillRender();
					oSelect=null;
				}

				if(dataMap.get("LOSS_COVERAGEAMOUNT")!= "")
				{
					lstLossDamageAmnt=actions.findElements("txtLossCollisionAmt");
					lstLossDamageAmnt.get(lstLossDamageAmnt.size()-1).sendKeys(dataMap.get("LOSS_COVERAGEAMOUNT"));
					actions.WaitTillRender();
				}

				if(dataMap.get("LOSS_STATUS")!= "")
				{
					lstLossStatus=actions.findElements("lstLossStatus");
					oSelect = new Select(lstLossStatus.get(lstLossStatus.size()-1));
					oSelect.selectByVisibleText(dataMap.get("LOSS_STATUS"));
					actions.WaitTillRender();
					oSelect=null;

				}

				if(dataMap.get("LOSS_FAULTTYPE")!= "")
				{
					lstFaultType=actions.findElements("lstLossFaultType");
					oSelect = new Select(lstFaultType.get(lstFaultType.size()-1));
					oSelect.selectByValue(dataMap.get("LOSS_FAULTTYPE"));
					actions.WaitTillRender();
					oSelect=null;
				}

				if(dataMap.get("LOSS_LOSSDETAILS")!= "")
				{
					lstLossDetails=actions.findElements("txtLossDetails");
					lstLossDetails.get(lstLossDetails.size()-1).sendKeys(dataMap.get("LOSS_LOSSDETAILS"));
					actions.WaitTillRender();
				}

			}
			if(dataMap.get("VIOLATIONS_PAGE_ADD").equalsIgnoreCase("YES"))
			{
				btnAdd=actions.findElements("btnAddViolation");
				btnAdd.get(btnAdd.size()-1).click();
				//actions.click("btnAddViolation");

				actions.WaitTillRender();

				lstDate=actions.findElements("txtLossDate");
				lstViolationType=actions.findElements("lstViolationType");
				lstViolationDrDesc=actions.findElements("txtDrivingRecordDescription");

				if(dataMap.get("VIOLATION_DATE")!= "")
				{
					lstDate.get(lstDate.size()-1).sendKeys(dataMap.get("VIOLATION_DATE"));
					actions.WaitTillRender();
				}

				if(dataMap.get("VIOLATION_TYPE")!= "")
				{

					oSelect = new Select(lstViolationType.get(lstViolationType.size()-1));
					oSelect.selectByVisibleText(dataMap.get("VIOLATION_TYPE"));
					actions.WaitTillRender();
					oSelect=null;
				}

				if(dataMap.get("VIOLATION_DRIVERRECORD_DESCRIPTION")!= "")
				{
					lstViolationDrDesc.get(lstViolationDrDesc.size()-1).sendKeys(dataMap.get("VIOLATION_DRIVERRECORD_DESCRIPTION"));
					actions.WaitTillRender();
				}

			}
			actions.postTestStep("PASS","Violations - AddViolationsAndLoss", "Entered data...");
		}
		catch(Exception e){
			LOGGER.error("Exception in Add Loss method in  Violations page");			
			actions.postTestStep("FAIL", "Violations Page AddViolationsAndLoss - Exit", "Exception occurred...");
			e.printStackTrace();			
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: MVROverride	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To select MVR Override Check box
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
	
	public void MVROverride(TreeMap<String, String> dataMap)throws Exception {
		// TODO Auto-generated method stub
		try{
			if(!actions.isLoaded("divViolations_PageTitle")){
				actions.postTestStep("FAIL", "MVR Manual Override", "Violations page expected but not available");
				throw new Exception();				
			}
			
			if(dataMap.get("VIOLATIONS_MVROVERRIDE").equalsIgnoreCase("ALL")){
				List<WebElement> weColl = actions.findElements("chkViolations_MVROverride");
				if(weColl.size()==0){
					actions.postTestStep("FAIL", "MVR Manual Override", "MVR Manual Override check boxes not found on the Violation page");
					throw new Exception();	
				}
				for(WebElement we:weColl){
					if(we.isDisplayed() && !we.isSelected()){
						we.click();	
						actions.WaitTillRender();
					}
				}
				actions.postTestStep("PASS", "MVR Manual Override", "MVR Manual Override is selected for all drivers on Violations page");
			}else if(dataMap.get("VIOLATIONS_MVROVERRIDE").contains(";")){ //to handle list of specific drivers. To be coded when needed
				
			}
			
		}catch(Exception e){
			actions.postTestStep("FAIL", "MVR Manual Override", "Error occured...");
			e.printStackTrace();
			throw e;
		}
	}

}
