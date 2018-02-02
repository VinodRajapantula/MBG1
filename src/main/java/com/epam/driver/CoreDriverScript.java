package com.epam.driver;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.epam.fixtures.KeyboardActions;
import com.epam.fixtures.WebActions;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.FrameworkExceptions;
import com.epam.utils.report.common.Listener;
import com.epam.utils.report.common.Log4jLogger;
import com.epam.utils.report.reporting.ReportGenerator;
import com.epam.utils.report.reporting.Writer;
import com.epam.utils.report.testsuite.TestCase;
import com.epam.utils.report.testsuite.TestScenario;
import com.epam.utils.report.testsuite.TestScipt;
import com.epam.utils.report.testsuite.TestStep;
import com.epam.utils.report.testsuite.TestSuite;
import com.epam.utils.TestDataReader;

public class CoreDriverScript {
	public static Logger frameworkLog = Logger.getLogger(CoreDriverScript.class.getName());
	public final static SetupSelenium setupSelenium = new SetupSelenium();
	public static WebDriver driver;
	public static Config config;
	public static WebActions actions;
	public static KeyboardActions keyActions;
	public static String scenarioStatus = "";
	public static String testStatus;
	public static boolean isTestSenario;
	public static TestSuite tSuite;
	public static TestScenario tScenario;
	public static TestCase tCase;
	public static TestStep tStep;
	public static TestScipt testScipt;
	public static ReportGenerator reportGen;
	public static String dest;
	public static String screenshotFileType;
	public static boolean tcaseflag;
	public static long testStepsCount;
	public static String test_step_result = null;
	public static String screenshot_fileName = null;
	public static String screenShotFile = null;
	public static String currentTest;
	public static String currentTestDescription;
	public static String currentTestStepData;
	public static String keyword;
	public static String object;
	public static String currentTSID;
	public static String stepDescription;
	public static String proceedOnFail;
	public static String inputData;
	public static String inputDataValue;
	public static String outPutData;
	public static int testRepeat;
	public static String indx = null;
	public static int tsid;
	public static int testCasesCount;
	public static String runmode;
	public static long waitMilliSec;
	public static long waitSeconds;
	public static int currentiterationrow;
	public static HashMap<String, String> descripMap;
	static HSSFSheet sheet;
	public static TestDataReader dataReader = new TestDataReader();
	public static String ScenarioName;
	public static String Scen_ID;
	public static String strQuoteNumber;
	public static String strPolicyNumber;
	public static boolean blnExecuteReRef=true;


	/**
	 * Selecting the test Execution engine based on the input
	 * @throws Exception
	 */
	@BeforeClass
	public void setup() throws Exception {
		frameworkLog = Log4jLogger.getLog4jInstance();

		if(Config.getConfig().getConfigProperty(Constants.EXECUTIONENGINE).toLowerCase().equals("selenium webdriver"))
		{
			setupSelenium.startWebDriver(Config.getConfig().getConfigProperty(Constants.BROWSERTYPE));
			driver = setupSelenium.getDriver();
			actions = new WebActions(driver);
		}
		
		FileAppender fileAppender = null;
		Enumeration appenders = frameworkLog.getRootLogger().getAllAppenders();
		while(appenders.hasMoreElements()) {

			Appender currAppender = (Appender) appenders.nextElement();
			if(currAppender instanceof FileAppender) {
				fileAppender = (FileAppender) currAppender;
			}
		}

		if(fileAppender != null) {
			this.dest = fileAppender.getFile();
		}

		// this.dest = frameworkLog.getName();
	}

	/**
	 * Setting the Test suite properties
	 */
	private static void setTestSuiteProperties() {

		try {
			tSuite.setBrowserName(Config.getConfig().getConfigProperty(
					Constants.BROWSERTYPE));
			tSuite.setCategory(Config.getConfig().getConfigProperty(
					Constants.TESTSUITECATEGORY));
			tSuite.setDescription(Config.getConfig().getConfigProperty(
					Constants.TESTSUITEDESCRIPTION));
			tSuite.setIdTestSuite(Config.getConfig().getConfigProperty(
					Constants.TESTSUITEID));
			tSuite.setTestSuiteName(Config.getConfig().getConfigProperty(
					Constants.TESTSUITENAME));
			tSuite.seturl(Config.getConfig().getConfigProperty(
					Constants.TESTSUITEURL));
			tSuite.setExecutionEngine(Config.getConfig().getConfigProperty(
					Constants.EXECUTIONENGINE));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	/**
	 * Get Current date
	 */
	private static Date getCurrentDateTime() {
		Date dt = new Date();
		return dt;
	}

	/**
	 * Stopping the Test ExecutionEngine
	 */
	@AfterClass
	public void stopTest() {
		try {
			if(Config.getConfig().getConfigProperty(Constants.EXECUTIONENGINE).toLowerCase().equalsIgnoreCase("selenium webdriver"))
			{
				setupSelenium.stopEngine();
			}
		
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		config = Config.getConfig();
		try {
			FrameworkExceptions
			.sPrintMessage("Initializing the Automation Framework");
			FileInputStream fis = new FileInputStream(Config.getConfig()
					.getConfigProperty(Constants.TESTDATAPATH));
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheet("Scenarios");
			int testCasesCount = sheet.getLastRowNum();
			executeTestCases(testCasesCount);
		} catch (Exception e) {
			frameworkLog.info(e);
			e.printStackTrace();
			frameworkLog.info(e);

		} finally {
			FrameworkExceptions.sPrintMessage("End of Execution!!!!!!!!");
		}

	}

	/**
	 * @param testCasesCount
	 * @throws ClassNotFoundException
	 */
	private static void executeTestCases(int testCasesCount)
			throws ClassNotFoundException, Exception {
		try{		
		boolean tcaseflag = false;
		boolean isReport = false;
		String tScenarioEndTime = "";
		ReportGenerator reportGen = null;
		LinkedHashMap<String, LinkedHashMap<String, String>> scenarioDataMap = null;
//		Process process = new ProcessBuilder(Config.getConfig().getConfigProperty("caffeinePath")).start(); 
		for (int tcid = 1; tcid <= testCasesCount; tcid++) {
			CoreDriverScript.tSuite = new TestSuite();
			if (sheet.getRow(tcid).getCell(1).toString().equalsIgnoreCase("Y")) {
				String scenario = sheet.getRow(tcid).getCell(0).toString();
				if(scenario.equalsIgnoreCase("Endorsement")){
					scenarioDataMap = dataReader.getEntityData(scenario,"TransactionData_END");
				}else if(scenario.equalsIgnoreCase("NewBusiness")){
					scenarioDataMap = dataReader.getEntityData(scenario,"TransactionData_NB");
				}
				
				int iterationcount = 0;
				for(Entry<String, LinkedHashMap<String, String>> entry : scenarioDataMap.entrySet()) {
					if (!entry.getValue().get("EXECUTIONFLAG").equalsIgnoreCase("Y") || (!blnExecuteReRef && entry.getValue().get("QUOTATION_TYPE").equalsIgnoreCase("Search"))){
						continue;
					}
					
					iterationcount++;
					TestNG testng = new TestNG();
					reportGen = new ReportGenerator();
					setTestSuiteProperties();
					// test case object is created for reporting
					tCase = new TestCase();
					descripMap = new HashMap<String, String>();
					reportGen.setStartTime(getCurrentDateTime());
					tcaseflag = false;
					//isReport = false;
					testStatus = null;
					frameworkLog.debug("Test Case object created for reporting");
					frameworkLog.debug("Executing the Test Case : " + tcid);
					System.out.println("Executing the Test Case : " + scenario +" Iteration "+ iterationcount+" at row no "+tcid);

					currentiterationrow = Integer.parseInt(entry.getKey());
					List<Class> arraylistClasses = new ArrayList<Class>();
					scenarioStatus = "true";
					tScenario = new TestScenario();
					System.out.println("Scenarion ID = " + tScenario.getBusinessScenarioId() + "; Status = "+ scenarioStatus);

					Class classname = Class.forName("com.epam.scenarios."+ scenario);
					arraylistClasses.add(classname);
					descripMap.put(scenario, sheet.getRow(tcid).getCell(1).toString());
					frameworkLog.info("Execution Result of " + currentTest + " : "+ ((tcaseflag) ? "FAIL" : "PASS"));
					
					Class[] classes = ((List<Class>) arraylistClasses)
							.toArray(new Class[1]);
					testng.setTestClasses(classes);
					Listener ld = new Listener();
					testng.addListener((Object) ld);
					Scen_ID=entry.getValue().get("Scenario_ID");
					System.out.println(Scen_ID);
					if (iterationcount > 1){						
//						ScenarioName = scenario + "_"+iterationcount;
						ScenarioName = scenario + "_"+Scen_ID;	
						
						CoreDriverScript.tScenario.setBusinessScenarioId(ScenarioName);
						CoreDriverScript.tScenario.setTestScenarioRequirementId(ScenarioName);}
					else{
//						ScenarioName = scenario;
						ScenarioName = scenario + "_"+Scen_ID;
						CoreDriverScript.tScenario.setBusinessScenarioId(ScenarioName);
						CoreDriverScript.tScenario.setTestScenarioRequirementId(ScenarioName);
					}

					testng.setPreserveOrder(true);
					testng.run();
					isReport = true;
					if (isReport) {
						if (scenarioStatus.equalsIgnoreCase("false")) {
							testStatus = "FAIL";
							tScenario.setExecutionResult("FAIL");
						} else if (scenarioStatus.equalsIgnoreCase("true")) {
							testStatus = "PASS";
						} else {
							testStatus = "SKIP";
						}

						Date dt1 = new Date();
						DateFormat dateFormat = new SimpleDateFormat(
								Constants.DATEFORMAT);
						tScenarioEndTime = dateFormat.format(dt1);
						frameworkLog.info("Test scenarios added to the XML report");
					}
					reportGen.setEndTime(tScenarioEndTime);
					reportGen.addTestSuite(tSuite);
//					reportGen.generateXmlReport();
				}//for scenario iterations
				reportGen.generateXmlReport();
				if(Config.getConfig().getConfigProperty(Constants.SHAREDTESTRESULTPATH).trim().length()>0){
					Writer writer = new Writer();
					writer.refreshTestResultXMLFile(Config.getConfig().getConfigProperty(Constants.SHAREDTESTRESULTPATH));
				}
				//process.destroy();
			}
		}

		}catch(Exception e){	//Shanker 17-Nov-2016
			System.out.println("Error occured in CoreDriverScript");
			e.printStackTrace();
		}
	}

}
