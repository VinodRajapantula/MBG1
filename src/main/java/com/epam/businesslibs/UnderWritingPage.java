package com.epam.businesslibs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.TestDataReader;

public class UnderWritingPage extends CoreDriverScript {
	private static final Logger LOGGER = Logger.getLogger(UnderWritingPage.class);

	/* 	**********************Method Header******************************************************************
	Method Name					: SelectAllListItems	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Select All the list items to NO
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
	public void SelectAllListItems() throws Exception
	{
		try
		{	actions.postTestStep("PASS","Underwriter page Questions", "Filling answers..");
			List<WebElement> eleRatingMessages=actions.findElements("lstGeneralInfoYesNO");
			for (WebElement element : eleRatingMessages)
			{
				Select objSelect = new Select(element);
				objSelect.selectByVisibleText("No");

			}
			actions.postTestStep("PASS","Underwriter page Questions", "Filled the answers..");
		}
		catch (Exception e) {					
			LOGGER.error("Exception in SelectAllListItems method in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "SelectAllListItems - Exit", "Exception occurred in SelectAllListItems method");
			throw e;
		}

	}
	
	public void SelectAllListItems(String itemValue) throws Exception
	{
		try
		{	actions.postTestStep("PASS","Underwriter page Questions", "Filling answers..");
			List<WebElement> eleRatingMessages=actions.findElements("lstGeneralInfoYesNO");
			for (WebElement element : eleRatingMessages)
			{
				Select objSelect = new Select(element);
				objSelect.selectByVisibleText(itemValue);

			}
			actions.postTestStep("PASS","Underwriter page Questions", "Filled the answers..");
		}
		catch (Exception e) {					
			LOGGER.error("Exception in SelectAllListItems method in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "SelectAllListItems - Exit", "Exception occurred in SelectAllListItems method");
			throw e;
		}

	}
	/* 	**********************Method Header******************************************************************
	Method Name					: AnswerTheQuestions	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Answer the Questions 
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
	public void AnswerTheQuestions(TreeMap<String,String> dataMap) throws Exception{
		try{
			if(!actions.isLoaded("divUnderWriting_PageTitle")){
				LOGGER.error("User is not in UnderWriting Page");
				actions.postTestStep("FAIL", "AnswerTheQuestions - Exit", "Underwriting Page not found");
				throw new FrameworkExceptions("User is not in UnderWriting Page");
			}
			actions.postTestStep("PASS","Underwriter page Questions", "Filling answers..");
			if(dataMap.get("UNDERWRITING_DATA").trim().length()>0){
				if(dataMap.get("UNDERWRITING_EDITQ").toUpperCase().contains("Y")){
					actions.click("btnUW_EditQuestions");
				}
				if(dataMap.get("UNDERWRITING_DATA").toUpperCase().contains("ALL-NO")){
					SelectAllListItems();
				}else
				{
				Map<String, String> objRowData = new HashMap<String,String>();
				objRowData = TestDataReader.gFunc_ReadTestData("UWQuestions",dataMap.get("UNDERWRITING_DATA"));
				System.out.println(objRowData);
				SelectAllListItems();
				
				if(objRowData.get("QA1").trim().length()>0){
					SelectYesToQuestion(0);
					String[] arrQA1 = objRowData.get("QA1").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(iCount>0){ // to handle the Add button
//								click Add button and update code below to operate on elements based on index
							}
							if(arrEachField[iCount].toUpperCase().contains("VNUM=")){	
								actions.selectOptionByText("lstUW_OtherOwnerVehicleNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("OWNER=")){	
								actions.type("txtUW_OtherOwnerName", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA2").trim().length()>0){
					SelectYesToQuestion(1);
					String[] arrQA1 = objRowData.get("QA2").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("VNUM=")){	
								actions.selectOptionByText("lstUW_CarModifiedVehicleNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("DESCRIPTION=")){	
								actions.type("txtUW_CarModifiedDescription", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("COST=")){	
								actions.type("txtUW_CarModifiedCost", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA3").trim().length()>0){
					SelectYesToQuestion(2);
					String[] arrQA1 = objRowData.get("QA3").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("VNUM=")){	
								actions.selectOptionByText("lstUW_ExistingDamageVehicleNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("DESCRIPTION=")){	
								actions.type("txtUW_ExistingDamageDescription", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA4").trim().length()>0){
					SelectYesToQuestion(3);
					String[] arrQA1 = objRowData.get("QA4").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_OtherLossesDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("DESCRIPTION=")){	
								actions.type("txtUW_OtherLossesDescription", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("COST=")){	
								actions.type("txtUW_OtherLossesCost", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}							
						}
					}
				}

				if(objRowData.get("QA5").trim().length()>0){
					SelectYesToQuestion(4);
					String[] arrQA1 = objRowData.get("QA5").split("#"); //should be enhanced when multiple sets of data to be entered
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("OPERATORSNUM=")&&iIndex==0){	
								actions.type("txtUW_GenInfoOperatorsNum", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("NamedInsured=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceNamedInsured", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("YEAR=")){	
								actions.type("txtUW_OtherInsuranceYear", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("MAKE=")){	
								actions.type("txtUW_OtherInsuranceMake", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("MODEL=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceModel", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("CARRIER=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceCarrier", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("NAIC=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceNAIC", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("POLICYNUM=".toUpperCase())){	
								actions.type("txtUW_OtherInsurancePolicyNum", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA6").trim().length()>0){
					SelectYesToQuestion(5);
					String[] arrQA1 = objRowData.get("QA6").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("PolicyNum=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceMBGPolicyNum", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("InsuranceType=".toUpperCase())){	
								actions.type("txtUW_OtherInsuranceMBGInsType", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}					
						}
					}
				}
				
				if(objRowData.get("QA7").trim().length()>0){
					SelectYesToQuestion(6);
					String[] arrQA1 = objRowData.get("QA7").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_MilitaryServiceDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("BaseLocation=".toUpperCase())){	
								actions.type("txtUW_MilitaryServiceBaseLocation", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("VehicleAtBase=".toUpperCase())){	
								actions.selectOptionByText("lstUW_MilitaryServiceVehicleAtBase", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA8").trim().length()>0){
					SelectYesToQuestion(7);
					String[] arrQA1 = objRowData.get("QA8").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_LicenseSuspendedDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("StartDate=".toUpperCase())){	
								actions.type("txtUW_LicenseSuspendedStartDate", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("EndDate=".toUpperCase())){	
								actions.type("txtUW_LicenseSuspendedEndDate", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Explanation=".toUpperCase())){	
								actions.type("txtUW_LicenseSuspendedExplanation", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("ReinstatementDate=".toUpperCase())){	
								actions.type("txtUW_LicenseSuspendedReinstateDate", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA9").trim().length()>0){
					SelectYesToQuestion(8);
					String[] arrQA1 = objRowData.get("QA9").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_ImpairmentDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("DESCRIPTION=")){	
								actions.type("txtUW_ImpairmentDescription", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}							
						}
					}
				}
				
				if(objRowData.get("QA10").trim().length()>0){
					SelectYesToQuestion(9);
					String[] arrQA1 = objRowData.get("QA10").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_MedicalTreatmentDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Explanation=".toUpperCase())){	
								actions.type("txtUW_MedicalTreatmentExplanation", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}							
						}
					}
				}
				
				if(objRowData.get("QA11").trim().length()>0){
					SelectYesToQuestion(10);
					String[] arrQA1 = objRowData.get("QA11").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_FinancialFilingDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Reason=".toUpperCase())){	
								actions.type("txtUW_FinancialFilingReason", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("FilingDate=".toUpperCase())){	
								actions.type("txtUW_FinancialFilingFilingDate", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
						}
					}
				}
				
				if(objRowData.get("QA12").trim().toUpperCase().contains("Y")){
					SelectYesToQuestion(11);
				}
				
				if(objRowData.get("QA13").trim().length()>0){
					SelectYesToQuestion(12);
					String[] arrQA1 = objRowData.get("QA13").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_CoverageDeclinedDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Reason=".toUpperCase())){	
								actions.type("txtUW_CoverageDeclinedReason", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}							
						}
					}
				}
				
				if(objRowData.get("QA14").trim().toUpperCase().contains("Y")){
					SelectYesToQuestion(13);
				}
				
				if(objRowData.get("QA15").trim().toUpperCase().contains("Y")){
					SelectYesToQuestion(14);
				}
				
				if(objRowData.get("QA16").trim().length()>0){
					SelectYesToQuestion(15);
					String[] arrQA1 = objRowData.get("QA16").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_LegalActionDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Explanation=".toUpperCase())){	
								actions.type("txtUW_LegalActionExplanation", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();	
							}							
						}
					}
				}
				
				if(objRowData.get("QA17").trim().length()>0){
					SelectYesToQuestion(16);
					String[] arrQA1 = objRowData.get("QA17").split("#");
					for(int iIndex=0; iIndex<arrQA1.length; iIndex++){
						String[] arrEachField=arrQA1[iIndex].split(";");
						for(int iCount=0; iCount<arrEachField.length; iCount++){
							if(arrEachField[iCount].toUpperCase().contains("DRVRNUM=")){	
								actions.selectOptionByText("lstUW_WithoutLiabilityDriverNumber", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}
							if(arrEachField[iCount].toUpperCase().contains("Explanation=".toUpperCase())){	
								actions.type("txtUW_WithoutLiabilityExplanation", arrEachField[iCount].split("=")[1].trim());
								actions.WaitTillRender();
							}							
						}
					}
				}
				}
				actions.postTestStep("PASS","Underwriter page Questions", "Filled the answers..");
			}//
			
		}catch (Exception e){
			LOGGER.error("Exception in seleccting Answers for Questions in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Answer The questions - Exit", "Exception occurred in AnswerTheQuestions method");
			throw e;
		}
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: SelectYesToQuestion	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To select YES option for Particular Question.
	*List of arguments       	: Integer QNum
	*Return value            	: boolean
	*Notes					 	:	
	*********************************************************************************************************	
	Revision History
	*********************************************************************************************************
	Modified Date				: MM-DD-YYYY			
	Modified By					: <Name>		
	Changed Description			: <Change comments>
 	********************************************************************************************************/ 
	private boolean SelectYesToQuestion(int QNum) throws Exception{

		try{
			List<WebElement> eleRatingMessages=actions.findElements("lstGeneralInfoYesNO");
			Select objSelect = new Select(eleRatingMessages.get(QNum));
			objSelect.selectByVisibleText("Yes");
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("Exception in seleccting 'Yes' for Question# "+ QNum +" in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Select Yes to the given question number - Exit", "Exception occurred in SelectYesToQuestion method");
			throw e;
		}
		
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: Submit	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Next button and Navigate to General Information page.
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
			blnStatus = actions.isLoaded("divUnderWriting_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","UnderWriting page Submission", "Submitting the page...");
				// method to be added for capturing screenshot before submission
				actions.click("btnNext");
				if(actions.isLoaded("divGeneralInfo_PageTitle")){
					blnStatus= true;
					actions.postTestStep("PASS","UnderWriting page Submission", "UnderWriting page submitted successfully");
				}
				else
				{
					LOGGER.error("Object 'divGeneralInfo_PageTitle' not found.");
					actions.postTestStep("FAIL", "Underwriting Page Submit - Exit", "General information Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divUnderWriting_PageTitle' not found.");
				actions.postTestStep("FAIL", "Underwriting Page Submit - Exit", "Underwriting Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in Submit method in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "Underwriting Submit - Exit", "Exception occurred in Submit method in UnderWritingPage");
			throw e;
		}
		return blnStatus;
	}
	/* 	**********************Method Header******************************************************************
	Method Name					: QuestionsComplete	
	Module						: NA.
	Compatible Starting With	: NA.	
	*********************************************************************************************************
	*Description             	: To Click on Question Complete button.
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
	public boolean QuestionsComplete() throws Exception {
		// TODO Auto-generated method stub
		boolean blnStatus = false;

		try
		{
			blnStatus = actions.isLoaded("divUnderWriting_PageTitle");
			if(blnStatus)
			{	actions.postTestStep("PASS","Underwriter page Questions", "Clicking Questions Complete..");
				// method to be added for capturing screenshot before submission
				actions.click("btnQuestionsComplete");
				if(actions.isLoaded("divUnderWriting_PageTitle")){
					blnStatus= false;
					actions.postTestStep("PASS","Underwriter page Questions", "Filled answers successfully");
				}
				else
				{
					LOGGER.error("Object 'divGeneralInfo_PageTitle' not found.");
					actions.postTestStep("FAIL", "Underwriting Page QuestionsComplete - Exit", "Underwriting Page not found");
					throw new Exception();
				}
			}
			else
			{
				LOGGER.error("Object 'divUnderWriting_PageTitle' not found.");
				actions.postTestStep("FAIL", "Underwriting Page QuestionsComplete - Exit", "Underwriting Page not found");
				throw new Exception();
			}
		}
		catch (Exception e) {					
			LOGGER.error("Exception in QuestionsComplete method in UnderWritingPage");
			e.printStackTrace();
			actions.postTestStep("FAIL", "QuestionsComplete Button Click in underwritingpage - Exit", "Exception occurred in QuestionsComplete method in UnderWritingPage");
			throw e;
		}
		return blnStatus;
	}

}
