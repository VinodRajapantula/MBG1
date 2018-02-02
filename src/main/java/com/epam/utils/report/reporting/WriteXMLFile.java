package com.epam.utils.report.reporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.epam.utils.FrameworkExceptions;

public class WriteXMLFile {
	
	/** The Constant LOGGER. */
//	private static final Logger LOGGER = Logger.getLogger(WriteXMLFile.class);
	private SAXBuilder builder = null;
	private File xmlFile = null;
	private Document doc = null;

	public Document createRootNode() {
		Element company = new Element("ResultList");
		doc = new Document();
		doc.setRootElement(company);
		return doc;
	}

	public Document getRootNode(String fileName) throws JDOMException,
			IOException {
		builder = new SAXBuilder();
		doc = (Document) builder.build(fileName);
		return doc;
	}

	public boolean verifyFile(String file) {
		boolean flag = false;
		File f = new File(file);
		if (f.exists()) {
			flag = true;
		}
		return flag;
	}

	public void writeXMLFile(String fileName, String readFileName)
			throws FrameworkExceptions {
		XMLOutputter xmlOutput = new XMLOutputter();
		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(createXML(fileName, readFileName), new FileWriter(
					fileName));
		} catch (Exception e) {
//			LOGGER.error(e);
			throw new FrameworkExceptions(e);
		}
	}

	public List<Element> testSuiteCount(String file) {
		List<Element> testSuiteList = null;
		builder = new SAXBuilder();
		xmlFile = new File(file);
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			testSuiteList = rootNode.getChildren("TestSuite");
		} catch (Exception e) {
//			LOGGER.error(e);
		}
		return testSuiteList;
	}

	public Document createXML(String acFile, String readFile)
			throws FrameworkExceptions {
		Element rootNode = null;
		Element testSuite = null;
		int testPass = 0;
		int testFail = 0;
		int testCasePass = 0;
		int testCaseFail = 0;
		Document document = null;
		try {
			if (verifyFile(acFile)) {
				document = getRootNode(acFile);
			} else {
				document = createRootNode();
			}
			rootNode = document.getRootElement();
			Element result = new Element("Result");
			result.setAttribute(new Attribute("file", readFile.substring(
					readFile.lastIndexOf('/') + 1, readFile.length())));
			rootNode.addContent(result);
			List<Element> listTSCount = testSuiteCount(readFile);
			Iterator<Element> i = listTSCount.iterator();
			while (i.hasNext()) {
				testSuite = i.next();
				Element mainFileTS = new Element(testSuite.getName());
				mainFileTS.setAttribute(new Attribute("name", testSuite
						.getAttributeValue("Name")));
				mainFileTS.setAttribute(new Attribute("Browser", testSuite
						.getAttributeValue("browser")));
				result.addContent(mainFileTS);

				List<Element> listTest = testSuite.getChildren("Test");
				Iterator<Element> j = listTest.iterator();
				while (j.hasNext()) {
					Element testscenario = j.next();
					if (testscenario.getChildText("Result").equalsIgnoreCase(
							"Pass")) {
						testPass = testPass + 1;
					} else {
						testFail = testFail + 1;
					}
					List<Element> stepsList = testscenario.getChildren("Steps");
					Iterator<Element> stepsIterator = stepsList.iterator();
					while (stepsIterator.hasNext()) {
						List<Element> stepList = stepsIterator.next()
								.getChildren("Step");
						Iterator<Element> stepIterator = stepList.iterator();
						while (stepIterator.hasNext()) {
							Element stepNode = stepIterator.next();
							if (stepNode.getAttribute("result").getValue()
									.equalsIgnoreCase("PASS")) {
								testCasePass = testCasePass + 1;
							} else {
								testCaseFail = testCaseFail + 1;
							}
						}
					}
				}
				setElementData(testPass, testFail, testCasePass, testCaseFail,
						mainFileTS);
			}
		} catch (Exception e) {
//			LOGGER.error(e);
			throw new FrameworkExceptions(e);
		}

		return document;

	}

	/**
	 * This method will set the Element data
	 * 
	 * @param testPass
	 * @param testFail
	 * @param testCasePass
	 * @param testCaseFail
	 * @param mainFileTS
	 */
	private void setElementData(int testPass, int testFail,
			int testCasePass, int testCaseFail, Element mainFileTS ) {
		Element eleTestPass = new Element("Test_Pass");
		eleTestPass.setText(testPass + "");
		Element eleTestFail = new Element("Test_Fail");
		eleTestFail.setText(testFail + "");
		Element eleTestCasePass = new Element("TestCase_Pass");
		eleTestCasePass.setText(testCasePass + "");
		Element eleTestCaseFail = new Element("TestCase_Fail");
		eleTestCaseFail.setText(testCaseFail + "");
		mainFileTS.addContent(eleTestPass);
		mainFileTS.addContent(eleTestFail);
		mainFileTS.addContent(eleTestCasePass);
		mainFileTS.addContent(eleTestCaseFail);
	}
}