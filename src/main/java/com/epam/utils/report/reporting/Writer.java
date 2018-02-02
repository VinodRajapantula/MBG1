/*
 * Copyright 2012 Alliance Global Services, Inc. All rights reserved.
 * 
 * Licensed under the General Public License, Version 3.0 (the "License") you
 * may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.gnu.org/licenses/gpl-3.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Class: Writer
 * 
 * Purpose: This utility class generate XML document for reporting from
 * TestSuite result.
 */

package com.epam.utils.report.reporting;

import java.io.File;


import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.omg.CORBA.portable.ApplicationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.epam.driver.CoreDriverScript;
import com.epam.utils.Config;
import com.epam.utils.Constants;
import com.epam.utils.report.testsuite.TestCase;
import com.epam.utils.report.testsuite.TestScenario;
import com.epam.utils.report.testsuite.TestStep;
import com.epam.utils.report.testsuite.TestSuite;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
/**
 * Utility class to generate XML document from TestSuite result.
 * 
 */
@SuppressWarnings("restriction")
public class Writer extends CoreDriverScript{

	/** The Constant LOGGER. */

	private List<TestSuite> suiteResult;
	private String startTime;
	private String endTime;
	private Document document;
	private DocumentBuilder builder;
	private Document resultListDocument;
	private static String ResultListFile = "";
	private static String localResultListFile = "";
	private String SahredResultFileName = "";
	private String LocalResultFileName = "";


	/**
	 * Creates an instance of XMLWriter.
	 * 
	 * @param suiteResult
	 *            ArrayList<TestSuite> object.
	 * @param startTime
	 *            startTime
	 * @param endTime
	 *            endTime
	 * @throws FrameworkExceptions
	 */
	public Writer(List<TestSuite> suiteResult, String startTime, String endTime)
	{
		this.suiteResult = suiteResult;
		this.startTime = startTime;
		this.endTime = endTime;
		/* System.out.println("Start TIME: " + startTime.split(" ")[1]);
            System.out.println("End TIME: " + endTime.split("")[1]);
            System.out.println( "DIFFERENCE: " + formatTime(endTime - startTime) );*/
	}

	public Writer() {
		// TODO Auto-generated constructor stub
	}

	private String getExecutionTimeDifference() {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATEFORMAT);
		String result = "";
		try {
			java.util.Date startDate = format.parse(startTime);
			java.util.Date endDate = format.parse(endTime);

			result = formatTime(endDate.getTime() - startDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * writes the reporting data to a file
	 * @throws Exception 
	 * 
	 * @throws FrameworkExceptions
	 */
	public void writeToFile() throws Exception {
		try {
			/*resultListFile = "./testReports/xml"
					+ "\\" + Constants.TESTREPORTRESULTFILE;
			 */
			//	ResultListFile = Config.getConfig().getConfigProperty(Constants.SHAREDTESTRESULTPATH)	+ "\\" + Constants.TESTREPORTRESULTFILE;


			//			LOGGER.trace("Creating new DocumentBuilderFactory object");
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			builder = factory.newDocumentBuilder();
			//			LOGGER.trace("Creating new document object");
			document = builder.newDocument();
			//			LOGGER.trace("Creating DOM trees for TestSuite and TestCase reporting");
			createDOMTree();
		} catch (ParserConfigurationException pe) {
			//			LOGGER.error("Exception:: ", pe);
		} catch (UnknownHostException ue) {
			//			LOGGER.error("Exception:: ", ue);
		}
	}



	/**
	 * Refreshes the test result file TestResultList.xml for new test result xml
	 * @throws Exception 
	 * 
	 * @throws ApplicationException
	 * 
	 */
	public void refreshTestResultXMLFile(String resultFolderPath) throws Exception  {

		try {
			File resultFolder = null;
			//			resultFolder = new File("./testReports/xml");
			resultFolder = new File(resultFolderPath);
			ResultListFile = resultFolderPath	+ "\\" + Constants.TESTREPORTRESULTFILE;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Shanker - Temp
			builder = factory.newDocumentBuilder();//Shanker - Temp
			resultListDocument = builder.newDocument();
			Element rootNode = resultListDocument.createElement("ResultList");

			File[] xmlList = resultFolder.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					String f = new File(name).getName();
					return f.contains(".xml")
							&& !f.contains(Constants.TESTREPORTRESULTFILE);
				}
			});

			xmlList = sortFilesByDate(xmlList);

			for (int i = 0; i < xmlList.length; i++) {
				// condition xmlList[i].length()<3000 added below to avoid error while parsing an incomplete report xml file - Shanker-06-10-2015
				if(!(xmlList[i].length()<3000||xmlList[i].getName().startsWith("SpellErrors")||xmlList[i].getName().startsWith("LinkErrors")||xmlList[i].getName().equalsIgnoreCase("ErrorResults.xml"))){
					Element resultElement = resultListDocument
							.createElement("Result");
					resultElement.setAttribute("file", xmlList[i].getName());
					rootNode.appendChild(resultElement);
					Document doc = builder.parse(resultFolder + "\\"
							+ xmlList[i].getName());
					NodeList suiteList = doc.getElementsByTagName("TestSuite");
					createTestSuiteElement(suiteList, resultElement);
				}
			}
			resultListDocument.appendChild(rootNode);
			OutputFormat format = new OutputFormat(resultListDocument);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(
					ResultListFile), format);
			serializer.serialize(resultListDocument);
		} catch (IOException io) {

		} catch (SAXException e) {

		}
	}

	/**
	 * Workhorse method for creating XML document.
	 * 
	 * @throws UnknownHostException
	 * @throws DOMException
	 * @throws FrameworkExceptions
	 */
	private void createDOMTree() throws DOMException, UnknownHostException
	{

		Element rootElement = document.createElement("TestResult");
		document.appendChild(rootElement);

		Iterator<TestSuite> suiteIterator = suiteResult.iterator();
		while (suiteIterator.hasNext()) {
			TestSuite suite = (TestSuite) suiteIterator.next();
			String browser = suite.getBrowserName();
			String url = suite.geturl();

			Element suiteElement = createTestSuiteElement(suite);
			suiteElement.setAttribute("browser", browser);
			suiteElement.setAttribute("BaseUrl", url);
			rootElement.appendChild(suiteElement);
		}
		rootElement.appendChild(createEnvInfoDOM());
	}

	/**
	 * @return envInfoElement
	 * @throws UnknownHostException
	 * @throws FrameworkExceptions
	 */
	private Element createEnvInfoDOM() throws UnknownHostException
	{

		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();
		String os = System.getProperty("os.name");

		// String testType = "Regression";

		Element envInfoElement = document.createElement("Environment");
		envInfoElement.setAttribute("os", os);
		// envInfoElement.setAttribute("testType", testType);
		envInfoElement.setAttribute("hostname", hostname);
		envInfoElement.setAttribute("startTime", startTime); 

		if (endTime != null && !endTime.equals("")) {
			envInfoElement.setAttribute("endTime", endTime);
			// String totalExecutionDuration = getTotalExecutionTime();  //souban
			String totalExecutionDuration = getExecutionTimeDifference();
			envInfoElement.setAttribute("totalExecutionDuration",
					totalExecutionDuration);
		} else {
			envInfoElement.setAttribute("endTime", "Execution Not Completed");
			envInfoElement.setAttribute("totalExecutionDuration",
					"Execution Not Completed");
		}

		// adding log file name and path attributes
		/*	String logFile = Log4JPlugin.getInstance().getLogFile(); */
		//		envInfoElement.setAttribute("logFile", CoreDriverScript.dest);
		//envInfoElement.setAttribute("logFile", "logFile destination");
		try {
			String filepath = lastFileModified(Constants.REPORTFILEPATH).getCanonicalPath().replace("\\Report", ".\\Report");
			envInfoElement.setAttribute("logFile", filepath.substring(filepath.lastIndexOf(".\\")) );
		} catch (IOException e) {
			e.printStackTrace();
		}


		//	LOGGER.trace("Created environment information DOM tree element successfully");*/

		return envInfoElement;
	}

	/**
	 * Helper method which creates a XML element <TestSuite>
	 * 
	 * @param suite
	 *            TestSuite whose xml representation has to be created.
	 * @return XML element snippet representing a book
	 */
	private Element createTestSuiteElement(TestSuite suite) {

		Element suiteElement = document.createElement("TestSuite");
		suiteElement.setAttribute("Name", suite.getTestSuiteName());
		// Category attribute added.
		suiteElement.setAttribute("Category", suite.getCategory());

		// ExecutionEngine attribute added.
		suiteElement
		.setAttribute("ExecutionEngine", suite.getExecutionEngine());

		List<TestScenario> testcases = suite.getTestScenariosArr();				
		Iterator<TestScenario> testsIterator = testcases.iterator();		
		long totalScenariosExecutionTime = 0;
		Element testElement = null;
		while (testsIterator.hasNext()) {

			TestScenario testScenario = (TestScenario) testsIterator.next();
			testElement = document.createElement("Test");
			testElement.setAttribute("id", testScenario.getBusinessScenarioId());
			/*testElement.setAttribute("executionTime",
					formatTime(testScenario.getExecutionTime()));
			 */
			long totalExecutionTime = 0;
			testElement.appendChild(createTestCaseDetailsElement(testScenario));
			testElement.appendChild(createTestCaseElement(testScenario));

			List<TestCase> testCaseList = testScenario.getTestCaseDetails();
			if (testCaseList != null && testCaseList.size() > 0) {
				Iterator<TestCase> testCaseIterator = testCaseList.iterator();
				while (testCaseIterator.hasNext()) {
					TestCase testCase = testCaseIterator.next();
					totalExecutionTime = totalExecutionTime
							+ testCase.getTestCaseExecutionTime();
				}
			}
			totalScenariosExecutionTime = totalScenariosExecutionTime + totalExecutionTime;		
			testElement.setAttribute("executionTime",
					formatTimeMilliSec(totalScenariosExecutionTime));



			Element descElement = document.createElement("Description");
			Text description = document.createTextNode(testScenario
					.getBusinessScenarioDesc());
			descElement.appendChild(description);
			testElement.appendChild(descElement);

			Element resultElement = document.createElement("Result");
			Text result = document.createTextNode(testScenario
					.getExecutionResult());
			resultElement.appendChild(result);
			testElement.appendChild(resultElement);

			// Test Case Id / Requirement Id Column added
			Element testCaseRequirementId = document
					.createElement("TestCaseRequirementId");
			String testCaseReqmtId = testScenario
					.getTestScenarioRequirementId();
			if (Constants.EMPTYVALUE.equals(testCaseReqmtId)) {
				testCaseReqmtId = "N/A";
			}
			Text testCaseRequirementId1 = document
					.createTextNode(testCaseReqmtId);
			testCaseRequirementId.appendChild(testCaseRequirementId1);
			testElement.appendChild(testCaseRequirementId);

			suiteElement.appendChild(testElement);
		}
		//souban - start
		//		suiteElement.setAttribute("executionTime",
		//				formatTime(totalScenariosExecutionTime));
		//souban - end

		suiteElement.setAttribute("executionTime",
				getExecutionTimeDifference());

		suite.setExecutionTime(totalScenariosExecutionTime);
		return suiteElement;
	}

	/**
	 * @param testScenario
	 *           testScenario
	 * @return stepsElement
	 */
	private Element createTestCaseElement(TestScenario testScenario) {

		Element stepsElement = document.createElement("Steps");
		Element stepElement = null;
		List<TestCase> testCaseList = testScenario.getTestCaseDetails();
		if (testCaseList != null && testCaseList.size() > 0) {
			Iterator<TestCase> testCaseIterator = testCaseList.iterator();
			while (testCaseIterator.hasNext()) {
				TestCase testCase = testCaseIterator.next();
				List<TestStep> testStepList = testCase.getTestStepDetails();
				if (testStepList != null && testStepList.size() > 0) {
					Iterator<TestStep> testStepIterator = testStepList
							.iterator();
					while (testStepIterator.hasNext()) {
						TestStep testStep = testStepIterator.next();

						stepElement = document.createElement("Step");
						stepElement.setAttribute("id", testStep.getTestCase()
								.getTestCaseId());
						stepElement.setAttribute("object",
								testStep.getElementName());
						stepElement
						.setAttribute("action", testStep.getAction());
						stepElement
						.setAttribute("result", testStep.getResult());
						stepElement.setAttribute("errorMessage",
								testStep.getErrorMessage());
						stepElement.setAttribute("description",
								testStep.getActionDescription());
						stepElement.setAttribute("stepId",
								testStep.getStepId());

						stepElement
						.setAttribute("sortId", testStep.getSortId());
						stepElement.setAttribute("reusable",
								Boolean.valueOf(testCase.isReusable())
								.toString());
						stepElement.setAttribute("executionTime",
								formatTimeMilliSec(testStep
										.getTestStepExecutionTime()));
						if (testStep.getAction() != null
								&& testStep.getAction().toUpperCase()
								.startsWith("WS_")) {
							if (testStep.getServiceRequestName().length() > 0) {
								stepElement.setAttribute("image",
										testStep.getServiceRequestName());
							} else {
								stepElement.setAttribute("image",
										testStep.getServiceResponseName());
							}
						} else {
							if(actions.blnCaptureScreenShotOnFail){
								stepElement.setAttribute("image",testStep.getImageName());}
							else if(actions.blnCaptureScreenShotOnPass)
							{
								stepElement.setAttribute("image",testStep.getImageName());}
							else if(actions.blnCaptureScreenShotOnFail&&actions.blnCaptureScreenShotOnPass){
								stepElement.setAttribute("image",testStep.getImageName());}
							else
							{
								stepElement.setAttribute("image","");
							}
						}
						stepsElement.appendChild(stepElement);
					}
				}
			}
		}

		return stepsElement;
	}

	/**
	 * Create DOM Tree element for Test Case Details
	 * 
	 * @param testScenario
	 *           testScenario
	 * @return testCaseDetails
	 */
	private Element createTestCaseDetailsElement(TestScenario testScenario) {

		Element testCaseDetails = document.createElement("TestCaseDetails");
		Element testCaseElement = null;
		List<TestCase> testCaseList = testScenario.getTestCaseDetails();
		if (testCaseList != null && testCaseList.size() > 0) {
			Iterator<TestCase> testCaseIterator = testCaseList.iterator();
			while (testCaseIterator.hasNext()) {
				TestCase testCase = testCaseIterator.next();
				testCaseElement = document.createElement("TestCase");
				testCaseElement.setAttribute("id", testCase.getTestCaseId());
				testCaseElement.setAttribute("description",
						testCase.getTestCaseDesc());
				testCaseElement.setAttribute("sortId", testCase.getSortId());
				testCaseElement.setAttribute("reusable",
						Boolean.valueOf(testCase.isReusable()).toString());


				List<TestStep> testStepList = testCase.getTestStepDetails();
				long totalExecutionTime = 0;
				if (testStepList != null && testStepList.size() > 0) {
					Iterator<TestStep> testStepIterator = testStepList
							.iterator();
					while (testStepIterator.hasNext()) {
						TestStep testStep = testStepIterator.next();
						totalExecutionTime = totalExecutionTime
								+ testStep.getTestStepExecutionTime();
					}

				}
				testCaseElement.setAttribute("executionTime", formatTimeMilliSec(totalExecutionTime));
				testCase.setTestCaseExecutionTime(totalExecutionTime);
				testCaseDetails.appendChild(testCaseElement);
			}
		}

		return testCaseDetails;
	}

	/**
	 * This method uses Xerces specific classes and prints the XML document to
	 * file.
	 * 
	 * @param startTime
	 *            startTime
	 * @param testSuiteName
	 *            testSuiteName
	 * @throws Exception 
	 * @throws FrameworkExceptions
	 * 
	 */
	public void saveTestResult(java.util.Date startTime, String testSuiteName) throws Exception
	{

		try {
			XMLSerializer serializer = null;
			OutputFormat format = new OutputFormat(document);
			format.setIndenting(true);
			// String resultFileName = "";


			/*			resultFileName = createReportXmlFileName(
					"./testReports/xml", startTime,
							testSuiteName, "Reports")
							+ ".xml";
			System.out.println("resultFileName1 = "+ resultFileName);*/

			if(Config.getConfig().getConfigProperty(Constants.SHAREDTESTRESULTPATH).trim().length()>0){
				SahredResultFileName = createReportXmlFileName(
						Config.getConfig().getConfigProperty(Constants.SHAREDTESTRESULTPATH), startTime,
						testSuiteName, "Reports")
						+ ".xml";
				if(SahredResultFileName.contains("\\\\")){
					SahredResultFileName=SahredResultFileName.replace("/", "\\");
				}


				serializer = new XMLSerializer(new FileOutputStream(new File(
						SahredResultFileName)), format);
				serializer.serialize(document);
			}

			LocalResultFileName = createReportXmlFileName(
					Config.getConfig().getConfigProperty(Constants.LOCALTESTRESULTPATH), startTime,
					testSuiteName, "Reports")
					+ ".xml";
			if(LocalResultFileName.contains("\\\\")){
				LocalResultFileName=LocalResultFileName.replace("/", "\\");
			}

			serializer = new XMLSerializer(new FileOutputStream(new File(
					LocalResultFileName)), format);
			serializer.serialize(document);

			refreshTestResultXMLFile(Config.getConfig().getConfigProperty(Constants.LOCALTESTRESULTPATH));			

		} catch (IOException io) {
		}
	}

	/**
	 * @param files
	 *            array to be sorted by date
	 * @return sorted files array by date
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static File[] sortFilesByDate(File files[]) {
		Arrays.sort(files, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return new Long(((File) o1).lastModified()).compareTo(new Long(
						((File) o2).lastModified()));
			}
		});
		return files;
	}

	/**
	 * @param milliseconds
	 *          milliseconds
	 * @return String
	 */
	public static String formatTime(long milliseconds) {
		StringBuffer time = new StringBuffer();
		System.out.println("Format Time: " + time);
		int hours = (int) TimeUnit.MILLISECONDS.toSeconds(milliseconds) / 3600;
		int mins = (int) ((TimeUnit.MILLISECONDS.toSeconds(milliseconds) / 60) - (hours * 60));
		int secs = (int) (TimeUnit.MILLISECONDS.toSeconds(milliseconds) - (hours * 3600 + mins * 60));
		/*
		 * int msecs = (int) (TimeUnit.MILLISECONDS.toMillis(milliseconds) -
		 * ((hours 3600 + mins * 60 + secs) * 1000));
		 */

		// time.append(hours + ":" + mins + ":" + secs + "." + msecs);
		String strHours = String.valueOf(hours);
		strHours = strHours.length() == 1 ? "0" + strHours : strHours;

		String strMins = String.valueOf(mins);
		strMins = strMins.length() == 1 ? "0" + strMins : strMins;

		String strSecs = String.valueOf(secs);
		strSecs = strSecs.length() == 1 ? "0" + strSecs : strSecs;

		time.append(strHours + ":" + strMins + ":" + strSecs); // milli sec
		// removed
		return time.toString();
	}

	/**
	 * To return time in MM:SS:sss format
	 * 
	 * @param milliseconds
	 *           milliseconds
	 * @return String
	 */
	private String formatTimeMilliSec(long milliseconds) {
		StringBuffer time = new StringBuffer();		
		int hours = (int) TimeUnit.MILLISECONDS.toSeconds(milliseconds) / 3600;
		int mins = (int) ((TimeUnit.MILLISECONDS.toSeconds(milliseconds) / 60) - (hours * 60));
		int secs = (int) (TimeUnit.MILLISECONDS.toSeconds(milliseconds) - (hours * 3600 + mins * 60));
		int msecs = (int) (TimeUnit.MILLISECONDS.toMillis(milliseconds) - ((hours
				* 3600 + mins * 60 + secs) * 1000));

		// Minutes
		String strMins = String.valueOf(mins);
		strMins = strMins.length() == 1 ? "0" + strMins : strMins;
		// Seconds
		String strSec = String.valueOf(secs);
		strSec = strSec.length() == 1 ? "0" + strSec : strSec;
		// Milliseconds
		String mSec = String.valueOf(msecs);
		if (mSec.length() == 1) {
			mSec = "00" + mSec;
		} else if (mSec.length() == 2) {
			mSec = "0" + mSec;
		}
		time.append(strMins + ":" + strSec + ":" + mSec); // MM:SS:sss
		return time.toString();
	}

	/**
	 * Calculates and Returns the Total Execution Duration.
	 * 
	 * @return totalExecutionDuration
	 * @throws FrameworkExceptions
	 */
	@SuppressWarnings("unused")
	private String getTotalExecutionTime()
	{

		String totalExecutionDuration = "";

		/*try {

			Iterator<TestSuite> suiteIterator = suiteResult.iterator();
			long totalSuiteExecutionTime = 0;
			while (suiteIterator.hasNext()) {
				TestSuite suite = (TestSuite) suiteIterator.next();
				totalSuiteExecutionTime = totalSuiteExecutionTime
						+ suite.getExecutionTime();
				totalExecutionDuration = formatTime(totalSuiteExecutionTime);
            }


		} catch (Exception e) {

		}*/
		totalExecutionDuration = getExecutionTimeDifference();
		return totalExecutionDuration;
	}

	/**
	 * This method will create test suite element
	 * 
	 * @param suiteList
	 *          suiteList
	 * @param resultElement
	 *          resultElement
	 */
	private void createTestSuiteElement(NodeList suiteList,
			Element resultElement) {
		for (int i = 0; i < suiteList.getLength(); i++) {
			Node nNode = suiteList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Element suiteElement = resultListDocument
						.createElement(eElement.getNodeName());
				suiteElement
				.setAttribute("Name", eElement.getAttribute("Name"));
				suiteElement.setAttribute("browser",
						eElement.getAttribute("browser"));
				resultElement.appendChild(suiteElement);
				NodeList testList = eElement.getElementsByTagName("Test");
				setTestSuiteData(testList, suiteElement);
			}
		}
	}

	/**
	 * This method will set test suite data
	 * 
	 * @param testList
	 *          testList
	 * @param suiteElement
	 *          suiteElement
	 */

	private void setTestSuiteData(NodeList testList, Element suiteElement) {
		int testPass = 0;
		int testFail = 0;
		int testCasePass = 0;
		int testCaseFail = 0;
		for (int j = 0; j < testList.getLength(); j++) {
			Node testNode = testList.item(j);
			if (testNode.getNodeType() == Node.ELEMENT_NODE) {
				Element testElement = (Element) testNode;
				String result = testElement.getElementsByTagName("Result")
						.item(0).getTextContent();
				if (result.equalsIgnoreCase("Pass")) {
					testPass = testPass + 1;
				} else {
					testFail = testFail + 1;
				}
				NodeList stepsList = testElement.getElementsByTagName("Steps");
				for (int k = 0; k < stepsList.getLength(); k++) {
					Node stepNode = stepsList.item(k);
					if (stepNode.getNodeType() == Node.ELEMENT_NODE) {
						Element stepElement = (Element) stepNode;
						NodeList stepList = stepElement
								.getElementsByTagName("Step");
						for (int m = 0; m < stepList.getLength(); m++) {
							Node sNode = stepList.item(m);
							if (sNode.getNodeType() == Node.ELEMENT_NODE) {
								Element sElement = (Element) sNode;
								if (!sElement.getAttribute("result")
										.equalsIgnoreCase("FAIL")) {
									testCasePass = testCasePass + 1;
								} else {
									testCaseFail = testCaseFail + 1;
								}
							}
						}
					}
				}
			}
		}
		setElementData(testPass, testFail, testCasePass, testCaseFail,
				suiteElement);
	}

	/**
	 * This method will set the Element data
	 * 
	 * @param testPass
	 *            testPass
	 * @param testFail
	 *            testFail
	 * @param testCasePass
	 *            testCasePass
	 * @param testCaseFail
	 *            testCaseFail
	 * @param suiteElement
	 *            suiteElement
	 */
	private void setElementData(int testPass, int testFail, int testCasePass,
			int testCaseFail, Element suiteElement) {
		Element eleTestPass = resultListDocument.createElement("Test_Pass");
		eleTestPass.setTextContent(testPass + "");
		Element eleTestFail = resultListDocument.createElement("Test_Fail");
		eleTestFail.setTextContent(testFail + "");
		Element eleTestCasePass = resultListDocument
				.createElement("TestCase_Pass");
		eleTestCasePass.setTextContent(testCasePass + "");
		Element eleTestCaseFail = resultListDocument
				.createElement("TestCase_Fail");
		eleTestCaseFail.setTextContent(testCaseFail + "");
		suiteElement.appendChild(eleTestPass);
		suiteElement.appendChild(eleTestFail);
		suiteElement.appendChild(eleTestCasePass);
		suiteElement.appendChild(eleTestCaseFail);
	}

	/**
	 * Gets the resultFileName.
	 * 
	 * @return the resultFileName
	 */
	public String getResultFileName() {
		return SahredResultFileName;
	}

	/**
	 * set the resultFileName
	 * 
	 * @param resultFileName
	 *            resultFileName
	 */
	public void setResultFileName(String resultFileName) {
		this.SahredResultFileName = resultFileName;
	}


	public String createReportXmlFileName(String path,
			java.util.Date testStartTime, String hostName, String sourceName)
	{
		String filePath = path;
		try {
			File f = new File(filePath);
			if (!f.exists() || !f.isDirectory() || !f.canWrite()) {
			} else {
				// Create a place holder to store the screen shots or service
				// requests.
				DateFormat formatter = new SimpleDateFormat(
						Constants.DATEFORMATFOLDERNAME);
				String timestamp = formatter.format(testStartTime);
				filePath = filePath + "/" + hostName + "_" + timestamp;
			}
		} catch (Exception e) {
		}
		return filePath;
	}

	public static File lastFileModified(String dir) {
		File f1 = new File(dir);
		File[] files = f1.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});

		long lastMod = Long.MIN_VALUE;
		File choice = null;
		if(files.length == 1) {
			choice = files[0];
		}
		for (File f: files) {
			if(!f.getName().equals("Log")) {
				if (f.lastModified() > lastMod) {
					choice = f;
					lastMod = f.lastModified();
				}
			}
		}
		return choice;
	}



}