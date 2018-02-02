package com.epam.utils;

/**
 * The Class Constants. Contains all the constants 
 */
public class Constants {
	
	final public static String PERFORMTABLEACTIONS_XPATH = "(//input[@type='checkbox'])[position()=";

	/** The Constant ORFILEPATH. */
	final public static String ORFILEPATH="ORFilePath";
	
	/** The Constant CONFIGFILE. */
	final public static String CONFIGFILEPATH="./Configuration/Config.xml";
	
	/** The Constant ReportFilePath. */
	final public static String REPORTFILEPATH = "./Report";
	
	/** The Constant CONFIGFILE. */
	final public static String TESTDATAPATH="TestDataFilePath";	
	
	/** The Constant URL. */
	final public static String URL="AppURL";
	
	/** The Constant BrowserType. */
	final public static String BROWSERTYPE="BrowserType";
	
	public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**The ChromeDriver path**/
	final public static String CHROMEDRIVERPATH="ChromeDriverExecutablePath";
	
	final public static String IEEDRIVERPATH32Bit="IEEDRIVERPATH32Bit";
	final public static String IEEDRIVERPATH64bit="IEEDRIVERPATH64bit";
	
	final public static String STEPSCREENSHORTPATH="captureScreeLevel";
	
	final public static String TESTSUITECATEGORY="testSuiteCategory";	
	final public static String TESTSUITEDESCRIPTION="testSuiteDescription";
	final public static String TESTSUITEID="testSuiteId";
	final public static String TESTSUITENAME="testSuiteName";
	final public static String TESTSUITEURL="testSuiteURL";
	final public static String EXECUTIONENGINE="testSuiteExecutionEngine";	
	final public static String MOBILE="mobile";
	final public static String TESTSCENARIOREQUIREMENTID="testScenarioRequirementId";	
	final public static String TESTSCENATIOBUSINESSSCNARIODESC="testScenarioBusinessScenarioDesc";	
	final public static String ONFAIL="onFail";	
	public static final String  ONERROR="OnError";
	final public static String SCREENSHOTFILETYPE="screenshotFileType";	
	public static final String  SERVICEREQUESTPATH="ServiceRequestPath";
	public static final String  CAPTURESCREENSHOT="CaptureScreenShot";
	final public static String SCREENSHOTPATH="ScreenShotPath";	
	public static final String EMPTYVALUE = "novalue";
	public static final String SHAREDTESTRESULTPATH = "SharedTestResultPath";
	public static final String LOCALTESTRESULTPATH = "LocalTestResultPath";
	public static final String TESTREPORTRESULTFILE = "TestResultList.xml";
	public static final String DATEFORMATFOLDERNAME = "yyyyMMdd_HHmmssS";	
	/**The Oracle User */
	public static final String ORACLE_USER = "oracleuser";
	/**The Oracle Password */
	public static final String ORACLE_PWD = "oraclepwd";
	/**The Oracle Connection URL */
	public static final String ORACLE_CONNECTION_URL = "oracleurl";
	/**The Oracle driver Class */
	public static final String ORACLE_CLASS = "oracle.jdbc.driver.OracleDriver";
	/**The SQL User */
	public static final String SQL_USER = "sqluser";
	/**The SQL PWD */
	public static final String SQL_PWD = "sqlpwd";
	/**The SQL URL */
	public static final String SQL_CONNECTION_URL = "sqlurl";
	/**The MYSQL driver */
	public static final String SQL_CLASS = "com.imaginary.sql.msql.MsqlDriver";	
	/**The MYSQL UserName */
	public static final String MYSQL_USER = "mysqluser";
	/**The MYSQL Password */
	public static final String MYSQL_PWD = "mysqlpwd";
	/**The MYSQL URL */
	public static final String MYSQL_CONNECTION_URL = "mysqlurl";
	/**The MYSQL driver class */
	public static final String MYSQL_CLASS = "com.mysql.jdbc.Driver";
	/**The Cloud Execution flag */
	public static final String CLOUDEXEUTION = "cloudExecution";
	/**The SauceLabs EXE 32bit */
	public static final String SAHCEXE32 = "sauceExecutablePath32";
	/**The SauceLabs EXE 64bit */
	public static final String SAHCEXE64 = "sauceExecutablePath64";
	/**The SauceLabs flag */
	public static final String SAUCELABS = "saucelabs";
	/**The Sauce Browser Operating System*/
	public static final String SAUCEOS = "sauceos";
	/**The Sauce Browser */
	public static final String SAUCEBROWSWER = "sauceBrowser";
	/**The Sauce Browser Version */
	public static final String SAUCEBROWSWERVERSION = "sauceBrowserVersion";
	/**The Sauce Encrypted UserName */
	public static final String SAUCEUSERNAME = "SauceUserName";
	/**The Sauce Encrypted Password */
	public static final String SAUCEPWD = "SauceEncryptedPassword";
	/**The Sauce Proxy Flag */
	public static final String SAUCEPROXY = "sauceproxy";
	/**The Sauce Proxy System Host */
	public static final String SAUCEPROXYHost = "sauceproxyHost";
	/**The Sauce Proxy System Port */
	public static final String SAUCEPROXYPort = "sauceproxyPort";
	/**The Sauce Proxy System UserName */
	public static final String SAUCEPROXYUserId = "sauceproxyUserID";
	/**The Sauce Proxy System Password */
	public static final String SAUCEPROXYPwd = "sauceproxyPwd";
	public static final String DATABASENAME = "databaseName";
	
	
	
	/**The Cloud Execution flag */
	public static final String BROWSERsTACKEXEUTION = "browserstackExecution";
	/**The SauceLabs EXE 32bit */
	public static final String BROWSERsTACKEXE32 = "browserstackExecutablePath32";
	/**The SauceLabs EXE 64bit */
	public static final String BROWSERsTACKEXE64 = "browserstackExecutablePath64";
	/**The SauceLabs flag */
	public static final String BROWSERsTACKLABS = "browserstack";
	/**The Sauce Browser Operating System*/
	public static final String BROWSERsTACKOS = "browserstackos";
	/**The Sauce Browser */
	public static final String BROWSERsTACKBROWSWER = "browserstackBrowser";
	/**The Sauce Browser Version */
	public static final String BROWSERsTACKBROWSWERVERSION = "browserstackBrowserVersion";
	/**The Sauce Encrypted UserName */
	public static final String BROWSERsTACKUSERNAME = "browserstackUserName";
	/**The Sauce Encrypted Password */
	public static final String BROWSERsTACKKEY = "browserStackKey";
	
	/**WebServices details**/
	public static final String WEBSERVICEHOSTNAME = "webServicesHostName";
	public static final String WEBSERVICEPORT = "webServicesPort";
	public static final String WEBSERVICEAUTHENTICATION = "webServicesAuthentication";
	public static final String WEBSERVICEUSERKEY = "webServicesUserKey";
	public static final String WEBSERVICEPASSWORDKEY = "webServicesPasswordKey";
	public static final String WEBSERVICEUSER = "webServicesUser";
	public static final String WEBSERVICEPASSWORD = "webServicesPassword";
	
	/**Linux details**/
	public static final String LINUXHOSTNAME = "linuxHostName";
	public static final String LINUXPORT = "linuxPort";
	public static final String LINUXUSER = "linuxUser";
	public static final String LINUXPASSWORD = "linuxPassword";
	public static final String LINUXFILEPERMISSION = "0770";
	
	/** Appium Details **/
	public static final String DEVICENAME = "deviceName";
	public static final String PLATFORMNAME = "platformName";
	public static final String SPLATFORMVERSION = "sPlatformVersion";
	public static final String SAPP = "sApp";
	public static final String SAUTOLAUNCH = "sAutoLaunch";
	public static final String SDRIVERURL = "sDriverurl";
	public static final String SAUTOMATIONNAME = "automationName";
}
