package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;

public class SelectAgencyPage extends CoreDriverScript{

	private static final Logger LOGGER = Logger.getLogger(SelectAgencyPage.class);	

	/* 	**********************Method Header******************************************************************
	Method Name					: SelectAgency	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To select an agency
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

	public void SelectAgency(TreeMap<String,String> dataMap) throws Exception
	{
		try{
//			actions.isLoaded("lstSelectAgency");
						
			//Select an agency
//			if(dataMap.get("SERVERSELECTION") != "")
			{
//				actions.selectOptionByText("lstSelectAgency", "CARRIAGE HOUSE INSURANCE INC - m8980,							404 WASHINGTON STREET  ,							PA,							16652-1525");
				actions.selectOptionByValue("lstSelectAgency", "168");
			}
			
			actions.postTestStep("PASS","Select Agency","Clicking Continue");
			
			//Click on Submit Button
			actions.click("btnContinue");

			//Verification of Successful page submission
			if(actions.isLoaded("btnNewQuote_NewQuote"))
			{
				LOGGER.info("Logged into MBG Successfully");
				actions.postTestStep("PASS","SelectAgency","Successfully selected agency");
			}
			else
			{
				LOGGER.error("Logged into MBG Failed");
				actions.postTestStep("FAIL","SelectAgency","Failed to select agency");				
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in SelectAgency method in  SelectAgencyPage");			
			e.printStackTrace();
			actions.postTestStep("FAIL", "SelectAgency - Exit", "Exception occurred in SelectAgency method");
			throw e;
		}
	}
	
	public void SelectAgencyInUW(TreeMap<String,String> dataMap) throws Exception
	{
		try{
//			actions.isLoaded("lstSelectAgency");
						
			//Select an agency
//			if(dataMap.get("SERVERSELECTION") != "")
			{
//				actions.selectOptionByText("lstSelectAgency", "CARRIAGE HOUSE INSURANCE INC - m8980,							404 WASHINGTON STREET  ,							PA,							16652-1525");
				actions.selectOptionByValue("lstSelectAgency", "1");
			}
			
			actions.postTestStep("PASS","Select Agency","Clicking Continue");
			
			//Click on Submit Button
			actions.click("btnContinue");

			//Verification of Successful page submission
			if(actions.isLoaded("btnNewQuote_NewQuote"))
			{
				LOGGER.info("Logged into MBG Successfully");
				actions.postTestStep("PASS","SelectAgency","Successfully selected agency");
			}
			else
			{
				LOGGER.error("Logged into MBG Failed");
				actions.postTestStep("FAIL","SelectAgency","Failed to select agency");				
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in SelectAgency method in  SelectAgencyPage");			
			e.printStackTrace();
			actions.postTestStep("FAIL", "SelectAgency - Exit", "Exception occurred in SelectAgency method");
			throw e;
		}
	}
	

}
