package com.epam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.epam.driver.CoreDriverScript;

public class TestDataReader {

	FileInputStream file;
	private static Logger applicationLogs = Logger.getLogger(TestDataReader.class);

	public String path;
	public static FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	public HSSFSheet sheet = null;
	private static HSSFWorkbook workbook = null;
	private HSSFRow row = null;
	private HSSFCell cell = null;
	//static LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
	static TreeMap<String, String> dataMap = new TreeMap<String, String>();

	public TestDataReader() {

		try {

			fis = new FileInputStream(Config.getConfig().getConfigProperty(Constants.TESTDATAPATH));
			workbook = new HSSFWorkbook(fis);
			fis.close();
		} catch (Exception e) {
			applicationLogs.info("Please check the excel file and excel sheet" + e.getMessage());
		}
	}

	/*Function Name : getEntityData
	 * Returns		: Scenarios specified data in the form of ArrayList of ArrayList Strings
	 * Arguments	: SheetName(Sheet Data to read),ScenarioID(The scenario against which data need to be fetched)
	 */

	public static TreeMap<String, String> getEntityData(String senarioName,String sheetName, int row)
			throws Exception {
		try
		{
			int firstRowNumber, lastRowNumber, firstCellNumber, noofcolumns = 0, tempcols;
			HSSFRow headerRow = null;
			HSSFRow tempRow = null;
			HSSFRow firstRow = null;
			ArrayList<String> arrayListData = null;

			ArrayList<String> arrayListScenario = null;
			ArrayList<ArrayList<String>> arrayListScenarios = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> arrayListsData = new ArrayList<ArrayList<String>>();

			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheet(sheetName);
			firstRowNumber = sheet.getFirstRowNum();
			lastRowNumber = sheet.getLastRowNum();

			firstRow = sheet.getRow(lastRowNumber);

			firstCellNumber = firstRow.getFirstCellNum();
			for (int irow = (firstRowNumber+1) ;irow <= lastRowNumber; irow++)
			{
				tempRow = sheet.getRow(irow);
				tempcols = tempRow.getLastCellNum();
				if (noofcolumns < tempcols)
				{
					noofcolumns = tempcols;
				}
			}


//            for(int row = (firstRowNumber+1) ;row <= lastRowNumber; row++)
//            {
			tempRow = sheet.getRow(row);
			arrayListScenario = new ArrayList<String>();
			for(int col = (firstCellNumber);col<noofcolumns;col++)
			{
				if(tempRow.getCell(col) == null)
				{
					arrayListScenario.add("");
				}
				else
				{
					arrayListScenario.add(tempRow.getCell(col).toString());
				}
			}
			arrayListScenarios.add(arrayListScenario);
//            }
			for(ArrayList<String> individualScenarios : arrayListScenarios )
			{
				if(individualScenarios.get(0).equalsIgnoreCase(senarioName))
				{
					arrayListData = new ArrayList<String>();
					for(int i = 1;i<individualScenarios.size();i++)
					{
						headerRow = sheet.getRow(0);
						HSSFCell ColumHeader = headerRow.getCell(i);
						String strValue = individualScenarios.get(i);

						//Get date
						if(strValue.toUpperCase().startsWith("CDATE_"))
						{
							strValue = getDate(strValue);
						}

						//Get time
						if(strValue.toUpperCase().startsWith("CTIME_"))
						{
							strValue = getTime(strValue);
						}

						//Get unique data with timestamp
						if(strValue.toUpperCase().startsWith("UNIQUE"))
						{
							strValue = getUniqueValue(strValue);
						}

//						if((strValue != null) && (!strValue.equalsIgnoreCase("Null")) && (strValue.trim().length()!=0) &&
//								(ColumHeader != null) && (! ColumHeader.toString().equalsIgnoreCase("Null")) &&(ColumHeader.toString().trim().length()!=0))
//						{
						//Adding to map object
						dataMap.put(headerRow.getCell(i).toString(), strValue);
						strValue = "";
//						}

					}
					arrayListsData.add(arrayListData);
					break;
				}
			}
		}
		catch(NullPointerException ne)
		{
			System.out.println("Exception while reading test data from sheet "+sheetName);
			throw ne;
		}
		catch(Exception e)
		{
			throw e;
		}
		return dataMap;
	}
	/**
	 * @param senarioName
	 * @param tableName
	 * @param coloName
	 * @return
	 * @throws Exception
	 */
	public static TreeMap<String, String> getEntityData(String senarioName,String tableName,String coloName)
			throws Exception {
		String coloums="";
		DBAccess dbAcess=new DBAccess();

		String []noofColms=coloName.split(",");
		if(noofColms.length>0){
			for (String myValue : noofColms) {
				System.out.println(myValue);
				coloums =coloums+myValue+" ,";
			}
			coloums = coloums.substring(0, coloums.length() - 1);
		}
		Connection connection=(Connection) dbAcess.getConnection(CoreDriverScript.config.getConfigProperty(Constants.DATABASENAME));
		dbAcess.executeQuery(connection, "Select "+coloums +"from "+tableName+" where Scenario_Name="+senarioName);
		ResultSet result=dbAcess.getResultSet();
		while(result.next()){
			for ( int i=1; i<noofColms.length+1; i++ )
			{
				dataMap.put(noofColms[i], result.getString(noofColms[i]));
			}
		}
		connection.close();
		return dataMap;

	}

	/*	  1. Current Date:CDATE_TODAY -(Returns current date)
          2.Future Date:CDATE_TODAY#4(Adds 4 days to the current date and
              returns the value in date format specified).
          3.Past Date: CDATE_TODAY_4(Subtract 4 days to the current date and
              returns the value in date format specified). */
	public static String getDate(String strValue) throws ArrayIndexOutOfBoundsException,Exception
	{
		int intDays;
		try
		{
			Calendar objCal = Calendar.getInstance();

			SimpleDateFormat objSdf = new SimpleDateFormat("MM/dd/yyyy");

			objCal.setTime(objSdf.parse(getSystemDate()));

			if (strValue.trim().toUpperCase().equalsIgnoreCase("CDATE_TODAY"))
			{
				strValue = objSdf.format(objCal.getTime());
			}else if (strValue.trim().toUpperCase().contains("CDATE_TODAY_"))
			{
				String [] arrValues = strValue.toUpperCase().split("DAY_");
				intDays = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.DATE, -intDays);
				strValue = objSdf.format(objCal.getTime());
			}else if (strValue.trim().toUpperCase().contains("CDATE_TODAY#"))
			{
				String [] arrValues = strValue.toUpperCase().split("DAY#");
				intDays = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.DATE, intDays);
				strValue = objSdf.format(objCal.getTime());
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new RuntimeException(e.toString());
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}
		return strValue;
	}

	/* 1.CTIME_TODAY : Current time
      2.CTIME_TODAY_HOURS_3:(Decrement of 3 hours to current time).
      3.CTIME_TODAY_HOURS#3(Increment of 3 hours to current time)
      4.CTIME_TODAY_MIN_3(Decrement of 3 minutes to current time)
      5.CTIME_TODAY_MIN#3(Increment of 3 minutes to current time)
      6.CTIME_TODAY_SECONDS_3(Decrement of 3 seconds to current time)
      7.CTIME_TODAY_SECONDS#3(Increment of 3 seconds to current time)  */
	public static String getTime(String strValue) throws ArrayIndexOutOfBoundsException,Exception
	{
		int intHours;
		int intMinutes;
		int intSeconds;
		try
		{
			Calendar objCal = Calendar.getInstance();

			SimpleDateFormat objSdf = new SimpleDateFormat("hh:mm a");

			if (strValue.trim().toUpperCase().equalsIgnoreCase("CTIME_TODAY"))
			{
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_HOURS_"))
			{
				String [] arrValues = strValue.toUpperCase().split("HOURS_");
				intHours = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.HOUR, -intHours);
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_HOURS#"))
			{
				String [] arrValues = strValue.toUpperCase().split("HOURS#");
				intHours = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.HOUR, intHours);
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_MIN_"))
			{
				String [] arrValues = strValue.toUpperCase().split("MIN_");
				intMinutes = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.MINUTE, -intMinutes);
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_MIN#"))
			{
				String [] arrValues = strValue.toUpperCase().split("MIN#");
				intMinutes = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.MINUTE, intMinutes);
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_SECONDS_"))
			{
				String [] arrValues = strValue.toUpperCase().split("SECONDS_");
				intSeconds = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.SECOND, -intSeconds);
				strValue = objSdf.format(objCal.getTime());
			}
			else if (strValue.trim().toUpperCase().contains("CTIME_TODAY_SECONDS#"))
			{
				String [] arrValues = strValue.toUpperCase().split("SECONDS#");
				intSeconds = Integer.parseInt(arrValues[1]);
				objCal.add(Calendar.SECOND, intSeconds);
				strValue = objSdf.format(objCal.getTime());
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new RuntimeException(e.toString());
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}
		return strValue;
	}

	/* input should start with 'unique' keyword ex : UniqueSamuel */
	public static String getUniqueValue(String strValue) throws InterruptedException
	{
		Thread.sleep(1000);
		Calendar objCalNow = Calendar.getInstance();
		if (strValue.equalsIgnoreCase(""))
		{
			return strValue;
		}
		else
		{
			strValue = strValue + objCalNow.get(Calendar.YEAR)
					+ (objCalNow.get(Calendar.MONTH)+1)
					+ objCalNow.get(Calendar.DAY_OF_MONTH)
					+ objCalNow.get(Calendar.HOUR)
					+ objCalNow.get(Calendar.MINUTE)
					+ objCalNow.get(Calendar.SECOND)
			;
			objCalNow = null;
			strValue = strValue.replace("UNIQUE", "");
			strValue = strValue.replace("unique", "");
			return strValue;
		}
	}


	public static String getSystemDate()
	{
		String strValue = "";
		try
		{
			Calendar objCal = Calendar.getInstance();
			SimpleDateFormat objSdf = new SimpleDateFormat("MM/dd/yyyy");
			strValue = objSdf.format(objCal.getTime());
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}

		return strValue;
	}

	public static String removeLeadingCharacters(String strInput, String strLeadingCharacter, String strDelimitor)
	{
		try
		{
			String strArray[] = null;
			if (strDelimitor.length() == 0)
			{
				strArray = new String[1];
				strArray[0] = strInput;
			}
			else
			{
				strArray = strInput.split(strDelimitor);
			}
			String strTemp = "";
			strInput = "";
			for (int i= 0 ;i< strArray.length; i++)
			{
				strTemp = strArray[i].replaceAll("^"+strLeadingCharacter+"+", "");
//				strTemp = StringUtils.stripStart(strArray[i],strTrailingCharacter);
				if ( i!=strArray.length-1)
				{
					strInput = strInput+strTemp+strDelimitor;
				}
				else
				{
					strInput = strInput+strTemp;
				}
			}

		}
		catch(Exception e)
		{
			throw new RuntimeException(e.toString());
		}

		return strInput;
	}
/*Function Name : getEntityData
	 * Returns		: Scenarios specified data in the form of ArrayList of ArrayList Strings
	 * Arguments	: SheetName(Sheet Data to read),ScenarioID(The scenario against which data need to be fetched)
	 */

	public static LinkedHashMap<String, LinkedHashMap<String, String>> getEntityData(String senarioName,String sheetName)
			throws Exception {
//		TreeMap<String, TreeMap<String, String>> scenarioDataMap = new TreeMap<String, TreeMap<String, String>>();
//		TreeMap<String, String> rowdata = new TreeMap<String, String>();
		LinkedHashMap<String, LinkedHashMap<String, String>> scenarioDataMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> rowdata = new LinkedHashMap<String, String>();
		
		try {
			int firstRowNumber, lastRowNumber, firstCellNumber, noofcolumns = 0, tempcols;
			HSSFRow headerRow = null;
			HSSFRow tempRow = null;
			HSSFRow firstRow = null;
			ArrayList<String> arrayListData = null;

			ArrayList<String> arrayListScenario = null;

			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheet(sheetName);
			firstRowNumber = sheet.getFirstRowNum();
			lastRowNumber = sheet.getLastRowNum();

			firstRow = sheet.getRow(lastRowNumber);

			firstCellNumber = firstRow.getFirstCellNum();
			for (int row = (firstRowNumber + 1); row <= lastRowNumber; row++) {
				tempRow = sheet.getRow(row);
				tempcols = tempRow.getLastCellNum();
				if (noofcolumns < tempcols) {
					noofcolumns = tempcols;
				}
			}


			String iteration;
			int scenarioiterator = 1;
			headerRow = sheet.getRow(0);
			for (int row = (firstRowNumber + 1); row <= lastRowNumber; row++)
			{
				
				tempRow = sheet.getRow(row);
				
				if (tempRow!=null && tempRow.getCell(0).toString().equalsIgnoreCase(senarioName))
				{
					rowdata = new LinkedHashMap<String, String>();
					iteration = String.valueOf(row); //"row" + scenarioiterator;
					arrayListScenario = new ArrayList<String>();
					for (int col = (firstCellNumber); col < noofcolumns; col++)
					{
						if (tempRow.getCell(col) == null)
						{
							arrayListScenario.add("");
						}
						else
						{
							String strValue = tempRow.getCell(col).toString();
							//Get date
							if (strValue.toUpperCase().startsWith("CDATE_")) {
								strValue = getDate(strValue);
							}

							//Get time
							if (strValue.toUpperCase().startsWith("CTIME_")) {
								strValue = getTime(strValue);
							}

							//Get unique data with timestamp
							if (strValue.toUpperCase().startsWith("UNIQUE")) {
								strValue = getUniqueValue(strValue);
							}

//                            arrayListScenario.add(strValue);
							rowdata.put(headerRow.getCell(col).toString(), strValue);

						}
					}

					scenarioiterator++;
					scenarioDataMap.put(iteration, rowdata);
				}
			}
		}
		catch(NullPointerException ne)
		{
			System.out.println("Exception while reading test data from sheet "+sheetName);
			throw ne;
		}
		catch(Exception e)
		{
			throw e;
		}
		return scenarioDataMap;
	}

	public static Map<String,String> gFunc_ReadTestData(String strSheetName,String strSearchData) throws Exception
	{
		Map<String,String> objMap = new LinkedHashMap<String,String>();
		HSSFWorkbook workbook = null;
		HSSFSheet dataSheet = null;
		HSSFRow HeaderRow  = null;
		HSSFRow firstRow = null;
		HSSFRow DataRow = null;
		int  lastRowNumber, firstCellNumber;
		FileInputStream fo =null;//to read excel a
		File file = new File(Config.getConfig().getConfigProperty(Constants.TESTDATAPATH));//to  access excel file
		if(!file.exists())//to check for file existence
		{
			return null;
		}
		else
		{
			fo = new FileInputStream(file);
		}

		if(Config.getConfig().getConfigProperty(Constants.TESTDATAPATH).endsWith("xls"))
		{
			workbook = new HSSFWorkbook(fo);
		}
		dataSheet = workbook.getSheet(strSheetName);
		HeaderRow = dataSheet.getRow(0);
		
		lastRowNumber = dataSheet.getLastRowNum();
	//	firstRow = dataSheet.getRow(lastRowNumber);

	//	firstCellNumber = firstRow.getFirstCellNum();

		try
		{
			for(int i = 1;i<= dataSheet.getLastRowNum();i++) //row number starts from 1 as 0 is headers
			{
				DataRow = dataSheet.getRow(i);
				if(DataRow.getCell(0).toString().equalsIgnoreCase(strSearchData))
				{

					for(int iCounter =0;iCounter<HeaderRow.getLastCellNum();iCounter++)
					{
						if (DataRow.getCell(iCounter) == null)
						{
							objMap.put(HeaderRow.getCell(iCounter).toString(), "");
						}
						else
						{
							if(DataRow.getCell(iCounter).toString().toUpperCase().startsWith("CDATE_"))
							{
								String strValue = getDate(DataRow.getCell(iCounter).toString());
								objMap.put(HeaderRow.getCell(iCounter).toString(), strValue);
							}else{
								objMap.put(HeaderRow.getCell(iCounter).toString(), DataRow.getCell(iCounter).toString());
							}
						}
					}
					break;

				}
			}


		}

		catch(NullPointerException ne)
		{
			System.out.println("Exception while reading test data from sheet "+strSheetName);
			throw ne;
		}
		catch(Exception e)
		{
			throw e;
		}

		return objMap;

	}
}



