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

public class VehiclesPage extends CoreDriverScript {


	private static final Logger LOGGER = Logger.getLogger(VehiclesPage.class);

	/* 	**********************Method Header******************************************************************
	Method Name					: AddVehicles	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Add New Vehicles.
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
	public void AddVehicles(TreeMap<String,String> dataMap) throws Exception 
	{
		boolean blnStatus = false;
		try{

			blnStatus = actions.isLoaded("divVehicals_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Vehicles Page");
				actions.postTestStep("FAIL", "Vehicles Page - AddVehicles", "Vehicles Page not found");
				throw new Exception();
			}
			String strVehicleCount = dataMap.get("VEHICLE_DATA");

			if(strVehicleCount.contains("#")){
				String[] arrVehicleCount=strVehicleCount.split("#");
				for(int arrIndex=0; arrIndex<arrVehicleCount.length;arrIndex++)
				{
					if(arrVehicleCount[arrIndex].toUpperCase().contains("ADD="))
					{
						strVehicleCount=arrVehicleCount[arrIndex].split("=")[1];
						break;
					}
				}
			}

			String[] strVehicle = strVehicleCount.split("-");

			for(int iCounter = 0;iCounter<strVehicle.length;iCounter++)
			{
				Map<String, String> objRowData = new HashMap<String,String>();
				objRowData = TestDataReader.gFunc_ReadTestData("Vehicles",strVehicle[iCounter]);
				System.out.println(objRowData);

				if(actions.getText("divVINNumber").length() ==0)
				{	
					actions.postTestStep("PASS", "Vehicles Page - AddVehicles", "Clicking Details..");
					actions.click("lnkDetail");
				}
				else
				{	actions.postTestStep("PASS", "Vehicles Page - AddVehicles", "Add Vehicle..");
					actions.click("btnAddVehicle");
				}

				if(objRowData.get("VEHICLE_TYPE") != "")
				{
					actions.selectOptionByText("lstType",objRowData.get("VEHICLE_TYPE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstType' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_VIN") != "")
				{
					actions.type("txtVIN", objRowData.get("VEHICLE_VIN"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtVIN' Data updated Successfully.");}
				}

				//Added Accepted Vin on 07-11-2016 Srikanth,T
				if(objRowData.get("VEHICLE_ACCEPT_VIN").equalsIgnoreCase("No"))
				{
					actions.check("lstAcceptVIN");
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAcceptVIN' Data updated Successfully.");}
				}
				if(objRowData.get("VEHICLE_YEAR") != "")
				{
					actions.type("txtYear", objRowData.get("VEHICLE_YEAR"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtYear' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_MAKE") != "")
				{
					if(objRowData.get("VEHICLE_TYPE").equalsIgnoreCase("Utility Trailer") || objRowData.get("VEHICLE_TYPE").equalsIgnoreCase("Motorhome") )
					{
						actions.type("txtMake", objRowData.get("VEHICLE_MAKE"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtMake' Data updated Successfully.");}
					}
					else
					{
						actions.selectOptionByText("lstMake", objRowData.get("VEHICLE_MAKE"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstMake' Data updated Successfully.");}
					}
				}

				if(objRowData.get("VEHICLE_MODEL") != "")
				{
					actions.type("txtModel", objRowData.get("VEHICLE_MODEL"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUsage' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_DESCRIPTION") != "")
				{
					actions.type("txtDescription", objRowData.get("VEHICLE_DESCRIPTION"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtDescription' Data updated Successfully.");}
				}


				if(objRowData.get("VEHICLE_TITLE") != "")
				{
					actions.selectOptionByText("lstTitle", objRowData.get("VEHICLE_TITLE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstTitle' Data updated Successfully.");}
				}
				if(objRowData.get("VEHICLE_IS_THIS_VEHICLE__REGISTERED_IN_PA") != "")
				{
					actions.selectOptionByText("lstVehicleRegisteredinPA", objRowData.get("VEHICLE_IS_THIS_VEHICLE__REGISTERED_IN_PA"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstVehicleRegisteredinPA' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_USAGE") != "")
				{
					actions.selectOptionByText("lstUsage", objRowData.get("VEHICLE_USAGE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUsage' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_MILES") != "")
				{
					actions.type("txtMiles", objRowData.get("VEHICLE_MILES"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtMiles' Data updated Successfully.");}
				}

				if(objRowData.get("DETAILS_OF_BUSINESS_USE") != "")
				{
					actions.type("txtDetailsofBusinessUsage", objRowData.get("DETAILS_OF_BUSINESS_USE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtDetailsofBusinessUsage' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_COST_NEW") != "")
				{
					actions.type("txtCostNew", objRowData.get("VEHICLE_COST_NEW"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtCostNew' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_STATED_AMOUNT") != "" )
				{
					if(objRowData.get("VEHICLE_STATED_AMOUNT").equalsIgnoreCase("YES"))
					{
						actions.check("chkStatedAmount");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtCostNew' Data updated Successfully.");}

						actions.type("txtInsurablValue", objRowData.get("VEHICLE_INSURABLE_VALUE"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtInsurablValue' Data updated Successfully.");}
					}
					else if(objRowData.get("VEHICLE_STATED_AMOUNT").equalsIgnoreCase("NO"))
					{
						actions.unCheck("chkStatedAmount");
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtCostNew' Data updated Successfully.");}
					}

				}


				if(objRowData.get("VEHICLE_PASSIVE_RESTRAINT") != "")
				{
					actions.selectOptionByText("lstPassiveRestarint", objRowData.get("VEHICLE_PASSIVE_RESTRAINT"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstPassiveRestarint' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_ANTI_THEFT") != "")
				{
					actions.selectOptionByText("lstAntiTheft", objRowData.get("VEHICLE_ANTI_THEFT"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAntiTheft' Data updated Successfully.");}
				}

				if(objRowData.get("VEHICLE_ANTI_LOCK_BRAKES") != "")
				{
					actions.selectOptionByText("lstAntiLockBrakes", objRowData.get("VEHICLE_ANTI_LOCK_BRAKES"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAntiLockBrakes' Data updated Successfully.");}
				}

				// Adding the Alternate Garrage Address details
				if(objRowData.get("VEHICLE_ALTERNATE_GARAGE") != "" && objRowData.get("VEHICLE_ALTERNATE_GARAGE").equalsIgnoreCase("Yes"))
				{
					actions.check("chkAlternateGarage");
					if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAlternateGarage' Checked Successfully.");}

					if(objRowData.get("VEHICLE_ALTERNATE_GARAGEADDRESS") != "")
					{
						actions.type("txtGarriageAddress", objRowData.get("VEHICLE_ALTERNATE_GARAGEADDRESS"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageAddress' Data updated Successfully.");}
					}

					if(objRowData.get("VEHICLE_ALTERNATE_GARAGECITY") != "")
					{
						actions.type("txtGarriageCity", objRowData.get("VEHICLE_ALTERNATE_GARAGECITY"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageCity' Data updated Successfully.");}
					}

					if(objRowData.get("VEHICLE_ALTERNATE_GARAGESTATE") != "")
					{
						actions.selectOptionByText("lstGarriageState", objRowData.get("VEHICLE_ALTERNATE_GARAGESTATE"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstGarriageState' Data updated Successfully.");}
					}

					if(objRowData.get("GARAGE_STATE_CHANGE") != "")
					{
						actions.selectOptionByText("lstGarriageStateChange", objRowData.get("GARAGE_STATE_CHANGE"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstGarriageStateChange' Data updated Successfully.");}
					}

					if(objRowData.get("VEHICLE_ALTERNATE_GARAGEZIP") != "")
					{
						actions.type("txtGarriageZip", objRowData.get("VEHICLE_ALTERNATE_GARAGEZIP"));
						if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageZip' Data updated Successfully.");}
					}

					if(objRowData.get("VEHICLE_ALTERNATE_GARAGECOUNTY") != "")
					{
						actions.selectOptionByText("lstGarriageCounty", objRowData.get("VEHICLE_ALTERNATE_GARAGECOUNTY"));
						if(!actions.WaitTillRender()){LOGGER.info("Object"
								+ ""
								+ ""
								+ " 'lstGarriageCounty' Data updated Successfully.");}
					}
					if(objRowData.get("VEHICLE_ALTERNATE_GARRIAGE_APPLIEDVEHICLES") != "")
					{
						actions.selectOptionByText("lstAppliedToALlVehicles", objRowData.get("VEHICLE_ALTERNATE_GARRIAGE_APPLIEDVEHICLES"));
						if(!actions.WaitTillRender()){LOGGER.info("Object"
								+ ""
								+ ""
								+ " 'lstAppliedToALlVehicles' Data updated Successfully.");
						}
					}


				}

				actions.postTestStep("PASS", "Vehicles Page - AddVehicles", "Clicking OK..");
				actions.click("btnOK");

				blnStatus = actions.isLoaded("divVehicals_PageTitle");
				if(blnStatus)
				{                                                   
					LOGGER.info("Vehicle  Added Successfully");
					if(objRowData.get("VEHICLE_VIN") != "")
					{
						actions.postTestStep("PASS","Adding Vehicle with VIN number " + objRowData.get("VEHICLE_VIN") ,"Adding Driver with VIN number " + objRowData.get("VEHICLE_VIN")  + " is successful.");
					}
					else
					{
						actions.postTestStep("PASS","Adding Vehicle with  Model " + objRowData.get("MODEL") ,"Adding Driver with MODEL " + objRowData.get("MODEL")  + " is successful.");
					}
				}
				else
				{
					LOGGER.error("Adding  Drivers  Failed");
					if(objRowData.get("VEHICLE_VIN") != "")
					{
						actions.postTestStep("FAIL","Adding Vehicle with VIN number " + objRowData.get("VEHICLE_VIN") ,"Adding Driver with VIN number " + objRowData.get("VEHICLE_VIN")  + " is failed.");
					}
					else
					{
						actions.postTestStep("FAIL","Adding Vehicle with  Model " + objRowData.get("MODEL") ,"Adding Driver with MODEL " + objRowData.get("MODEL")  + " is failed.");
					}
				}

			}
		}

		catch (Exception e) {					
			LOGGER.error("Exception in AddVehicles method in VehiclesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Vehicles Page - Exit", "Exception occurred in AddVehicles method");
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to Coverage page.
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
			blnStatus = actions.isLoaded("divVehicals_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS", "Vehicles Page - Submission", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.isLoaded("lnkDetail");
				actions.click("btnNext");
				if(actions.isLoaded("divCoverageDetails_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","Vehicles page Submission", "Vehicles page submitted successfully");
				}	
				else
				{
					LOGGER.error("Object 'divCoverageDetails_PageTitle' not found.");
					actions.postTestStep("FAIL", "Vehicles page Submit - Exit", "Vehicles page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divVehicals_PageTitle' not found.");
				actions.postTestStep("FAIL", "Vehicles Page Submit - Exit", "Vehicles Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in VehiclesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Vehicles Page Submit - Exit", "Exception occurred in Vehicles Page Submit method");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: UpdateVehicles	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To update the existing vehicles information.
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
	public void UpdateVehicles(TreeMap<String, String> dataMap) throws Exception {

		boolean blnStatus = false;

		try{

			blnStatus = actions.isLoaded("divVehicals_PageTitle");
			if(!blnStatus)
			{
				LOGGER.error("User is not in Vehicles Page");
				actions.postTestStep("FAIL", "Update Vehicles Page - Exit", "Vehicles Page not found");
				throw new Exception();
			}
			String strVehicleCount = dataMap.get("VEHICLE_DATA");

			if(strVehicleCount.contains("#"))
			{
				String[] arrVehicleCount=strVehicleCount.split("#");
				for(int arrIndex=0; arrIndex<arrVehicleCount.length;arrIndex++)
				{
					if(arrVehicleCount[arrIndex].toUpperCase().contains("UPDATE="))
					{
						strVehicleCount=arrVehicleCount[arrIndex].split("=")[1];
						break;
					}
				}
			}
			String[] strVehicle = strVehicleCount.split("-");
			actions.postTestStep("PASS", "Vehicles Page - UpdateVehicles", "Updating vehicles...");
			for(int iCounter = 0;iCounter<strVehicle.length;iCounter++)
			{
				Map<String, String> objRowData = new HashMap<String,String>();
				objRowData = TestDataReader.gFunc_ReadTestData("Vehicles",strVehicle[iCounter]);
				System.out.println(objRowData);

				//Getting the available LicenceNumbers
				List<WebElement> lstVinNums	= actions.findElements("divVINNumber");

				List<WebElement> lstLinkDetails = actions.findElements("lnkDetailsLst");

				for(int i=0;i<lstVinNums.size();i++)
				{
					if(lstVinNums.get(i).getText().equalsIgnoreCase(objRowData.get("VEHICLE_VIN")))
					{

//						lstLinkDetails.get(i+1).click();
						lstLinkDetails.get(i).click();

						if(objRowData.get("VEHICLE_TYPE") != "")
						{
							actions.selectOptionByText("lstType",objRowData.get("VEHICLE_TYPE"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstType' Data updated Successfully.");}
							
							
							if(objRowData.get("VEHICLE_VIN") != "")
							{
								actions.type("txtVIN", objRowData.get("VEHICLE_VIN"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtVIN' Data updated Successfully.");}
							}
							
							if(objRowData.get("VEHICLE_YEAR") != "")
							{
								actions.type("txtYear", objRowData.get("VEHICLE_YEAR"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtYear' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_MAKE") != "")
							{
								if(objRowData.get("VEHICLE_TYPE").equalsIgnoreCase("Utility Trailer") || objRowData.get("VEHICLE_TYPE").equalsIgnoreCase("Motorhome") )
								{
									actions.type("txtMake", objRowData.get("VEHICLE_MAKE"));
									if(!actions.WaitTillRender()){LOGGER.info("Object 'txtMake' Data updated Successfully.");}
								}
								else
								{
									actions.selectOptionByText("lstMake", objRowData.get("VEHICLE_MAKE"));
									if(!actions.WaitTillRender()){LOGGER.info("Object 'lstMake' Data updated Successfully.");}
								}
							}

							if(objRowData.get("VEHICLE_MODEL") != "")
							{
								actions.type("txtModel", objRowData.get("VEHICLE_MODEL"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUsage' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_DESCRIPTION") != "")
							{
								actions.type("txtDescription", objRowData.get("VEHICLE_DESCRIPTION"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtDescription' Data updated Successfully.");}
							}
						}

						if(objRowData.get("VEHICLE_ACCEPT_VIN").equalsIgnoreCase("Yes"))
						{
							actions.check("lstAcceptVIN");
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAcceptVIN' Data updated Successfully.");}
						}

						if(objRowData.get("VEHICLE_ACCEPT_VIN").equalsIgnoreCase("No"))
						{
							actions.check("lstAcceptVIN");
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAcceptVIN' Data updated Successfully.");}
						}


						if(objRowData.get("VEHICLE_TITLE") != "")
						{
							actions.selectOptionByText("lstTitle", objRowData.get("VEHICLE_TITLE"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstTitle' Data updated Successfully.");}
						}
						if(objRowData.get("VEHICLE_IS_THIS_VEHICLE__REGISTERED_IN_PA") != "")
						{
							actions.selectOptionByText("lstVehicleRegisteredinPA", objRowData.get("VEHICLE_IS_THIS_VEHICLE__REGISTERED_IN_PA"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstVehicleRegisteredinPA' Data updated Successfully.");}
						}

						if(objRowData.get("VEHICLE_USAGE") != "")
						{
							actions.selectOptionByText("lstUsage", objRowData.get("VEHICLE_USAGE"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUsage' Data updated Successfully.");}
						}
						if(objRowData.get("DETAILS_OF_BUSINESS_USE") != "")
						{
							actions.type("txtDetailsofBusinessUsage", objRowData.get("DETAILS_OF_BUSINESS_USE"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'lstUsage' Data updated Successfully.");}
						}

						if(objRowData.get("VEHICLE_COST_NEW") != "")
						{
							actions.type("txtCostNew", objRowData.get("VEHICLE_COST_NEW"));
							if(!actions.WaitTillRender()){LOGGER.info("Object 'txtCostNew' Data updated Successfully.");}
						}

						if(objRowData.get("VEHICLE_ALTERNATE_GARAGE") != "" && objRowData.get("VEHICLE_ALTERNATE_GARAGE").equalsIgnoreCase("Yes"))
						{
							actions.check("chkAlternateGarage");
							if(!actions.WaitTillRender()){LOGGER.info("Object 'chkAlternateGarage' Checked Successfully.");}

							if(objRowData.get("VEHICLE_ALTERNATE_GARAGEADDRESS") != "")
							{
								actions.type("txtGarriageAddress", objRowData.get("VEHICLE_ALTERNATE_GARAGEADDRESS"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageAddress' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_ALTERNATE_GARAGECITY") != "")
							{
								actions.type("txtGarriageCity", objRowData.get("VEHICLE_ALTERNATE_GARAGECITY"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageCity' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_ALTERNATE_GARAGESTATE") != "")
							{
								actions.selectOptionByText("lstGarriageState", objRowData.get("VEHICLE_ALTERNATE_GARAGESTATE"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'lstGarriageState' Data updated Successfully.");}
							}

							if(objRowData.get("GARAGE_STATE_CHANGE") != "")
							{
								actions.selectOptionByText("lstGarriageStateChange", objRowData.get("GARAGE_STATE_CHANGE"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'lstGarriageStateChange' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_ALTERNATE_GARAGEZIP") != "")
							{
								actions.type("txtGarriageZip", objRowData.get("VEHICLE_ALTERNATE_GARAGEZIP"));
								if(!actions.WaitTillRender()){LOGGER.info("Object 'txtGarriageZip' Data updated Successfully.");}
							}

							if(objRowData.get("VEHICLE_ALTERNATE_GARAGECOUNTY") != "")
							{
								actions.selectOptionByText("lstGarriageCounty", objRowData.get("VEHICLE_ALTERNATE_GARAGECOUNTY"));
								if(!actions.WaitTillRender()){LOGGER.info("Object"
										+ ""
										+ ""
										+ " 'lstGarriageCounty' Data updated Successfully.");
								}
							}
							

							if(objRowData.get("VEHICLE_ALTERNATE_GARRIAGE_APPLIEDVEHICLES") != "")
							{
								actions.selectOptionByText("lstAppliedToALlVehicles", objRowData.get("VEHICLE_ALTERNATE_GARRIAGE_APPLIEDVEHICLES"));
								if(!actions.WaitTillRender()){LOGGER.info("Object"
										+ ""
										+ ""
										+ " 'lstAppliedToALlVehicles' Data updated Successfully.");
								}
							}

						}
						actions.postTestStep("PASS", "Vehicles Page - UpdateVehicles", "Clicking OK..");
						actions.click("btnOK");


						blnStatus = actions.isLoaded("divVehicals_PageTitle");

						if(blnStatus)
						{                                                   
							LOGGER.info("Vehicles  Updated Successfully");
							actions.postTestStep("PASS","Updating Vehicles with VIN number " + objRowData.get("VEHICLE_VIN") ,"Updating Vehicles with VIN number " + objRowData.get("VEHICLE_VIN")  + " is successful.");
						}
						else
						{
							LOGGER.error("Vehicles Updated  Failed");
							actions.postTestStep("FAIL","Updating Vehicles with VIN number " + objRowData.get("VEHICLE_VIN") ,"Updating Vehicles with VIN number " + objRowData.get("VEHICLE_VIN")  + " is failed.");				
						}

						break;


					}
				}

			}
		}

		catch (Exception e) {					
			LOGGER.error("Exception in UpdateVehicles method in VehiclesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Vehicles Page  - Exit", "Exception occurred in UpdateVehicles method");
			throw e;
		}

	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SelectOtherMBGPolicy	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Select other MBG Policy list items.
	*List of arguments       	: Map-dataMap.
	*Return value            	: void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	public void SelectOtherMBGPolicy(TreeMap<String, String> dataMap) throws Exception
	{
		try
		{
			actions.isLoaded("lnkDetail");
			if(dataMap.get("VEHICLE_OTHER_MBG_AUTO_POLICY") != "")
			{
				actions.isLoaded("lnkDetail");
				actions.selectOptionByText("lstOtherMBGPolicy", dataMap.get("VEHICLE_OTHER_MBG_AUTO_POLICY"));
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in SelectOtherMBGPolicy method in VehiclesPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Vehicles Page  - Exit", "Exception occurred in SelectOtherMBGPolicy method");
			throw e;
		}

	}
}