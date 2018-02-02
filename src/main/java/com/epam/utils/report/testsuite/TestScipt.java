package com.epam.utils.report.testsuite;

//import com.horizon.utilities.ExcelReaderWriter;

public class TestScipt {
	//int tcid,String currentTest,ExcelReaderWriter controller,int tsid,TestCase tCase,String Index
	
	private int testCaseId;
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getCurrentTest() {
		return currentTest;
	}
	public void setCurrentTest(String currentTest) {
		this.currentTest = currentTest;
	}
	public int getTestStepId() {
		return testStepId;
	}
	public void setTestStepId(int testStepId) {
		this.testStepId = testStepId;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	private String currentTest;
	private int testStepId;
	String index;
}
