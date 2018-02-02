package com.epam.businesslibs;

import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.epam.driver.CoreDriverScript;

public class ApplicantPage extends CoreDriverScript
{
	private static final Logger LOGGER = Logger.getLogger(ApplicantPage.class);	

	/* 	**********************Method Header******************************************************************
	Method Name					: FillApplicantGeneralDetails	
	*********************************************************************************************************
	*Description             	: To Fill applicant General Details
	*List of arguments       	: Input dataMap - Map of object of the test data
	*Return value            	:None-void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/
	public void FillApplicantGeneralDetails(TreeMap<String,String> dataMap)throws Exception{

		try{

			//waiting for Application Page
			if(!actions.isLoaded("divApplicant_PageTitle"))
			{
				LOGGER.error("Object 'ApplicantPageTitle' not found.");
				actions.postTestStep("FAIL", "Applicant Page - Exit", "Applicant Page not found");
				throw new Exception();
			}

			//Entering Applicant First Name
			if(dataMap.get("APPLICANT_FIRST_NAME")!= "")
			{
				actions.type("txtApplicant_ApplicantFirstName", dataMap.get("APPLICANT_FIRST_NAME"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_ApplicantFirstName' Data updated Successfully.");} 
			}

			//Entering Applicant Last Name
			if(dataMap.get("APPLICANT_LAST_NAME")!= "")
			{
				actions.type("txtApplicant_ApplicantLastName", dataMap.get("APPLICANT_LAST_NAME"));
				if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_ApplicantLastName' Data updated Successfully.");}
			}

			//Entering SSN Number
			if(dataMap.get("APPLICANT_SSN1")!= "")
			{
				actions.type("txtApplicant_SocialSecurityNumber1", dataMap.get("APPLICANT_SSN1"));
			}

			if(dataMap.get("APPLICANT_SSN2")!= "")
			{
				actions.type("txtApplicant_SocialSecurityNumber2", dataMap.get("APPLICANT_SSN2"));
			}

			if(dataMap.get("APPLICANT_SSN3")!= "")
			{
				actions.type("txtApplicant_SocialSecurityNumber3", dataMap.get("APPLICANT_SSN3"));
			}

			//Selecting Marital Status
			if(dataMap.get("APPLICANT_MARITAL_STATUS")!= "")
			{
				actions.selectOptionByText("lstApplicant_MaritalStatus", dataMap.get("APPLICANT_MARITAL_STATUS"));
			}

			//Entering DOB
			if(dataMap.get("APPLICANT_DATE_OF_BIRTH")!= "")
			{
				actions.type("txtApplicant_ApplicantDOB", dataMap.get("APPLICANT_DATE_OF_BIRTH"));
			}

		}

		//Catching the exception 
		catch (Exception e) {					
			LOGGER.error("Exception in FillApplicantGeneralDetails method in ApplicantPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Applicant Page - Exit", "Exception occurred...");
			throw e;
		}
	}

	/* 	**********************Method Header******************************************************************
	Method Name					: FillApplicantAddressDetails	
	*********************************************************************************************************
	*Description             	: To Fill applicant Address Details
	*List of arguments       	: Input dataMap - Map of object of the test data
	*Return value            	:None-void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/
	public void FillApplicantAddressDetails(TreeMap<String,String> dataMap)throws Exception{
		try{

			List<WebElement>txtStreetAddress= null;
			List<WebElement>txtCity= null;
			List<WebElement>lstState= null;
			List<WebElement>txtZipCode= null;
			//waiting for Application Page
			if(!actions.isLoaded("divApplicant_PageTitle"))
			{
				LOGGER.error("Object 'ApplicantPageTitle' not found.");
				actions.postTestStep("FAIL", "Applicant Page - Exit", "Applicant Page not found");
				throw new Exception();
			}

			//Entering Applicant Street Address
			if(dataMap.get("APPLICANT_STREET_ADDRESS")!= "")
			{
				txtStreetAddress= actions.findElements("txtApplicant_InsuredStreetAddress");
				txtStreetAddress.get(txtStreetAddress.size()-1).sendKeys(dataMap.get("APPLICANT_STREET_ADDRESS"));
			}
			//Entering Insured City
			if(dataMap.get("APPLICANT_CITY")!= "")
			{
				txtCity= actions.findElements("txtApplicant_InsuredCity");
				txtCity.get(txtCity.size()-1).sendKeys(dataMap.get("APPLICANT_CITY"));
			}

			//Entering ZipCode
			if(dataMap.get("APPLICANT_ZIP_CODE")!= ""){
				Thread.sleep(1000);
				txtZipCode= actions.findElements("txtApplicant_InsuredZipCode");
				txtZipCode.get(txtZipCode.size()-1).sendKeys(dataMap.get("APPLICANT_ZIP_CODE"));
				if(!actions.WaitTillRender()){
					LOGGER.info("Object 'txtApplicant_InsuredZipCode' Data updated Successfully.");
					}
			}

			if(dataMap.get("APPLICANT_OVERRIDE_ADDRESS").equalsIgnoreCase("YES"))
			{	
				Thread.sleep(1000);
				actions.WaitTillRender();
				actions.check("lstApplicant_OverrideAddress");
				actions.WaitTillRender();
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_OverrideAddress' Data updated Successfully.");}

				//Entering Applicant Street Address
				if(dataMap.get("APPLICANT_OVERRIDE_STREET_ADDRESS")!= "")
				{
					txtStreetAddress= actions.findElements("txtApplicant_InsuredStreetAddress");
					txtStreetAddress.get(txtStreetAddress.size()-1).clear();
					txtStreetAddress.get(txtStreetAddress.size()-1).sendKeys(dataMap.get("APPLICANT_OVERRIDE_STREET_ADDRESS"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredStreetAddress' Data updated Successfully.");}
				}
				//Entering Insured City
				if(dataMap.get("APPLICANT_OVERRIDE_CITY")!= "")
				{
					txtCity= actions.findElements("txtApplicant_InsuredCity");
					txtCity.get(txtCity.size()-1).clear();
					txtCity= actions.findElements("txtApplicant_InsuredCity");
					txtCity.get(txtCity.size()-1).sendKeys(dataMap.get("APPLICANT_OVERRIDE_CITY"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredCity' Data updated Successfully.");}
				}

				//Entering ZipCode
				if(dataMap.get("APPLICANT_OVERRIDE_ZIPCODE")!= ""){
					Thread.sleep(1000);
					txtZipCode= actions.findElements("txtApplicant_InsuredZipCode");
					txtZipCode.get(txtZipCode.size()-1).clear();
					actions.WaitTillRender();
					txtZipCode= actions.findElements("txtApplicant_InsuredZipCode");
					txtZipCode.get(txtZipCode.size()-1).sendKeys(dataMap.get("APPLICANT_OVERRIDE_ZIPCODE"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredZipCode' Data updated Successfully.");}
				}

				//Moving focus to Country
				actions.FoucsOnElement("lstApplicant_InsuredCountry", "id");
				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredCounty' Data updated Successfully.");}

				if(dataMap.get("APPLICANT_OVERRIDE_REASON")!="")
				{
					actions.selectOptionByText("lstApplicant_ReasonForChange", dataMap.get("APPLICANT_OVERRIDE_REASON"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_ReasonForChange' Data updated Successfully.");}

				}
				
				if(dataMap.get("APPLICANT_LICENSED_OPERATOR_COUNT")!="")
				{
					actions.selectOptionByText("lstOtherLicnsedOperatorCount", dataMap.get("APPLICANT_LICENSED_OPERATOR_COUNT"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstOtherLicnsedOperatorCount' Data updated Successfully.");}

				}
				
				
			}

				//Selecting Address Type
				if(dataMap.get("APPLICANT_CURRENT_ADDRESS_SAME_AS_MAILING") != "")
				{
					if(dataMap.get("APPLICANT_CURRENT_ADDRESS_SAME_AS_MAILING").equalsIgnoreCase("No")){

						actions.selectOptionByText("lstApplicant_InsuredCurrentAddressAsMailing", dataMap.get("APPLICANT_CURRENT_ADDRESS_SAME_AS_MAILING"));
						actions.WaitTillRender();
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredCurrentAddressAsMailing' Data updated Successfully.");}

						// Filling the Mailing Address if the current address is deifferent from the mailing address
						FillMailingAddressDifferentFromCurrent(dataMap);
						actions.WaitTillRender();

					}
					else{
						actions.selectOptionByText("lstApplicant_InsuredCurrentAddressAsMailing", dataMap.get("APPLICANT_CURRENT_ADDRESS_SAME_AS_MAILING"));
						actions.WaitTillRender();
					}
				}


				//Moving focus to Country
				actions.FoucsOnElement("lstApplicant_InsuredCountry", "id");

				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredCounty' Data updated Successfully.");}


				//Selecting Years of Residence
				if(dataMap.get("APPLICANT_YEARS_AT_ABOVE_RESIDENCE")!= "")
				{
					if(dataMap.get("APPLICANT_YEARS_AT_ABOVE_RESIDENCE").equalsIgnoreCase("Less Than 3 Years")){
						actions.selectOptionByText("lstApplicant_InsuredYearsResidence", dataMap.get("APPLICANT_YEARS_AT_ABOVE_RESIDENCE"));				
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredYearsResidence' Data updated Successfully.");}

						// Filling the Residence details if the residence is less than 3Years
						FillResidenceLessThan3Yrs(dataMap);
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredYearsResidence' Data updated Successfully.");}
					}
					else{
						actions.waitForDropdownToLoad("lstApplicant_InsuredYearsResidence", dataMap.get("APPLICANT_YEARS_AT_ABOVE_RESIDENCE"), 10);
						actions.selectOptionByText("lstApplicant_InsuredYearsResidence", dataMap.get("APPLICANT_YEARS_AT_ABOVE_RESIDENCE"));				
						if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredYearsResidence' Data updated Successfully.");}
					}

				}
				
				//Entering Effective Policy Date
				if(dataMap.get("APPLICANT_EFF_DATE") != "")
				{
					actions.type("txtApplicant_InsuredEffectiveDate", dataMap.get("APPLICANT_EFF_DATE"));
					actions.WaitTillRender();
				}

				//Selecting Term
				if(dataMap.get("APPLICANT_TERM")!="")
				{
					actions.selectOptionByText("lstApplicant_InsuredTerm", dataMap.get("APPLICANT_TERM"));
					LOGGER.info("Object 'lstApplicant_InsuredTerm' Data updated Successfully.");
					actions.FoucsOnElement("lstApplicant_InsuredTerm", "id");

					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredTerm' Data updated Successfully.");}
				}

				//Entering Applicants Phone number
				if(dataMap.get("APPLICANT_PHONE")!="")
				{
					actions.type("txtApplicant_InsuredPhone", dataMap.get("APPLICANT_PHONE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredPhone' Data updated Successfully.");}
				}

				//Selecting Type of business
				if(dataMap.get("APPLICANT_TYPE_OF_BUSINESS") !="")
				{
					actions.selectOptionByText("lstApplicant_InsuredTypeOfBusiness", dataMap.get("APPLICANT_TYPE_OF_BUSINESS"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredTypeOfBusiness' Data updated Successfully.");}
				}

				//Current policy number
				if(dataMap.get("APPLICANT_CURRENT_PLOICY_NUM") !="")
				{
					actions.type("txtCurrentPolicyNumber", dataMap.get("APPLICANT_CURRENT_PLOICY_NUM"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtCurrentPolicyNumber' Data updated Successfully.");}
				}
				//Reason for rewrite
				if(dataMap.get("APPLICANT_REASON_REWRITE") !="")
				{
					actions.selectOptionByText("lstApplicant_ReasonForRewrite", dataMap.get("APPLICANT_REASON_REWRITE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_InsuredTypeOfBusiness' Data updated Successfully.");}
				}
				//Explain
				if(dataMap.get("APPLICANT_EXPLAIN") !="")
				{
					actions.type("txtExplain", dataMap.get("APPLICANT_EXPLAIN"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtExplain' Data updated Successfully.");}
				}

			}
			catch (Exception e) {					
				LOGGER.error("Exception in FillApplicantAddressDetails method in ApplicantPage");
				e.printStackTrace();
				actions.postTestStep("FAIL", "Applicant Page - Exit", "Exception occurred...");
				throw e;
			}
		}

	/* 	**********************Method Header******************************************************************
	Method Name					: FillNamedInsuredDetails	
	*********************************************************************************************************
	*Description             	: To Fill  Named Insured Details
	*List of arguments       	: Input dataMap - Map of object of the test data
	*Return value            	:None-void
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/

		public void FillNamedInsuredDetails(TreeMap<String,String> dataMap)throws Exception{

			try{
				//waiting for Application Page
				if(!actions.isLoaded("divApplicant_PageTitle"))
				{
					LOGGER.error("Object 'ApplicantPageTitle' not found.");
					actions.postTestStep("FAIL", "Applicant Page - Exit", "Applicant Page not found");
					throw new Exception();


				}

				if(!actions.waitForElementPresent("txtApplicant_NamedInsuredFirstName", 10))
				{
					actions.click("btnNamedInsured");
					if(!actions.WaitTillRender()){LOGGER.info("Object 'btnNamedInsured' Data updated Successfully.");} 
				}
				//Entering Applicant First Name
				if(dataMap.get("NAMEDINSURED_FIRST_NAME")!= "")
				{
					actions.type("txtApplicant_NamedInsuredFirstName", dataMap.get("NAMEDINSURED_FIRST_NAME"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_NamedInsuredFirstName' Data updated Successfully.");} 
				}

				//Entering Applicant Last Name
				if(dataMap.get("NAMEDINSURED_LAST_NAME")!= "")
				{
					actions.type("txtApplicant_NamedInsuredLastName", dataMap.get("NAMEDINSURED_LAST_NAME"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_NamedInsuredLastName' Data updated Successfully.");}
				}
				if(dataMap.get("NAMEDINSURED_LISTED")!=null && dataMap.get("NAMEDINSURED_LISTED")!="")
				{
					actions.selectOptionByText("lstDoseNamedInsuredneedtobeListed", dataMap.get("NAMEDINSURED_LISTED"));				
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstDoseNamedInsuredneedtobeListed' Data updated Successfully.");}

				}

				//Entering SSN Number
				if(dataMap.get("NAMEDINSURED_SSN1")!= "")
				{
					actions.type("txtApplicant_NamedInsuredSocialSecurityNumber1", dataMap.get("NAMEDINSURED_SSN1"));
				}

				if(dataMap.get("NAMEDINSURED_SSN2")!= "")
				{
					actions.type("txtApplicant_NamedInsuredSocialSecurityNumber2", dataMap.get("NAMEDINSURED_SSN2"));
				}

				if(dataMap.get("NAMEDINSURED_SSN3")!= "")
				{
					actions.type("txtApplicant_NamedInsuredSocialSecurityNumber3", dataMap.get("NAMEDINSURED_SSN3"));
				}

				//Selecting Marital Status
				if(dataMap.get("NAMEDINSURED_MARITAL_STATUS")!= "")
				{
					actions.selectOptionByText("lstApplicant_NamedInsuredMaritalStatus", dataMap.get("NAMEDINSURED_MARITAL_STATUS"));
				}
				if(dataMap.get("VEHICLESSOLELY_OWNED")!=null && dataMap.get("VEHICLESSOLELY_OWNED")!="")
				{
					actions.selectOptionByText("lstAreAnyVehiclesNotSolelyOwned", dataMap.get("VEHICLESSOLELY_OWNED"));				
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstAreAnyVehiclesNotSolelyOwned' Data updated Successfully.");}

				}

				//Entering DOB
				if(dataMap.get("NAMEDINSURED_DATE_OF_BIRTH")!= "")
				{
					actions.type("txtApplicant_NamedInsuredApplicantDOB", dataMap.get("NAMEDINSURED_DATE_OF_BIRTH"));
				}

			}
			catch (Exception e) {					
				LOGGER.error("Exception in FillNamedInsuredDetails method in ApplicantPage");
				e.printStackTrace();
				actions.postTestStep("FAIL", "Applicant Page - Exit", "Exception occurred...");
				throw e;
			}
		}
		/* 	**********************Method Header******************************************************************
		Method Name					: FillResidenceLessThan3Yrs	
		*********************************************************************************************************
		*Description             	: To Handle Address which is less than 3 years of Residence case.
		*List of arguments       	: Input dataMap - Map of object of the test data
		*Return value            	:None-void
		*Notes					 	:	
		*********************************************************************************************************	
		Revision History
		*********************************************************************************************************
		Modified Date				: MM-DD-YYYY			
		Modified By					: <Name>		
		Changed Description			: <Change comments>
	 	********************************************************************************************************/
		public void FillResidenceLessThan3Yrs(TreeMap<String,String> dataMap)throws Exception{

			try{
				List<WebElement>txtStreetAddress= null;
				List<WebElement>txtCity= null;
				List<WebElement>lstState= null;
				List<WebElement>txtZipCode= null;

				if(!actions.isLoaded("txtApplicant_YearsResidenceLT3Previoustitle"))
				{
					LOGGER.error("Object 'PreviousTitle' not found.");
					actions.postTestStep("FAIL", "Previous Residence Fields - Exit", "Previous Residence fields not found");
					throw new Exception();
				}

				//Entering previous Street Address
				if(dataMap.get("APPLICANT_PREVIOUS_STREET_ADDRESS")!= "")
				{
					txtStreetAddress= actions.findElements("txtApplicant_InsuredStreetAddress");
					txtStreetAddress.get(txtStreetAddress.size()-1).sendKeys(dataMap.get("APPLICANT_PREVIOUS_STREET_ADDRESS"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredStreetAddress' Data updated Successfully.");}			
				}

				//Entering previous Address2
				if(dataMap.get("APPLICANT_PREVIOUS_STREET_ADDRESS2")!= "")
				{
					actions.type("txtApplicant_YearsResidenceLT3InsuredAddress2", dataMap.get("APPLICANT_PREVIOUS_STREET_ADDRESS2"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_YearsResidenceLT3InsuredAddress2' Data updated Successfully.");}
				}


				//Entering Insured City
				if(dataMap.get("APPLICANT_PREVIOUS_CITY")!= "")
				{
					txtCity= actions.findElements("txtApplicant_InsuredCity");
					txtCity.get(txtCity.size()-1).clear();
					actions.WaitTillRender();
					txtCity= actions.findElements("txtApplicant_InsuredCity");
					txtCity.get(txtCity.size()-1).sendKeys(dataMap.get("APPLICANT_PREVIOUS_CITY"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredCity' Data updated Successfully.");}			

				}

				if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_YearsResidenceLT3InsuredState' Data updated Successfully.");}

				//Entering previous State
				if(dataMap.get("APPLICANT_PREVIOUS_STATE")!= "")
				{
					lstState = actions.findElements("lstApplicant_MailingInsuredState");
					Select objStateSelect = new Select(lstState.get(lstState.size()-1));
					objStateSelect.selectByVisibleText(dataMap.get("APPLICANT_PREVIOUS_STATE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_MailingInsuredState' Data updated Successfully.");}
				}
				//Entering previous  ZipCode
				if(dataMap.get("APPLICANT_PREVIOUS_ZIP_CODE")!= ""){

					txtZipCode= actions.findElements("txtApplicant_InsuredZipCode");
					txtZipCode.get(txtZipCode.size()-1).sendKeys(dataMap.get("APPLICANT_PREVIOUS_ZIP_CODE"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredZipCode' Data updated Successfully.");}
				}


			}

			catch (Exception e) {					
				LOGGER.error("Exception in FillResidenceLessThan3Yrs method in ApplicantPage");
				e.printStackTrace();
				actions.postTestStep("FAIL", "Previous Residence Fields - Exit", "Exception occurred...");
				throw e;
			}

		}
		
		/* 	**********************Method Header******************************************************************
		Method Name					: FillMailingAddressDifferentFromCurrent	
		*********************************************************************************************************
		*Description             	: To Handle Mailing Address different from Current case.
		*List of arguments       	: Input dataMap - Map of object of the test data
		*Return value            	:None-void
		*Notes					 	:	
		*********************************************************************************************************	
		Revision History
		*********************************************************************************************************
		Modified Date				: MM-DD-YYYY			
		Modified By					: <Name>		
		Changed Description			: <Change comments>
	 	********************************************************************************************************/

		public void FillMailingAddressDifferentFromCurrent(TreeMap<String,String> dataMap)throws Exception{

			try{

				List<WebElement>txtStreetAddress= null;
				List<WebElement>txtCity= null;
				List<WebElement>lstState= null;
				List<WebElement>txtZipCode= null;

				if(!actions.isLoaded("txtApplicant_Mailingtitle"))
				{
					LOGGER.error("Object 'PreviousTitle' not found.");
					actions.postTestStep("FAIL", "Mailing Address- Exit", "Mailing Address fields not found");
					throw new Exception();
				}

				//Entering Mailing Street Address
				if(dataMap.get("APPLICANT_MAILING_STREET_ADDRESS")!= "")
				{
					txtStreetAddress= actions.findElements("txtApplicant_InsuredStreetAddress");
					txtStreetAddress.get(txtStreetAddress.size()-1).sendKeys(dataMap.get("APPLICANT_MAILING_STREET_ADDRESS"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredStreetAddress' Data updated Successfully.");}
				}

				//Entering Mailing Address2
				if(dataMap.get("APPLICANT_MAILING_STREET_ADDRESS2")!= "")
				{
					actions.type("txtApplicant_MailingInsuredAddress2", dataMap.get("APPLICANT_MAILING_STREET_ADDRESS2"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_MailingInsuredAddress2' Data updated Successfully.");}
				}


				//Entering Mailing Insured City
				if(dataMap.get("APPLICANT_MAILING_CITY")!= "")
				{
					actions.FoucsOnElement("txtApplicant_MailingInsuredCity", "id");
					actions.WaitTillRender();
					txtCity= actions.findElements("txtApplicant_MailingInsuredCity");
					txtCity.get(txtCity.size()-1).sendKeys(dataMap.get("APPLICANT_MAILING_CITY"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_MailingInsuredCity' Data updated Successfully.");}
				}


				//Entering Mailing State
				if(dataMap.get("APPLICANT_MAILING_STATE")!= "")
				{
					lstState = actions.findElements("lstApplicant_MailingInsuredState");
					Select objStateSelect = new Select(lstState.get(lstState.size()-1));
					objStateSelect.selectByVisibleText(dataMap.get("APPLICANT_MAILING_STATE"));
					actions.WaitTillRender();
					if(!actions.WaitTillRender()){LOGGER.info("Object 'lstApplicant_MailingInsuredState' Data updated Successfully.");}
				}
				//Entering Mailing  ZipCode
				if(dataMap.get("APPLICANT_MAILING_ZIP_CODE")!= "")
				{

					txtZipCode= actions.findElements("txtApplicant_InsuredZipCode");
					txtZipCode.get(txtZipCode.size()-1).sendKeys(dataMap.get("APPLICANT_MAILING_ZIP_CODE"));
					actions.WaitTillRender();
					//actions.type("txtApplicant_MailingInsuredZipCode",dataMap.get("APPLICANT_MAILING_ZIP_CODE"));
					if(!actions.WaitTillRender()){LOGGER.info("Object 'txtApplicant_InsuredZipCode' Data updated Successfully.");}
				}


			}

			catch (Exception e) {					
				LOGGER.error("Exception in FillMailingAddressDifferentFromCurrent method in ApplicantPage");
				e.printStackTrace();
				actions.postTestStep("FAIL", "Mailing Address Fields - Exit", "Exception occurred...");
				throw e;
			}


		}
		/* 	**********************Method Header******************************************************************
		Method Name					: Submit	
		Module						: NA.
		Compatible Starting With	: NA.	
		*********************************************************************************************************
		*Description             	: To Click on Next button and Navigate to Drivers page.
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

		public void Submit()throws Exception{

			try{

				//waiting for Application Page
				if(!actions.isLoaded("divApplicant_PageTitle"))
				{
					LOGGER.error("Object 'ApplicantPageTitle' not found.");
					actions.postTestStep("FAIL", "Submit button- Exit", "Submit button not found");
					throw new Exception();

				}	
				actions.postTestStep("PASS","Applicant Data  filled successfully","Submitting the page...");
				//Clicking on Next button
				actions.click("btnApplicant_Next");

				if(actions.isLoaded("divDrivers_PageTitle"))
				{
					LOGGER.info("Clicked on Next in Applicant and Navigated to the Drivers Page");
					actions.postTestStep("PASS","Applicant Data  filled successfully","filling Applicant Data successful...");
				}
				else
				{
					LOGGER.error("Click on Next in Applicant page Failed");
					actions.postTestStep("FAIL","Applicant data filling failed..","Appliant data filling  failed");				
				}
			}

			catch (Exception e) {					
				LOGGER.error("Exception in Submit method in ApplicantPage");
				e.printStackTrace();
				actions.postTestStep("FAIL", "Submit button - Exit", "Exception occurred...");
				throw e;
			}

		}



	}
