package com.epam.businesslibs;

import java.util.TreeMap;

import com.epam.driver.CoreDriverScript;

public class ThankYouPage extends CoreDriverScript {

	/* 	**********************Method Header******************************************************************
	Method Name					: Exit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Exit from ThanYou page.
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
	public void Exit() throws Exception {
		// TODO Auto-generated method stub
		try{
			if(!actions.isLoaded("divThankYou_PageTitle")){
				actions.postTestStep("FAIL", "Thank You Page - Exit", "Thank You Page not found");
				throw new Exception();
			}
			actions.postTestStep("PASS", "Thank You Page - Exit", "Clicking Exit button..");
//			if(dataMap.get("").toUpperCase().contains("Y")){	// can be modified in future if other button would need to be clicked
				actions.click("btnThankYou_Exit");
//			}

			if(!actions.isLoaded("divThankYou_PageTitle")){
				actions.postTestStep("PASS", "Thank You Page - Exit", "Exit from Thank You Page successful");
			}else{
				actions.postTestStep("FAILED", "Thank You Page - Exit", "Exit from Thank You failed");
			}
			
		}catch(Exception e){
			actions.postTestStep("FAIL", "Thank You Page - Exit", "Exception occurred...");
			e.printStackTrace();
			throw e;
		}
	}

}
