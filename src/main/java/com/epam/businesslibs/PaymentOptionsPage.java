package com.epam.businesslibs;

import java.util.TreeMap;

import com.epam.driver.CoreDriverScript;

public class PaymentOptionsPage extends CoreDriverScript{

	/* 	**********************Method Header******************************************************************
	Method Name					: Continue	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To click on Continue in Payment page and Navigate to ThankYou Page.
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
	public void Continue(TreeMap<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stubs
		try{
			if(!actions.isLoaded("divPaymentOptions_PageTitle")){
				actions.postTestStep("FAIL", "Payment Options Page - Continue", "Payment Options Page not found");
				throw new Exception();
			}
				actions.postTestStep("PASS", "Continue without Payment", "Clicking Continue button..");
				actions.click("btnPaymentOptions_ContinueWOPayment");
			
			if(!actions.isLoaded("divThankYou_PageTitle")){				
				actions.postTestStep("FAIL", "Continue without Payment", "Not navigated to the Thank You Page..");
				throw new Exception();
			}else{
				actions.postTestStep("PASS", "Continue without Payment", "Navigated to the Thank You Page..");
			}
			
		}catch(Exception e){
			blnExecuteReRef=false;
			actions.postTestStep("FAIL", "Payment Options Page - Continue", "Exception occurred");
			e.printStackTrace();
			throw e;
		}
	}

}
