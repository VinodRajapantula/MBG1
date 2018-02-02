package com.epam.businesslibs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.fontbox.ttf.PostScriptTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.TestDataReader;

public class DriversPage extends CoreDriverScript {

	private static final Logger LOGGER = Logger.getLogger(DriversPage.class);	
	
	/* 	**********************Method Header******************************************************************
	Method Name					: AddDrivers	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Add new Drivers
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
	public void AddDrivers(TreeMap<String,String> dataMap) throws Exception
	{
		try{
			boolean blnStatus = false;

			blnStatus = actions.isLoaded("divDrivers_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Drivers Page");
			}
			String strDriverCount = dataMap.get("DRIVER_DATA");

			String[] strDrivers = strDriverCount.split("-");
			
			if(!actions.isLoaded("lnkDetail")){
				driver.navigate().refresh();				
			};
			
			if(actions.isLoaded("lnkDetail")){
			
			
			for(int iCounter = 0;iCounter<strDrivers.length;iCounter++)
			{
				Map<String, String> objRowData = new HashMap<String,String>();
				objRowData = TestDataReader.gFunc_ReadTestData("Drivers",strDrivers[iCounter]);
				System.out.println(objRowData);
				actions.WaitTillRender();
				
				if(!actions.isLoaded("divLicenseNumber")){
					driver.navigate().refresh();				
				};
				
				actions.waitForElementPresent("divLicenseNumber", 10);
				if(actions.getText("divLicenseNumber").length() == 0)
				{	
					actions.postTestStep("PASS","Drivers page - AddDrivers ", "Clicking Details link...");
					actions.click("lnkDetail");
				}
				else
				{
					actions.postTestStep("PASS","Drivers page - AddDrivers ", "Clicking Add Driver link...");					
					actions.click("btnAddDriver");
				}

				if(objRowData.get("DRIVERS_FIRST_NAME") !="")
				{
					actions.type("txtDriverFirstName",objRowData.get("DRIVERS_FIRST_NAME"));
				}

				if(objRowData.get("DRIVERS_MI") !="")
				{
					actions.type("txtDriverMI",objRowData.get("DRIVERS_MI"));
				}

				if(objRowData.get("DRIVERS_LAST_NAME") !="")
				{
					actions.type("txtDriverLastName",objRowData.get("DRIVERS_LAST_NAME"));
				}

				if(objRowData.get("DRIVERS_DATE_OF_BIRTH") !="")
				{
					actions.type("txtDriverDateOfBirth",objRowData.get("DRIVERS_DATE_OF_BIRTH"));
				}

				if(objRowData.get("DRIVERS_GENDER") !="")
				{
					actions.selectOptionByText("lstGender",objRowData.get("DRIVERS_GENDER"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_MARITAL_STATUS") !="")
				{
					actions.selectOptionByText("lstMaritalStatus",objRowData.get("DRIVERS_MARITAL_STATUS"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_RELATION_TO_APPLICANT") !="")
				{
					actions.selectOptionByText("lstRelationToApplicant",objRowData.get("DRIVERS_RELATION_TO_APPLICANT"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_DATE_LICENSED") !="")
				{
					actions.type("txtDateLicensed",objRowData.get("DRIVERS_DATE_LICENSED"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_OCCUPATION") !="")
				{
					actions.selectOptionByText("lstOccupation",objRowData.get("DRIVERS_OCCUPATION"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_SSN1") !="")
				{
					actions.type("txtDriver_SocialSecurityNumber1",objRowData.get("DRIVERS_SSN1"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_SSN2") !="")
				{
					actions.type("txtDriver_SocialSecurityNumber2",objRowData.get("DRIVERS_SSN2"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_SSN3") !="")
				{
					actions.type("txtDriver_SocialSecurityNumber3",objRowData.get("DRIVERS_SSN3"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_LICENSE_STATE") !="")
				{
					actions.selectOptionByText("lstLicenseState",objRowData.get("DRIVERS_LICENSE_STATE"));
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") !="")
				{
					actions.type("txtDriversLicenseNumber",objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER"));
					actions.WaitTillRender();
				}
				actions.WaitTillRender();
				
				if(objRowData.get("DRIVERS_DRIVER_TRAINING")!="")
				{	
					actions.WaitTillRender();
					actions.waitForElementPresent("chkDriverTraining", 10);

					if(actions.isLoaded("chkDriverTraining")){
						actions.click("chkDriverTraining");
						actions.WaitTillRender();

					}
				}
				
				if(objRowData.get("DRIVERS_DISTANT_STUDENT")!="")
				{	
					actions.WaitTillRender();
					actions.waitForElementPresent("chkDistantStudent", 10);

					if(actions.isLoaded("chkDistantStudent")){

						System.out.println("Element is Loaded");

						actions.click("chkDistantStudent");
						actions.WaitTillRender();

					}
					if(actions.isLoaded("lstDistantStudentVehicleStatus"))
					{
						actions.waitForDropdownToLoad("lstDistantStudentVehicleStatus",objRowData.get("DRIVERS_DISTANT_STUDENT_STATUS"), 10);
						actions.selectOptionByText("lstDistantStudentVehicleStatus",objRowData.get("DRIVERS_DISTANT_STUDENT_STATUS"));
						actions.WaitTillRender();
					}
				}
				
					
				if(objRowData.get("DRIVERS_GOOD_STUDENT") !="")
				{
					actions.check("chkGoodStudent");
					actions.WaitTillRender();
				}

				if(objRowData.get("DRIVERS_REPORT_CARD_DATE") !="")
				{
					actions.type("txtReportCardDate",objRowData.get("DRIVERS_REPORT_CARD_DATE"));
					actions.WaitTillRender();
				}				

				if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER") !="")
				{
					if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("YES"))
					{
							actions.check("chkDontIncludeAsAssignedDriver");
							
							if(objRowData.get("REASON_INCLUDE_ASSINGED_DRIVER") !="")
							{
								actions.selectOptionByText("lstAssignedReason",objRowData.get("REASON_INCLUDE_ASSINGED_DRIVER"));
								actions.WaitTillRender();
							}

							actions.WaitTillRender();
					}
					if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("NO"))
					{
							actions.unCheck("chkDontIncludeAsAssignedDriver");
							actions.WaitTillRender();
					}
				}
					
				if(objRowData.get("DRIVERS_DEFENSIVE_DRIVER") !="")
				{
					actions.check("chkDefenseDriver");
					actions.WaitTillRender();
				}
				
				actions.postTestStep("PASS","Drivers page - AddDrivers ", "Clicking OK...");
				actions.click("btnOK");

				if(actions.isLoaded("divDrivers_PageTitle"))
				{                                                   
					LOGGER.info("Drivers  Added Successfully");
					actions.postTestStep("PASS","Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is successful.");
				}
				else
				{
					LOGGER.error("Adding  Drivers  Failed");
					actions.postTestStep("FAIL","Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is failed.");				
				}

			}
			try{
				driver.findElement(By.xpath("//a[contains(text(),'Next')]")).isDisplayed();					
				}catch(NoSuchElementException nse){
					if(!actions.isLoaded("lnkDetail")){
						driver.navigate().refresh();				
					};
					actions.click("lnkDetail");				
					actions.click("btnOK");
				}
			}else{
				LOGGER.error("Adding  Drivers  Failed");
				actions.postTestStep("FAIL","Adding Driver" ,"Detail link not loaded even after refresh ");	
			}
		}

		catch(Exception e){
			LOGGER.error("Exception in AddDrivers method in  Drivers page");			
			e.printStackTrace();			
			throw e;
		}
	}
	
	/* 	**********************Method Header******************************************************************
	Method Name					: AddNamedInsuredDrivers	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Add named insured Drivers
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
	public void AddNamedInsuredDrivers(TreeMap<String,String> dataMap) throws Exception
	{
		try{
			boolean blnStatus = false;

			blnStatus = actions.isLoaded("divDrivers_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Drivers Page");
			}
			String strDriverCount = dataMap.get("DRIVER_DATA");

			String[] strDrivers = strDriverCount.split("-");
			if(!actions.waitForElementPresent("lnkDetail", 10))
			{
			CommonBusinessActions.NavigateToPage("Drivers");
			}

			for(int iCounter = 0;iCounter<strDrivers.length;iCounter++)
			{
				Map<String, String> objRowData = new HashMap<String,String>();
				objRowData = TestDataReader.gFunc_ReadTestData("Drivers",strDrivers[iCounter]);
				System.out.println(objRowData);
				actions.WaitTillRender();
				List<WebElement> lstLicenceNums	= actions.findElements("divLicenseNumber");
				List<WebElement> lstLinkDetails = actions.findElements("lnkDetailsLst");
			
					if(lstLicenceNums.size() == strDrivers.length)
					{
						lstLinkDetails.get(iCounter+1).click();
					}

					if(objRowData.get("DRIVERS_FIRST_NAME") !="")
					{
						actions.type("txtDriverFirstName",objRowData.get("DRIVERS_FIRST_NAME"));
					}

					if(objRowData.get("DRIVERS_MI") !="")
					{
						actions.type("txtDriverMI",objRowData.get("DRIVERS_MI"));
					}

					if(objRowData.get("DRIVERS_LAST_NAME") !="")
					{
						actions.type("txtDriverLastName",objRowData.get("DRIVERS_LAST_NAME"));
					}

					if(objRowData.get("DRIVERS_DATE_OF_BIRTH") !="")
					{
						actions.type("txtDriverDateOfBirth",objRowData.get("DRIVERS_DATE_OF_BIRTH"));
					}

					if(objRowData.get("DRIVERS_GENDER") !="")
					{
						actions.selectOptionByText("lstGender",objRowData.get("DRIVERS_GENDER"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_MARITAL_STATUS") !="")
					{
						actions.selectOptionByText("lstMaritalStatus",objRowData.get("DRIVERS_MARITAL_STATUS"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_RELATION_TO_APPLICANT") !="")
					{
						actions.selectOptionByText("lstRelationToApplicant",objRowData.get("DRIVERS_RELATION_TO_APPLICANT"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_DATE_LICENSED") !="")
					{
						actions.type("txtDateLicensed",objRowData.get("DRIVERS_DATE_LICENSED"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_OCCUPATION") !="")
					{
						actions.selectOptionByText("lstOccupation",objRowData.get("DRIVERS_OCCUPATION"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_SSN1") !="")
					{
						actions.type("txtDriver_SocialSecurityNumber1",objRowData.get("DRIVERS_SSN1"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_SSN2") !="")
					{
						actions.type("txtDriver_SocialSecurityNumber2",objRowData.get("DRIVERS_SSN2"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_SSN3") !="")
					{
						actions.type("txtDriver_SocialSecurityNumber3",objRowData.get("DRIVERS_SSN3"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_LICENSE_STATE") !="")
					{
						actions.selectOptionByText("lstLicenseState",objRowData.get("DRIVERS_LICENSE_STATE"));
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") !="")
					{
						actions.type("txtDriversLicenseNumber",objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER"));
						actions.WaitTillRender();
					}
					actions.WaitTillRender();

					if(objRowData.get("DRIVERS_DRIVER_TRAINING")!="")
					{	
						actions.WaitTillRender();
						actions.waitForElementPresent("chkDriverTraining", 10);

						if(actions.isLoaded("chkDriverTraining")){

							System.out.println("Element is Loaded");

							actions.click("chkDriverTraining");
							actions.WaitTillRender();

						}
					}

					if(objRowData.get("DRIVERS_DISTANT_STUDENT")!="")
					{	
						actions.WaitTillRender();
						actions.waitForElementPresent("chkDistantStudent", 10);

						if(actions.isLoaded("chkDistantStudent")){

							System.out.println("Element is Loaded");

							actions.click("chkDistantStudent");
							actions.WaitTillRender();

						}
						if(actions.isLoaded("lstDistantStudentVehicleStatus"))
						{
							actions.waitForDropdownToLoad("lstDistantStudentVehicleStatus",objRowData.get("DRIVERS_DISTANT_STUDENT_STATUS"), 10);
							actions.selectOptionByValue("lstDistantStudentVehicleStatus",objRowData.get("DRIVERS_DISTANT_STUDENT_STATUS"));
							actions.WaitTillRender();
						}
					}


					if(objRowData.get("DRIVERS_GOOD_STUDENT") !="")
					{
						actions.check("chkGoodStudent");
						actions.WaitTillRender();
					}

					if(objRowData.get("DRIVERS_REPORT_CARD_DATE") !="")
					{
						actions.type("txtReportCardDate",objRowData.get("DRIVERS_REPORT_CARD_DATE"));
						actions.WaitTillRender();
					}



					if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER") !="")
					{
						if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("YES"))
						{

							actions.check("chkDontIncludeAsAssignedDriver");

							if(objRowData.get("REASON_INCLUDE_ASSINGED_DRIVER") !="")
							{
								actions.selectOptionByText("lstAssignedReason",objRowData.get("REASON_INCLUDE_ASSINGED_DRIVER"));
								actions.WaitTillRender();
							}

							actions.WaitTillRender();
						}
						if(objRowData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("NO"))
						{
							actions.unCheck("chkDontIncludeAsAssignedDriver");
							actions.WaitTillRender();
						}
					}

					if(objRowData.get("DRIVERS_DEFENSIVE_DRIVER") !="")
					{
						actions.check("chkDefenseDriver");
						actions.WaitTillRender();
					}
					actions.postTestStep("PASS","AddNamedInsuredDrivers" ,"Clicking OK..." );
					actions.click("btnOK");
					blnStatus = actions.isLoaded("divDrivers_PageTitle");

					if(blnStatus)
					{                                                   
						LOGGER.info("Drivers  Added Successfully");
						actions.postTestStep("PASS","Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is successful.");
					}
					else
					{
						LOGGER.error("Adding  Drivers  Failed");
						actions.postTestStep("FAIL","Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Adding Driver with License number " + objRowData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is failed.");				
					}
					

				}
			//}
		}

		catch(Exception e){
			LOGGER.error("Exception in AddDrivers method in  Drivers page");			
			e.printStackTrace();			
			throw e;
		}
	}

	/* 	**********************Method Header******************************************************************
	Method Name					: UpdateDrivers	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To update the added Drivers
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
	public void UpdateDrivers(TreeMap<String, String> dataMap)throws Exception 
	{

		try{
			boolean blnStatus = false;

			blnStatus = actions.isLoaded("divDrivers_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Drivers Page");
			}

			String strDriverCount = dataMap.get("DRIVER_DATA");

			String[] strDrivers = strDriverCount.split("-");
			actions.isLoaded("lnkDetail");
			if(actions.isLoaded("lnkDetail")){

			for(int iCounter = 0;iCounter<strDrivers.length;iCounter++)
			{
				Map<String, String> objDriverData = new HashMap<String,String>();
				objDriverData = TestDataReader.gFunc_ReadTestData("Drivers",strDrivers[iCounter]);
				System.out.println(objDriverData);

				actions.isLoaded("divLicenseNumber");
				//Getting the available LicenceNumbers
				List<WebElement> lstLicenceNums	= actions.findElements("divLicenseNumber");
				List<WebElement> lstLinkDetails = actions.findElements("lnkDetailsLst");
				
				

				for(int i=0;i<lstLicenceNums.size();i++){
					if(lstLicenceNums.get(i).getText().equalsIgnoreCase(objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER"))){

//						lstLinkDetails.get(i+1).click();
						lstLinkDetails.get(i).click();

						if(objDriverData.get("DRIVERS_FIRST_NAME") !="")
						{
							actions.type("txtDriverFirstName",objDriverData.get("DRIVERS_FIRST_NAME"));
						}

						if(objDriverData.get("DRIVERS_MI") !="")
						{
							actions.type("txtDriverMI",objDriverData.get("DRIVERS_MI"));
						}

						if(objDriverData.get("DRIVERS_LAST_NAME") !="")
						{
							actions.type("txtDriverLastName",objDriverData.get("DRIVERS_LAST_NAME"));
						}

						if(objDriverData.get("DRIVERS_DATE_OF_BIRTH") !="")
						{
							actions.type("txtDriverDateOfBirth",objDriverData.get("DRIVERS_DATE_OF_BIRTH"));
						}

						if(objDriverData.get("DRIVERS_GENDER") !="")
						{
							actions.selectOptionByText("lstGender",objDriverData.get("DRIVERS_GENDER"));
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_MARITAL_STATUS") !="")
						{
							actions.selectOptionByText("lstMaritalStatus",objDriverData.get("DRIVERS_MARITAL_STATUS"));
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_RELATION_TO_APPLICANT") !="")
						{
							actions.selectOptionByText("lstRelationToApplicant",objDriverData.get("DRIVERS_RELATION_TO_APPLICANT"));
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_DATE_LICENSED") !="")
						{
							System.out.println(objDriverData.get("DRIVERS_DATE_LICENSED"));
							actions.type("txtDateLicensed",objDriverData.get("DRIVERS_DATE_LICENSED"));
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_OCCUPATION") !="")
						{
							actions.selectOptionByText("lstOccupation",objDriverData.get("DRIVERS_OCCUPATION"));
							actions.WaitTillRender();
						}

						//Updating SSN Number
						if(objDriverData.get("DRIVERS_SSN1") !=""){

							actions.click("btnEdit");
							actions.WaitTillRender();

							if(objDriverData.get("DRIVERS_SSN1") !="")
							{
								actions.type("txtDriver_UpdateSSN1",objDriverData.get("DRIVERS_SSN1"));
								actions.WaitTillRender();
							}

							if(objDriverData.get("DRIVERS_SSN2") !="")
							{
								actions.type("txtDriver_UpdateSSN2",objDriverData.get("DRIVERS_SSN2"));
								actions.WaitTillRender();
							}

							if(objDriverData.get("DRIVERS_SSN3") !="")
							{
								actions.type("txtDriver_UpdateSSN3",objDriverData.get("DRIVERS_SSN3"));
								actions.WaitTillRender();
							}

							actions.click("btnUse");
							actions.WaitTillRender();
						}


						if(objDriverData.get("DRIVERS_LICENSE_STATE") !="")
						{
							actions.selectOptionByText("lstLicenseState",objDriverData.get("DRIVERS_LICENSE_STATE"));
							actions.WaitTillRender();
						}

						/*if(objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER") !="")
						{
							actions.type("txtDriversLicenseNumber",objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER"));
							actions.WaitTillRender();
						}*/

						if(objDriverData.get("DRIVERS_NEW_DRIVER_LICENSE_NUMBER") !="")
						{
							actions.type("txtDriversLicenseNumber",objDriverData.get("DRIVERS_NEW_DRIVER_LICENSE_NUMBER"));
							actions.WaitTillRender();
						}

						
						if(objDriverData.get("DRIVERS_DRIVER_TRAINING") !="")
						{
							actions.check("chkDriverTraining");
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_GOOD_STUDENT") !="")
						{
							actions.check("chkGoodStudent");
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_REPORT_CARD_DATE") !="")
						{
							actions.type("txtReportCardDate",objDriverData.get("DRIVERS_REPORT_CARD_DATE"));
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_DISTANT_STUDENT") !="")
						{
							actions.check("chkDistantStudent");
							actions.WaitTillRender();
						}

						if(objDriverData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER") !="")
						{
							if(objDriverData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("YES"))
							{
							
									actions.check("chkDontIncludeAsAssignedDriver");
									
									if(objDriverData.get("REASON_INCLUDE_ASSINGED_DRIVER") !="")
									{
										actions.selectOptionByText("lstAssignedReason",objDriverData.get("REASON_INCLUDE_ASSINGED_DRIVER"));
										actions.WaitTillRender();
									}

									actions.WaitTillRender();
							}
							if(objDriverData.get("DRIVERS_DO_NOT_INCLUDE_AS_ASSINGED_DRIVER").equalsIgnoreCase("NO"))
							{
									actions.unCheck("chkDontIncludeAsAssignedDriver");
									actions.WaitTillRender();
							}
						}
						actions.postTestStep("PASS","Updating Driver" ,"Clicking OK...");						
						actions.click("btnOK");
						blnStatus = actions.isLoaded("divDrivers_PageTitle");

						if(blnStatus)
						{                                                   
							LOGGER.info("Drivers  Updated Successfully");
							actions.postTestStep("PASS","Updating Driver with License number " + objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Updating Driver with License number " + objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is successful.");
						}
						else
						{
							LOGGER.error("  Drivers Updated Failed");
							actions.postTestStep("FAIL","Updating Driver with License number " + objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER") ,"Updating Driver with License number " + objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER")  + " is failed.");				
						}

						break;

					}

				}
			}
			

			try{
				driver.findElement(By.xpath("//a[contains(text(),'Next')]")).isDisplayed();					
				}catch(NoSuchElementException nse){
					actions.click("lnkDetail");
					actions.click("btnOK");
				}
		}else{
				LOGGER.error("Adding  Drivers  Failed");
				actions.postTestStep("FAIL","Adding Driver" ,"Detail link not loaded even after refresh ");	
			}

		}
		catch(Exception e){
			LOGGER.error("Exception in UpdateDrivers method in  Drivers page");			
			e.printStackTrace();			
			throw e;
		}


	}

	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Violations page.
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
	public static boolean Submit() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;
		List<WebElement> lstLicenceNums;
		int Licnense;

		try
		{
			do
			{
				lstLicenceNums	= actions.findElements("divLicenseNumber");
				Licnense = lstLicenceNums.size();
				if(Licnense == 0)
				{
					blnStatus = false;
					System.out.println("No Drivers loaded...!");
					CommonBusinessActions.NavigateToPage("Drivers");
					
				}
				else 
				{
					blnStatus = true;
					System.out.println("Drivers Loaded...! " + Licnense);
				}
				
				
			}while(!blnStatus);
			
			Boolean blnNextFound=false;
			
			do{
				try{
				//actions.isDisplayed("btnNext");
				driver.findElement(By.xpath("//a[contains(text(),'Next')]")).isDisplayed();
				blnNextFound=true;
				}catch(NoSuchElementException nse){
					List<WebElement> lstDetailLinks	= actions.findElements("lnkDetailsLst");
					
					for(int i=1; i<=lstDetailLinks.size();i++){
						driver.findElement(By.xpath("(//a[text()='Detail'])[" + i + "]")).click();
						actions.click("btnOK");
					}
					
				}
			}while(!blnNextFound);
						
			if(blnStatus)
			{	actions.postTestStep("PASS","Drivers page Submission", "Submitting the page..");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divViolations_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Drivers page Submission", "Drivers page submitted successfully");
				}

			}
		}
		catch(Exception e){
			Reporter.log("Exception in Navigating to Violations Page.");
			e.printStackTrace();			
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: DeleteDrivers	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Delete the added Drivers
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
	public void DeleteDrivers(TreeMap<String, String> dataMap)throws Exception 
	{
		try{
			boolean blnStatus = false;

			blnStatus = actions.isLoaded("divDrivers_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Drivers Page");
			}

			String strDriverCount = dataMap.get("DRIVER_DATA");

			String[] strDrivers = strDriverCount.split("-");
			actions.isLoaded("lnkDetail");

			for(int iCounter = 0;iCounter<strDrivers.length;iCounter++)
			{
				Map<String, String> objDriverData = new HashMap<String,String>();
				objDriverData = TestDataReader.gFunc_ReadTestData("Drivers",strDrivers[iCounter]);
				System.out.println(objDriverData);
				actions.postTestStep("PASS", "Drivers Page - Delete Drivers", "List of exisgting drivers");
				actions.isLoaded("divLicenseNumber");
				//Getting the available LicenceNumbers
				List<WebElement> lstLicenceNums	= actions.findElements("divLicenseNumber");
				List<WebElement> lstLinkDelete = actions.findElements("lnkDrivers_DeleteLst");

				for(int i=0;i<lstLicenceNums.size();i++){
					if(lstLicenceNums.get(i).getText().equalsIgnoreCase(objDriverData.get("DRIVERS_DRIVER_LICENSE_NUMBER"))){
						lstLinkDelete.get(i).click();
						
						if(config.getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("firefox")){
							actions.switchToFrame(0);
						}else{
							actions.switchToFrame(1);
						}
						if(objDriverData.get("DRIVERS_DRIVER_DELETE_REASON").trim()!= ""){
							actions.selectOptionByText("lstframeReasonDeleted", objDriverData.get("DRIVERS_DRIVER_DELETE_REASON"));
						}
						
						actions.click("btnframeDeleteDriver");
						driver.switchTo().defaultContent();						
						
					}
				}				
			}
			if(actions.isLoaded("divDrivers_PageTitle")){
				actions.postTestStep("PASS", "Drivers Page - Delete Drivers", "Drivers deleted successfully");
			}else{
				actions.postTestStep("FAIL", "Drivers Page - Delete Drivers", "Drivers deletion failed");
			}
		}catch(Exception e){
			Reporter.log("Exception in Navigating to Violations Page.");
			e.printStackTrace();			
			throw e;
		}
	}
}
