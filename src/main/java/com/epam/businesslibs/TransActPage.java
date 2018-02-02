package com.epam.businesslibs;

import java.util.TreeMap;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Constants;

public class TransActPage extends CoreDriverScript {

	
	/* 	**********************Method Header******************************************************************
	Method Name					: SelectTransaction	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To select Transaction in TransAct Page.
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
	public void SelectTransaction(TreeMap<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		try{
			if(!actions.isLoaded("divTransAct_PageTitle")){
				actions.postTestStep("FAIL", "Thank You Page - Exit", "Thank You Page not found");
				throw new Exception();
			}
				actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Selecting Transaction type..");
				actions.selectOptionByText("lstTransAct_TransactionType", "Endorse");
				actions.click("lnkTransAct_Go");
				
				if(config.getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("firefox")|| config.getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("chrome")){
					actions.switchToFrame(0);
				}else{
					actions.switchToFrame(1);
				}
				if(dataMap.get("TRANSACT_EFFECTIVEDATE") != ""){
					actions.type("txtTransActFrame_EffectiveDate", dataMap.get("TRANSACT_EFFECTIVEDATE"));
				}
				
				if(dataMap.get("TRANSACT_REASON")!= ""){
					actions.selectOptionByText("lstTransActFrame_Reason", dataMap.get("TRANSACT_REASON"));
				}
				
				if(dataMap.get("TRANSACT_DETAIL") != ""){
					actions.selectOptionByText("lstTransActFrame_Detail", dataMap.get("TRANSACT_DETAIL"));
					actions.WaitTillRender();
				}
				
				actions.click("lnkTransActFrame_OK");
				driver.switchTo().defaultContent();
				

			if(actions.isLoaded("divApplicant_PageTitle")){
				actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Transaction selected successful");
			}
			
		}catch(Exception e){
			actions.postTestStep("FAIL", "TransAct Page - SelectTransAct", "Exception occurred...");
			e.printStackTrace();
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: EditPolicy	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Edit the Created Policy.
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
	public void EditPolicy() throws Exception {
		// TODO Auto-generated method stub
		try{
			if(!actions.isLoaded("divTransAct_PageTitle")){
				actions.postTestStep("FAIL", "Thank You Page - Exit", "Thank You Page not found");
				throw new Exception();}
				actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Clicking Edit policy.." );
				actions.click("lnkTransAct_EditPolicy");
				actions.WaitTillRender();

				if(actions.isLoaded("divApplicant_PageTitle")){
					actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Transaction selected successful");
				}else if(actions.isLoaded("divSubmission_PageTitle")){
					actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Transaction selected successful");
				}else{
					if(actions.isLoaded("lnkTransAct_EditPolicy")){
						actions.click("lnkTransAct_EditPolicy");
						actions.WaitTillRender();
					}
					if(actions.isLoaded("divApplicant_PageTitle")){
						actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Transaction selected successful");
					}else if(actions.isLoaded("divSubmission_PageTitle")){
						actions.postTestStep("PASS", "TransAct Page - SelectTransAct", "Transaction selected successful");
					}else{
						actions.postTestStep("FAIL", "TransAct Page - SelectTransAct", "Applicant page not displayed after clicking Edit Policy");
						throw new Exception();
					}
				}
				
			}catch(Exception e){
				actions.postTestStep("FAIL", "TransAct Page - SelectTransAct", "Exception occurred...");
				e.printStackTrace();
				throw e;
			}
	}

}
