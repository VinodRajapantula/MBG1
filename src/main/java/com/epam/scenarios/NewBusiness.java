package com.epam.scenarios;

import java.util.TreeMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.annotations.Test;

import com.epam.businesslibs.*;
import com.epam.driver.CoreDriverScript;
import com.epam.driver.SetupSelenium;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.TestDataReader;

public class NewBusiness extends CoreDriverScript {

	Config config = CoreDriverScript.config;
	final SetupSelenium setupSelenium = CoreDriverScript.setupSelenium;
	public static TestDataReader dataReader = new TestDataReader();	
	TreeMap<String, String> dataMap = new TreeMap<String, String>();
	static String methodDesc = "temp";

	//Business Classes initilization

	LoginToMBG login = new LoginToMBG();
	SelectAgencyPage selectAgencyPage = new SelectAgencyPage();
	NewQuotePage newQuote = new NewQuotePage();
	ApplicantPage applicantionPage = new ApplicantPage();
	DriversPage driverspage = new DriversPage();
	VehiclesPage vehiclesPage = new VehiclesPage();
	CoveragesPage coveragesPage = new CoveragesPage();
	UnderWritingPage underwritingPage = new UnderWritingPage();
	GeneralInformationPage generalInformationPage = new GeneralInformationPage(); 
	CommonBusinessActions commonBusinessActions = new CommonBusinessActions();
	SubmissionPage submissionPage = new SubmissionPage();
	ViolationsPage violationsPage = new ViolationsPage();
	PremiumDetailsPage premiumDetailsPage = new PremiumDetailsPage();
	AdditionalInterestPage additionalInterestPage = new AdditionalInterestPage();

	public static String getMethodDescription(){
		return methodDesc;
	}

	@Test
	public void loginToMBGroup() throws Exception
	{
		try
		{				
			frameworkLog.info("Login functionality");
			methodDesc = "Login to MBG";		
			driver.get(Config.getConfig().getConfigProperty(Constants.URL));
			dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),"TransactionData_NB", CoreDriverScript.currentiterationrow);
			login.LoginAsAgent(dataMap);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="loginToMBGroup")
	public void SelectAgency() throws Exception
	{
		try
		{				
			frameworkLog.info("Select Agency functionality");
			methodDesc = "Select Agency";			
			selectAgencyPage.SelectAgency(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	@Test(dependsOnMethods="SelectAgency")	
	public void FillNewQuotePage() throws Exception

	{
		try 
        {
               frameworkLog.info("New Quote filling Functionality");
               if(dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("Search")){
                     methodDesc = "Searching existing quote";
                     commonBusinessActions.SearchQuote(CoreDriverScript.strQuoteNumber);
                     commonBusinessActions.NavigateToPage("Submission"); // to be implemented
                     submissionPage.MakeChanges();
                     actions.WaitTillRender();
                     
                      if(config.getConfigProperty(Constants.BROWSERTYPE).equalsIgnoreCase("iexplore")){
                     try
                     {
                            driver.navigate().refresh();
                            Alert objAlert = driver.switchTo().alert();
                            if(objAlert != null)
                            {
                                   System.out.println(objAlert.getText());
                                   objAlert.accept();
                            }

                     }
                     catch(UnhandledAlertException uae)
                     {
                            System.out.println("No such Alert Present while Refreshing the page...");
                     }
                     }
                                          
                            commonBusinessActions.NavigateToPage("Applicant");
                     
               }else{
                     methodDesc = "filling new quote";             
                     newQuote.FillNewQuoteDetails(dataMap);
                     strQuoteNumber = "";
                     blnExecuteReRef=false;
               }


        }
        catch(Exception e)
        {
               e.printStackTrace();
               actions.postTestStep("FAIL");
               throw e;
        }

	}

	@Test(dependsOnMethods="FillNewQuotePage")
	public void FillApplicantData() throws Exception
	{
		try 
		{
			if(dataMap.get("APPLICANT_PAGE").equalsIgnoreCase("YES")||dataMap.get("APPLICANT_PAGE").equalsIgnoreCase("Y")){
				frameworkLog.info("Applicant page filling Functionality");
				methodDesc = "filling Applcant data";		
				applicantionPage.FillApplicantGeneralDetails(dataMap);
				applicantionPage.FillNamedInsuredDetails(dataMap);
				applicantionPage.FillApplicantAddressDetails(dataMap);
			}else{
				frameworkLog.info("No action on Applicant page");
				methodDesc = "Skipping Applicant page";		
			}
			applicantionPage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="FillApplicantData")
	public void UpdateDrivers() throws Exception
	{
		try
		{
			if(dataMap.get("DRIVERS_PAGE").equalsIgnoreCase("YES")||dataMap.get("DRIVERS_PAGE").equalsIgnoreCase("Y"))
			{
				if(dataMap.get("DRIVER_ACTION").equalsIgnoreCase("ADD")){	
					frameworkLog.info("Adding Drivers Functionality");
					methodDesc = "Adding drivers data";
					if(dataMap.get("Scenario_ID").equalsIgnoreCase("TC068_REF"))
					{
						driverspage.AddNamedInsuredDrivers(dataMap);
					}
					else
					{
						driverspage.AddDrivers(dataMap);		
					}
				}
				else if(dataMap.get("DRIVER_ACTION").equalsIgnoreCase("UPDATE")){
					frameworkLog.info("Updating Drivers Functionality");
					methodDesc = "Updating drivers data";
					driverspage.UpdateDrivers(dataMap);	//to be implemented
				}


			}
			driverspage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="UpdateDrivers")
	public void ViolationsPage() throws Exception
	{
		try
		{
			if(dataMap.get("VIOLATIONS_PAGE").equalsIgnoreCase("YES") && strQuoteNumber.length()==0)
			{
				System.out.println("REf case....!");
				frameworkLog.info("Operations on Violations Page");
				methodDesc = "Operations on Violations Page";
				violationsPage.Continue();

			}
			if(dataMap.get("VIOLATIONS_PAGE_LOSS").equalsIgnoreCase("YES") ||dataMap.get("VIOLATIONS_PAGE_ADD").equalsIgnoreCase("YES") && dataMap.get("VIOLATIONS_PAGE").equalsIgnoreCase("YES"))
			{

				violationsPage.AddViolationsAndLoss(dataMap);
			}

			else
			{

				methodDesc = "Submitting Violations Page";
			}      

			violationsPage.Submit();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}


	@Test(dependsOnMethods="ViolationsPage")
	public void UpdateVehicals() throws Exception
	{
		try
		{	
			if(dataMap.get("VEHICLES_PAGE").equalsIgnoreCase("YES")||dataMap.get("VEHICLES_PAGE").equalsIgnoreCase("Y"))
			{	
				String[] VehicleAction=dataMap.get("VEHICLE_ACTION").split("#");
				for(int iCounter=0;iCounter<VehicleAction.length;iCounter++){
					if(VehicleAction[iCounter].equalsIgnoreCase("ADD")){	
						frameworkLog.info("Add Vehicles Functionality");
						methodDesc = "Adding Vehicles data";
						vehiclesPage.AddVehicles(dataMap);	
						actions.WaitTillRender();
						actions.WaitTillRender();
						vehiclesPage.SelectOtherMBGPolicy(dataMap);
						actions.WaitTillRender();
					}else if(VehicleAction[iCounter].equalsIgnoreCase("UPDATE")){
						frameworkLog.info("Update Vehicles Functionality");
						methodDesc = "Updating Vehicles data";
						vehiclesPage.UpdateVehicles(dataMap);	
						actions.WaitTillRender();
						actions.WaitTillRender();
						vehiclesPage.SelectOtherMBGPolicy(dataMap);
						actions.WaitTillRender();

					}
				}
			}

			vehiclesPage.Submit();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="UpdateVehicals")
	public void AddCoverages() throws Exception
	{
		try
		{
			if(dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("YES")||dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("Y")){
				frameworkLog.info("Adding Coverages");
				methodDesc = "Adding Coverages data and Submitting";
				coveragesPage.fillCoverageDetails(dataMap);

			}else{
				methodDesc = "Submitting Coverages page";
			}				
			coveragesPage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="AddCoverages")
	public void ActionsOnPremiumDetailsPage() throws Exception
	{

		try
		{
			if(dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("YES")||dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("Y")){
				methodDesc = "Adding Coverages data and Submitting";
				premiumDetailsPage.ClickonButtonCompleteApplication();
			}else{
				methodDesc = "CompleteApplication in Premium page";
				premiumDetailsPage.Submit();
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="ActionsOnPremiumDetailsPage")
	public void ActionsonAdditionalInterest() throws Exception
	{
		try
		{
			if(dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("YES")||dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("Y")){
				methodDesc = "Updating Additional interest page";
			}else{
				methodDesc = "Submitting Additional interest page";
			}
			additionalInterestPage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="ActionsonAdditionalInterest")
	public void ActionsonUnderWritingPage() throws Exception
	{
		try
		{
			if(dataMap.get("UNDERWRITING_PAGE").equalsIgnoreCase("YES")||dataMap.get("UNDERWRITING_PAGE").equalsIgnoreCase("Y")){
				frameworkLog.info("Selecting Questions in underwriting page");
				methodDesc = "Answering the Questions";

				if(dataMap.get("UNDERWRITING_DATA").trim().length()>0){
					underwritingPage.AnswerTheQuestions(dataMap);
				}
				underwritingPage.QuestionsComplete();				
			}else{
				methodDesc = "Submitting Underwriting page";
			}			
			underwritingPage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="ActionsonUnderWritingPage")
	public void GeneralInformationPage() throws Exception
	{
		try
		{	
			if(dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("YES")||dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("Y")){
				frameworkLog.info("Filling General Information");
				methodDesc = "Filling General Information";
				generalInformationPage.PriorCoverageDetails(dataMap);
			}else{
				methodDesc = "submitting General Information page";
			}
			generalInformationPage.Submit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="GeneralInformationPage")
	public void SubmissionsPage() throws Exception
	{
		boolean blnStatus = false;
		try
		{
			if(dataMap.get("SUBMISSION_PAGE").equalsIgnoreCase("YES")||dataMap.get("SUBMISSION_PAGE").equalsIgnoreCase("Y"))
			{
				frameworkLog.info("Validating Vins and Refer to UnderWriter");
				methodDesc = "Validating VINS";

				if(dataMap.get("MBGUMBRELLA")!="" &&dataMap.get("MBGUMBRELLA").equalsIgnoreCase("YES"))
				{
					actions.check("chkMBGUmbrella");
					actions.WaitTillRender();
					actions.type("txtPolicyNumber", dataMap.get("MBGUMBRELLA"));
					actions.WaitTillRender();
				}
				else if(dataMap.get("MBGUMBRELLA")!="" &&dataMap.get("MBGUMBRELLA").equalsIgnoreCase("NO"))
				{
					actions.unCheck("chkMBGUmbrella");
					actions.WaitTillRender();
				}

				if(dataMap.get("VALIDATE_VINS").equalsIgnoreCase("YES")||dataMap.get("VALIDATE_VINS").equalsIgnoreCase("Y"))
				{
					submissionPage.ValidateVINS(dataMap);
				}
				strQuoteNumber = commonBusinessActions.strGetQuoteNumber();
				System.out.println(strQuoteNumber);
				String ExpectedRatingMessge = dataMap.get("RATING_MESSAGE");
				if(ExpectedRatingMessge.contains("No Message"))
				{
					actions.postTestStep("PASS", "No Rating Message after changes in Submission Page ", "Reffered to Underwriter is not Required");
				}
				else
				{
					blnStatus = commonBusinessActions.ValidateRatingMessage(dataMap.get("RATING_MESSAGE"));
					commonBusinessActions.ValidateUnexpectedRatingMessage(dataMap.get("UNEXPECTED_RATING_MESSAGE"));
					if(blnStatus)
					{
						submissionPage.ReferToUnderWriting();
					}
				}

			}

			else
			{

				actions.postTestStep("PASS", "No Rating Message without changes in submission page", "Reffered to Underwriter is not Required");
				//code to handle what to do if no message.....
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="SubmissionsPage")

	public void LoginAsUnderWriter() throws Exception 	{
		try
		{	
			if(blnExecuteReRef)
			{
				if(dataMap.get("UNDERWRITER_LOGIN").equalsIgnoreCase("YES")||dataMap.get("UNDERWRITER_LOGIN").equalsIgnoreCase("Y"))
				{			
					frameworkLog.info("Login As UnderWriter functionality");
					methodDesc = "Login to MBG As UnderWriter";
					setupSelenium.stopEngine();
					setup();
					driver.get(Config.getConfig().getConfigProperty(Constants.URL));		
					dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),"TransactionData_NB", CoreDriverScript.currentiterationrow);
					login.LoginAsUW(dataMap);
					
					selectAgencyPage.SelectAgencyInUW(dataMap);

					commonBusinessActions.SearchQuote(strQuoteNumber);
					commonBusinessActions.NavigateTillSubmission();
					submissionPage.ApproveQuote();

					commonBusinessActions.LogoutMBG();
				}
				else
				{
					methodDesc = "No Underwriter Approval Required";
					commonBusinessActions.LogoutMBG();
				}
			}
			else
			{
				actions.postTestStep("FAIL", "Underwriter Login Not Required As expected Rating Message is not displayed", "Underwriter Login Not Required As expected Rating Message is not displayed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	
}
