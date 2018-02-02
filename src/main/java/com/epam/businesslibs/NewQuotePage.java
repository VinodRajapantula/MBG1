package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;

public class NewQuotePage extends CoreDriverScript {


	private static final Logger LOGGER = Logger.getLogger(NewQuotePage.class);	

	/** 
	 *  Login : Login to application
	 *  @param TreeMap
	 *  throws exception 
	 */ 
	/* 	**********************Method Header******************************************************************
	Method Name					: FillNewQuoteDetails	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Fill NewQuote Details
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
	public void FillNewQuoteDetails(TreeMap<String,String> dataMap) throws Exception
	{
		try{

			actions.isLoaded("btnNewQuote_NewQuote");
			actions.click("btnNewQuote_NewQuote");
			actions.isLoaded("lstNewQuote_Producer");

			if(dataMap.get("AGENCY") != null && dataMap.get("AGENCY") != "")
			{
				actions.selectOptionByText("lstNewQuote_Agency", dataMap.get("AGENCY"));
				actions.WaitTillRender();
			}
			
			/*if(dataMap.get("PRODUCER") != "")
			{
				actions.selectOptionByText("lstNewQuote_Producer", dataMap.get("PRODUCER"));
			}*/
			if(dataMap.get("EFF_DATE") != "")
			{
				actions.type("txtNewQuote_EffDate", dataMap.get("EFF_DATE"));
			}
			if(dataMap.get("LINE") != "")
			{
				actions.selectOptionByText("lstNewQuote_Line", dataMap.get("LINE"));
				actions.WaitTillRender();
			}
			if(dataMap.get("STATE") != "")
			{
				actions.waitForDropdownToLoad("lstNewQuote_State", dataMap.get("STATE"), 0);
				actions.selectOptionByText("lstNewQuote_State", dataMap.get("STATE"));
				actions.WaitTillRender();
			}
			if(dataMap.get("USE_TRANSACT") != "")
			{
				actions.selectOptionByText("lstNewQuote_UseTransact", dataMap.get("USE_TRANSACT"));
			}
			/*if(dataMap.get("PROCISION") != "")
			{
				actions.selectOptionByText("lstNewQuote_Procision", dataMap.get("PROCISION"));
			}*/
			if(dataMap.get("QUOTE_TYPE") != "")
			{
				actions.selectOptionByText("lstNewQuote_QuoteType", dataMap.get("QUOTE_TYPE"));
			}
			
			actions.postTestStep("PASS","New Quote page ","Submitting the page...");
			actions.click("btnNewQuote_StartQuote");

			System.out.println(actions.getText("divCommon_PageTitle"));

			if(actions.getText("divCommon_PageTitle").contains("Applicant"))
			{
				LOGGER.info("New Quote created Successfully");
				actions.postTestStep("PASS","New Quote page ","New Quote page data entered successfully");
			}
			else
			{
				LOGGER.error("new quote page filling Failed");
				actions.postTestStep("FAIL","New Quote page","Failed to enter data in New Quote page");				
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in FillNewQuoteDetails method in NewQuotePage");
			e.printStackTrace();
			throw e;
		 }
	}
}
