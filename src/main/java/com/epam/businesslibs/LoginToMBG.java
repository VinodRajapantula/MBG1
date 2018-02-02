package com.epam.businesslibs;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;

public class LoginToMBG extends CoreDriverScript{

	private static final Logger LOGGER = Logger.getLogger(LoginToMBG.class);	

	/* 	**********************Method Header******************************************************************
	Method Name					: LoginAsAgent	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Login To MBG as Agent
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

	public void LoginAsAgent(TreeMap<String,String> dataMap) throws Exception
	{
		try{
			actions.isLoaded("txtLogin_UserName");
			actions.clearText("txtLogin_UserName");
			
			//Enter UserName
			if(dataMap.get("USERNAME") != "")
			{
				actions.type("txtLogin_UserName",dataMap.get("USERNAME"));
			}
			//Enter Password
			if(dataMap.get("PASSWORD") != "")
			{
				actions.clearText("txtLogin_Password");
				actions.type("txtLogin_Password", dataMap.get("PASSWORD"));
			}
			//Select ServerDetails
			/*if(dataMap.get("SERVERSELECTION") != "")
			{
				actions.selectOptionByText("lstLogin_Server", dataMap.get("SERVERSELECTION"));
			}
			
			actions.postTestStep("PASS","MBG Login","Clicking Submit");*/
			
			//Click on Submit Button
			actions.click("btnLogin_Submit");

			//Verification of Successful login
			if(actions.isLoaded("divSelectAgency_PageTitle"))
			{
				LOGGER.info("Logged into MBG Successfully");
				actions.postTestStep("PASS","MBG Login","Successfully logged in to MBG");
			}
			else
			{
				LOGGER.error("Logged into MBG Failed");
				actions.postTestStep("FAIL","MBG Login","Failed to log in to MBG");				
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in LoginAsAgent method in  LoginToMBG page");			
			e.printStackTrace();
			actions.postTestStep("FAIL", "LoginAsAgent - Exit", "Exception occurred in LoginAsAgent method");
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: LoginAsUW	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Login To MBG as UnderWriter
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


	public void LoginAsUW(TreeMap<String,String> dataMap) throws Exception
	{
		try{
			actions.isLoaded("txtLogin_UserName");
			actions.clearText("txtLogin_UserName");
			
			//Enter UserName
			if(dataMap.get("UW_USERNAME") != "")
			{
				actions.type("txtLogin_UserName",dataMap.get("UW_USERNAME"));
			}
			//Enter Password
			if(dataMap.get("UW_PASSWORD") != "")
			{
				actions.clearText("txtLogin_Password");
				actions.type("txtLogin_Password", dataMap.get("UW_PASSWORD"));
			}
			//Select ServerDetails
/*			if(dataMap.get("SERVERSELECTION") != "")
			{
				actions.selectOptionByText("lstLogin_Server", dataMap.get("SERVERSELECTION"));
			}
			
			actions.postTestStep("PASS","MBG Login","Clicking Submit");*/
			
			//Click on Submit Button
			actions.click("btnLogin_Submit");

			//Verification of Successful login
			if(actions.isLoaded("divSelectAgency_PageTitle"))
			{
				LOGGER.info("Logged into MBG Successfully");
				actions.postTestStep("PASS","MBG Login","Successfully logged in to MBG");
			}
			else
			{
				LOGGER.error("Logged into MBG Failed");
				actions.postTestStep("FAIL","MBG Login","Failed to log in to MBG");				
			}
		}
		catch(Exception e){
			LOGGER.error("Exception in LoginAsUW method in  LoginToMBG page");			
			e.printStackTrace();
			actions.postTestStep("FAIL", "LoginAsUW - Exit", "Exception occurred in LoginAsUW method");
			throw e;
		}
	}

}
