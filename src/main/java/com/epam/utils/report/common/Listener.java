package com.epam.utils.report.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.report.testsuite.TestCase;
import com.epam.utils.report.testsuite.TestScenario;
import com.epam.utils.report.testsuite.TestScipt;


public class Listener implements ITestListener, ISuiteListener,
		IInvokedMethodListener {

	public long tStepStartTime = 0;
	public long tStepEndTime = 0;
	public long tStepExecTime = 0;
	public long tCaseExecutionTime = 0;
	public int tcCount = 0;
	public String currTestName = "";
	public static int count = 0;


	// This belongs to ISuiteListener and will execute before the Suite start

	public void onStart(ISuite arg0) {		
		Reporter.log("About to begin executing Suite " + arg0.getName(), true);

	}

	// This belongs to ISuiteListener and will execute, once the Suite is
	// finished

	public void onFinish(ISuite arg0) {		
		Reporter.log("About to end executing Suite " + arg0.getName(), true);

	}

	// This belongs to ITestListener and will execute before starting of Test
	// set/batch

	public void onStart(ITestContext arg0) {		
		Reporter.log("Starting Testset " + arg0.getName(), true);

	}

	// This belongs to ITestListener and will execute, once the Test set/batch
	// is finished

	public void onFinish(ITestContext arg0) {		
		Reporter.log("Completed executing test " + arg0.getName(), true);

	}

	// This belongs to ITestListener and will execute only when the test is pass

	public void onTestSuccess(ITestResult arg0) {
		CoreDriverScript.tScenario
				.setExecutionResult(CoreDriverScript.testStatus);
		CoreDriverScript.tScenario.addTestCase(CoreDriverScript.tCase);
		CoreDriverScript.tSuite.addTestScenario(CoreDriverScript.tScenario);
		printTestResults(arg0);

	}

	// This belongs to ITestListener and will execute only on the event of fail
	// test

	public void onTestFailure(ITestResult arg0) {
		CoreDriverScript.tScenario
				.setExecutionResult(CoreDriverScript.testStatus);
		CoreDriverScript.tScenario.addTestCase(CoreDriverScript.tCase);
		CoreDriverScript.tSuite.addTestScenario(CoreDriverScript.tScenario);
		// This is calling the printTestResults method
		printTestResults(arg0);
	}

	// This belongs to ITestListener and will execute before the main test start
	// (@Test)

	public void onTestStart(ITestResult arg0) {
		
		CoreDriverScript.tCase = new TestCase();
		CoreDriverScript.tScenario = new TestScenario();
		CoreDriverScript.testScipt = new TestScipt();
		CoreDriverScript.tcaseflag = false;
		// ts2 = td.getTestScenar();
		CoreDriverScript.testStatus = null;
		CoreDriverScript.tScenario.setBusinessScenarioId(CoreDriverScript.ScenarioName);
		CoreDriverScript.tScenario.setTestScenarioRequirementId(CoreDriverScript.ScenarioName);

		try {
			String desciption = CoreDriverScript.descripMap.get(arg0
					.getInstance().getClass().getSimpleName().toUpperCase());
			// temp=arg0.getInstance().getClass().getDeclaredMethod("getDescription",
			// null).invoke(arg0.getInstance(), null).toString();
			CoreDriverScript.tScenario.setBusinessScenarioDesc(desciption);
			CoreDriverScript.currentTest = arg0.getMethod().getMethodName();// method1.getRealClass().getSimpleName();
																			// //"Get ID of TC and set it here";
			CoreDriverScript.currentTestDescription = arg0.getMethod()
					.getMethodName();
			; // "Get description of TC and set it here";
			CoreDriverScript.testStepsCount = 2;// "Get test step count and set it here";
			CoreDriverScript.tCase
					.setTestCaseDesc(CoreDriverScript.currentTestDescription);
			CoreDriverScript.tCase.setTestCaseId(String
					.valueOf(CoreDriverScript.currentTest));
			CoreDriverScript.tCase.setTestScenario(CoreDriverScript.tScenario);
			CoreDriverScript.proceedOnFail = Config.getConfig()
					.getConfigProperty(Constants.ONFAIL);

			CoreDriverScript.testScipt.setTestCaseId(1);// set testcase id
														// number here
			CoreDriverScript.testScipt
					.setCurrentTest(CoreDriverScript.currentTest);
			// testScipt.setTestStepId(null);
			CoreDriverScript.testScipt.setIndex(null);
			CoreDriverScript.scenarioStatus = "true";

			CoreDriverScript.keyword = "get the keyword and set it here";
			CoreDriverScript.object = "get the object and set it here";
			CoreDriverScript.object = "validating input data for object";
			CoreDriverScript.currentTSID = "get current teststep id and set it here";
			CoreDriverScript.stepDescription = "get current step description and set it here";
			CoreDriverScript.inputDataValue = "get input data value and set it here";
			CoreDriverScript.outPutData = "get output data and set it here";
			CoreDriverScript.screenshotFileType = Config.getConfig()
					.getConfigProperty(Constants.SCREENSHOTFILETYPE);
			CoreDriverScript.inputData = "validating input data";

			// take screenshot - as of now screenshot is taken only for
			// failures.
			SimpleDateFormat sdt = new SimpleDateFormat("ddMMyyyy_hhmmss");
			Date now = new Date();
			String dateTime = sdt.format(now);
			CoreDriverScript.screenshot_fileName = CoreDriverScript.testScipt
					.getCurrentTest()
					+ "_TC"
					+ (CoreDriverScript.testScipt.getTestCaseId() - 1)
					+ "_TS"
					+ CoreDriverScript.testScipt.getTestStepId()
					+ "_"
					+ dateTime + CoreDriverScript.screenshotFileType;
			CoreDriverScript.screenShotFile = Config.getConfig()
					.getConfigProperty(Constants.SCREENSHOTPATH)
					+ CoreDriverScript.screenshot_fileName;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// This belongs to ITestListener and will execute only if any of the main
	// test(@Test) get skipped

	public void onTestSkipped(ITestResult arg0) {/*
												 * 
												 * CoreDriverScript.tScenario
												 * .setExecutionResult
												 * (CoreDriverScript
												 * .testStatus);
												 * CoreDriverScript
												 * .tScenario.addTestCase
												 * (CoreDriverScript.tCase); //
												 * CoreDriverScript
												 * .setReportingProperties();
												 * printTestResults(arg0);
												 */
	}


	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	// This is the method which will be executed in case of test pass or fail

	// This will provide the information on the test

	private void printTestResults(ITestResult result) {

		Reporter.log("Test Method resides in "
				+ result.getTestClass().getName(), true);

		if (result.getParameters().length != 0) {

			String params = null;

			for (Object parameter : result.getParameters()) {

				params += parameter.toString() + ",";

			}

			Reporter.log(
					"Test Method had the following parameters : " + params,
					true);

		}

		String status = null;

		switch (result.getStatus()) {

		case ITestResult.SUCCESS:
		    	status = "Pass";
			break;

		case ITestResult.FAILURE:
			status = "Failed";
			break;

		case ITestResult.SKIP:
			status = "Skipped";
			break;
		}

		Reporter.log("Test Status: " + status, true);

	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

		String textMsg = "About to begin executing following method : "
				+ returnMethodName(arg0.getTestMethod());

		Reporter.log(textMsg, true);

	}

	// This belongs to IInvokedMethodListener and will execute after every
	// method including @Before @After @Test

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {

		if (arg0.isTestMethod()) {
			String methodDesc = "temporary";
			try {
				methodDesc = arg1.getInstance().getClass()
						.getDeclaredMethod("getMethodDescription", null)
						.invoke(arg1.getInstance().getClass(), null).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CoreDriverScript.tCase.setTestCaseDesc(methodDesc);
			String textMsg = "Completed executing following method : "
					+ returnMethodName(arg0.getTestMethod());

			Reporter.log(textMsg, true);
		}

	}

	// This will return method names to the calling function

	private String returnMethodName(ITestNGMethod method) {

		return method.getRealClass().getSimpleName() + "."
				+ method.getMethodName();

	}

}