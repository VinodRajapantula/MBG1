package com.epam.scenarios;

import java.util.TreeMap;

import org.testng.annotations.Test;

import com.epam.businesslibs.*;
import com.epam.driver.CoreDriverScript;
import com.epam.driver.SetupSelenium;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.TestDataReader;

public class Endorsement extends CoreDriverScript {

	Config config = CoreDriverScript.config;
	final SetupSelenium setupSelenium = CoreDriverScript.setupSelenium;
	public static TestDataReader dataReader = new TestDataReader();	
	TreeMap<String, String> dataMap = new TreeMap<String, String>();
	static String methodDesc = "skip";
	boolean blnExecute=true;
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
	PaymentOptionsPage paymentOptionsPage = new PaymentOptionsPage();
	ThankYouPage thankYouPage = new ThankYouPage();	
	TransActPage transAct = new TransActPage();
	
	public static String getMethodDescription(){
		return methodDesc;
	}

	@Test
	public void flowControl() throws Exception{
		System.out.println(Scen_ID);
		if(Scen_ID.toUpperCase().contains("REF")){
			methodDesc = "Executing Referral scenario for Endorsement";
			blnExecute=false;
		}else{
			methodDesc = "Executing Policy creation for Endorsement";
			strPolicyNumber="";
			blnExecuteReRef=false;
		}
	}
	
	@Test(dependsOnMethods="flowControl")	
	public void loginAsUW() throws Exception
	{	if(blnExecute){
		try
		{				
			frameworkLog.info("Login functionality");
			methodDesc = "Login to MBG as Underwriter";		
			driver.get(Config.getConfig().getConfigProperty(Constants.URL));
			System.out.println(Config.getConfig().getConfigProperty(Constants.URL));
			dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),"TransactionData_END", CoreDriverScript.currentiterationrow);
			login.LoginAsUW(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}else{
		methodDesc="NA";
	}
	}

	@Test(dependsOnMethods="loginAsUW")	
	public void SelectAgencyInUW() throws Exception
	{	if(blnExecute){
		try
		{				
			frameworkLog.info("Select Agency functionality");
			methodDesc = "Select Agency";			
			selectAgencyPage.SelectAgencyInUW(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
		}else{
			methodDesc="NA";
		}
	}
	
	@Test(dependsOnMethods="SelectAgencyInUW")
	public void FillNewQuotePage_End() throws Exception

	{
		try 
		{	if(blnExecute){
				frameworkLog.info("New Quote filling Functionality");
				if(dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("Search")){
					methodDesc = "Searching existing quote";
					commonBusinessActions.SearchQuote(CoreDriverScript.strQuoteNumber);
					commonBusinessActions.NavigateToPage("Submission");	// to be implemented
					submissionPage.MakeChanges();
					actions.WaitTillRender();
					commonBusinessActions.NavigateToPage("Applicant");
				}else{
					methodDesc = "filling new quote";		
					newQuote.FillNewQuoteDetails(dataMap);
					strQuoteNumber = "";
					blnExecuteReRef=false;
				}
			}else{
				methodDesc="NA";
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="FillNewQuotePage_End")
	public void FillApplicantData_End() throws Exception
	{
		try 
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="FillApplicantData_End")
	public void UpdateDrivers_End() throws Exception
	{
		try
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="UpdateDrivers_End")	
	public void ViolationsPage_End() throws Exception
    {
           try
           {	if(blnExecute){
                  if(dataMap.get("VIOLATIONS_PAGE").equalsIgnoreCase("YES") && strQuoteNumber.length()==0)
                  {
                        System.out.println("REf case....!");
                        frameworkLog.info("Operations on Violations Page");
                        methodDesc = "Operations on Violations Page";
                        violationsPage.Continue();
        				if(dataMap.get("VIOLATIONS_MVROVERRIDE").trim().length()>0){//the value can be 'ALL' or list of drivers to be impacted
        					violationsPage.MVROverride(dataMap);
        				}
                  }
                  if(dataMap.get("VIOLATIONS_PAGE_LOSS").equalsIgnoreCase("YES") ||dataMap.get("VIOLATIONS_PAGE_ADD").equalsIgnoreCase("YES") && dataMap.get("VIOLATIONS_PAGE").equalsIgnoreCase("YES"))
                  {
                        violationsPage.AddViolationsAndLoss(dataMap);
                  }else
                  {
                        methodDesc = "Submitting Violations Page";
                  }
                  violationsPage.Submit();
           		}else{
           			methodDesc="NA";
           		}
           }
           catch(Exception e)
           {
                  e.printStackTrace();
                  actions.postTestStep("FAIL");
                  throw e;
           }
    }
	
	@Test(dependsOnMethods="ViolationsPage_End")
	public void UpdateVehicals_End() throws Exception
	{
		try
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="UpdateVehicals_End")
	public void AddCoverages_End() throws Exception
	{
		try
		{	if(blnExecute){
				if(dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("YES")||dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("Y")){
					frameworkLog.info("Adding Coverages");
					methodDesc = "Adding Coverages data and Submitting";
					coveragesPage.fillCoverageDetails(dataMap);
	
				}else{
					methodDesc = "Submitting Coverages page";
				}				
				coveragesPage.Submit();
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="AddCoverages_End")
	public void ActionsOnPremiumDetailsPage_End() throws Exception
	{

		try
		{	if(blnExecute){
				if(dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("YES")||dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("Y")){
					methodDesc = "Adding Coverages data and Submitting";
					premiumDetailsPage.ClickonButtonCompleteApplication();
				}else{
					methodDesc = "CompleteApplication in Premium page";
					premiumDetailsPage.Submit();
				}
			
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="ActionsOnPremiumDetailsPage_End")
	public void ActionsonAdditionalInterest_End() throws Exception
	{
		try
		{	if(blnExecute){
				if(dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("YES")||dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("Y")){
					methodDesc = "Updating Additional interest page";
				}else{
					methodDesc = "Submitting Additional interest page";
				}
				additionalInterestPage.Submit();
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="ActionsonAdditionalInterest_End")
	public void ActionsonUnderWritingPage_End() throws Exception
	{
		try
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="ActionsonUnderWritingPage_End")
	public void GeneralInformationPage_End() throws Exception
	{
		try
		{	if(blnExecute){
				if(dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("YES")||dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("Y")){
					frameworkLog.info("Filling General Information");
					methodDesc = "Filling General Information";
					generalInformationPage.PriorCoverageDetails(dataMap);
				}else{
					methodDesc = "submitting General Information page";
				}
				generalInformationPage.Submit();
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="GeneralInformationPage_End")
	public void SubmissionsPage_End() throws Exception
	{
		try
		{	if(blnExecute){
			blnExecute=false;
				if(dataMap.get("SUBMISSION_PAGE").equalsIgnoreCase("YES")||dataMap.get("SUBMISSION_PAGE").equalsIgnoreCase("Y"))
				{
					frameworkLog.info("Validating Vins and Refer to UnderWriter");
					methodDesc = "Validating VINS";
					
					if(dataMap.get("VALIDATE_VINS").equalsIgnoreCase("YES")||dataMap.get("VALIDATE_VINS").equalsIgnoreCase("Y"))
					{
						submissionPage.ValidateVINS(dataMap);
					}
					
					if(dataMap.get("SUBMISSION_BIND").toUpperCase().contains("Y")){
						submissionPage.BindAndSubmit();
						paymentOptionsPage.Continue(dataMap);
						
						strPolicyNumber = commonBusinessActions.strGetQuoteNumber();
						if(strPolicyNumber.trim()==""){
							blnExecuteReRef=false;
							actions.postTestStep("FAIL", "Policy Number capture", "Failed to capture the policy number");
						}else{
							blnExecuteReRef=true;
						}
						thankYouPage.Exit();
					}
				}
			}else{
				methodDesc="NA";
				blnExecute=true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}

	}

	@Test(dependsOnMethods="SubmissionsPage_End")
	public void loginAsAgent_End() throws Exception
	{
		try
		{	if(blnExecute){
					frameworkLog.info("Login functionality");
					methodDesc = "Login to MBG as Agent";	
					driver.get(Config.getConfig().getConfigProperty(Constants.URL));
					dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),"TransactionData_END", CoreDriverScript.currentiterationrow);
					login.LoginAsAgent(dataMap);
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	
	@Test(dependsOnMethods="loginAsAgent_End")	
	public void SelectAgency_End() throws Exception
	{	if(blnExecute){
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
		}else{
			methodDesc="NA";
		}
	}
	
	@Test(dependsOnMethods="SelectAgency_End")
	public void EndorseOrEditPolicy_End() throws Exception 	{
		try
		{	
			if(blnExecute)
			{
				if(dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("ENDORSE")){
					frameworkLog.info("Search and Endorse a policy functionality");
					methodDesc = "Search and Endorse a policy";
					commonBusinessActions.SearchQuote(strPolicyNumber);					
					transAct.SelectTransaction(dataMap);
				}else if(dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("SEARCH")){
					frameworkLog.info("Search and Edit a policy functionality");
					methodDesc = "Search and Edit a policy";
					commonBusinessActions.SearchQuote(strPolicyNumber);
					transAct.EditPolicy();
					actions.WaitTillRender();
					submissionPage.MakeChanges();
					actions.WaitTillRender();
					commonBusinessActions.NavigateToPage("Applicant");
				}else{
					actions.postTestStep("FAIL", "QUOTATION_TYPE is not valid", dataMap.get("QUOTATION_TYPE"));
					throw new Exception();
				}
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	
	
	@Test(dependsOnMethods="EndorseOrEditPolicy_End")
	public void FillApplicantData() throws Exception
	{
		try 
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
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
		{	if(blnExecute){
				if(dataMap.get("DRIVERS_PAGE").equalsIgnoreCase("YES")||dataMap.get("DRIVERS_PAGE").equalsIgnoreCase("Y"))
				{
					if(dataMap.get("DRIVER_ACTION").equalsIgnoreCase("ADD")){	
						frameworkLog.info("Adding Drivers Functionality");
						methodDesc = "Adding drivers data";
						if(dataMap.get("Scenario_ID").equalsIgnoreCase("TC068_REF")||dataMap.get("Scenario_ID").equalsIgnoreCase("TC_END_012_REREF")||dataMap.get("Scenario_ID").equalsIgnoreCase("TC_END_PA_012_REREF"))
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
						driverspage.UpdateDrivers(dataMap);
					}
					else if(dataMap.get("DRIVER_ACTION").equalsIgnoreCase("DELETE")){
						frameworkLog.info("Deleting Drivers");
						methodDesc = "Deleting drivers";
						driverspage.DeleteDrivers(dataMap);	
					}
	
	
				}
				driverspage.Submit();
			}else{
				methodDesc="NA";
			}
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
           {	if(blnExecute){
                  if(dataMap.get("VIOLATIONS_PAGE").equalsIgnoreCase("YES") && strPolicyNumber.length()==0 )
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
           		}else{
    				methodDesc="NA";
    			}
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
		{	if(blnExecute){
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
			}else{
				methodDesc="NA";
			}
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
		{	if(blnExecute){
				if(dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("YES")||dataMap.get("COVERAGES_PAGE").equalsIgnoreCase("Y")){
					frameworkLog.info("Adding Coverages");
					methodDesc = "Adding Coverages data and Submitting";
					coveragesPage.fillCoverageDetails(dataMap);
	
				}else{
					methodDesc = "Submitting Coverages page";
				}				
				coveragesPage.Submit();
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="AddCoverages")
	public void ActionsOnPremiumANDAdditionalInterest() throws Exception
	{ 
		try
		{
			if(blnExecute){
				ActionsonAdditionalInterest();
				ActionsOnPremiumDetailsPage();				
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	
	public void ActionsOnPremiumDetailsPage() throws Exception
	{ 
		try
		{	if(blnExecute){
				if(dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("YES")||dataMap.get("PREMIUM_PAGE").equalsIgnoreCase("Y")){
					methodDesc = "Adding Coverages data and Submitting";
					premiumDetailsPage.ClickonButtonCompleteApplication();
				}else{
					methodDesc = "CompleteApplication in Premium page";
					premiumDetailsPage.Submit();
				}
			}else{
				methodDesc="NA";
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

//	@Test(dependsOnMethods="ActionsOnPremiumDetailsPage")
	public void ActionsonAdditionalInterest() throws Exception
	{
		try
		{	if(blnExecute){
				if(dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("YES")||dataMap.get("ADDITIONALINTEREST_PAGE").equalsIgnoreCase("Y")){
					methodDesc = "Updating Additional interest page";
				}else{
					methodDesc = "Submitting Additional interest page";
				}
				additionalInterestPage.Submit();
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}

	@Test(dependsOnMethods="ActionsOnPremiumANDAdditionalInterest")
	public void ActionsonUnderWritingPage() throws Exception
	{
		try
		{	if(blnExecute && dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("New")){
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
			}else{
				methodDesc="NA";
			}
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
		{	if(blnExecute && dataMap.get("QUOTATION_TYPE").equalsIgnoreCase("New")){
				if(dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("YES")||dataMap.get("GENERALINFORMATION_PAGE").equalsIgnoreCase("Y")){
					frameworkLog.info("Filling General Information");
					methodDesc = "Filling General Information";
					generalInformationPage.PriorCoverageDetails(dataMap);
				}else{
					methodDesc = "submitting General Information page";
				}
				generalInformationPage.Submit();
			}else{
				methodDesc="NA";
			}
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
		{	if(blnExecute){
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
					
					strPolicyNumber = commonBusinessActions.strGetQuoteNumber();
					System.out.println(strPolicyNumber);
					
					if(strPolicyNumber.trim()==""){
						blnExecuteReRef=false;
						actions.postTestStep("FAIL", "Policy Number capture", "Failed to capture the policy number");
						blnExecute=false;
					}else{
						blnExecuteReRef=true;
					}
					
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
						}else{
							blnExecute=false;
							blnExecuteReRef=false;
						}
					}	
				}
	
				else
				{	
					actions.postTestStep("PASS", "No Rating Message without changes in submission page", "Reffered to Underwriter is not Required");
					//code to handle what to do if no message.....
				}
			}else{
				methodDesc="NA";
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
		{	if(blnExecute && blnExecuteReRef){
				if(dataMap.get("UNDERWRITER_LOGIN").equalsIgnoreCase("YES")||dataMap.get("UNDERWRITER_LOGIN").equalsIgnoreCase("Y"))
				{			
					frameworkLog.info("Login As UnderWriter functionality");
					methodDesc = "Login to MBG As UnderWriter";
					setupSelenium.stopEngine();
					setup();
					driver.get(Config.getConfig().getConfigProperty(Constants.URL));		
					dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),"TransactionData_END", CoreDriverScript.currentiterationrow);
					login.LoginAsUW(dataMap);
					selectAgencyPage.SelectAgencyInUW(dataMap);
					commonBusinessActions.SearchQuote(strPolicyNumber);
					transAct.EditPolicy();
					commonBusinessActions.NavigateToPage("Applicant");
					commonBusinessActions.NavigateTillSubmission_End();
					submissionPage.ApproveQuote();	
				}
				else
				{
					methodDesc = "No Underwriter Approval Required";
					commonBusinessActions.LogoutMBG();
				}
			}else{
				methodDesc="NA";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			actions.postTestStep("FAIL");
			throw e;
		}
	}
	
	@Test(dependsOnMethods="LoginAsUnderWriter")
	public void ReportManager() throws Exception 	{
		frameworkLog.info("Report manager for Endorsement");
		methodDesc = "Report manager for Endorsement";
		commonBusinessActions.LogoutMBG();
	}
}
