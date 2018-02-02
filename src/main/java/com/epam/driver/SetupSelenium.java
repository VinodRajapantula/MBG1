package com.epam.driver;


import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.epam.utils.Config;
import com.epam.utils.Constants;

public class SetupSelenium {
	private WebDriver driver;
	DesiredCapabilities browserCapability = null;
	static Logger log = Logger.getLogger(SetupSelenium.class.getName());
	private ChromeDriverService chromeService = null;
	private InternetExplorerDriverService ieService = null;
	private static Process pr = null;
	private static DesiredCapabilities cap = null;	

	public WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * Get the browser instance
	 * @param sBrowserType
	 * @throws Exception
	 */
	public void startWebDriver(String sBrowserType) throws Exception {

		if (Config.getConfig().getConfigProperty(Constants.CLOUDEXEUTION)
				.equalsIgnoreCase("NO")) {

			if (sBrowserType.toLowerCase().contains("firefox")) {
				driver = new FirefoxDriver();
				log.info("Successfully started WebDriver Firefox Driver");
				} else if (sBrowserType.toLowerCase().contains("iexplore")) {
				    initiateIE();
	
			} else if (sBrowserType.toLowerCase().contains("chrome")) {
			    initiateChrome();
			} else if (sBrowserType.toLowerCase().contains("safari")) {
				log.info("User had specified Safari as the target browser");
				try {
					driver = new SafariDriver();
					log.info("Successfully started WebDriver Safari Driver");
				} catch (Exception e) {
					log.error("Exception::", e);
				}
			
			}
			driver.manage().window().maximize();
		} else if (!Config.getConfig().getConfigProperty(Constants.BROWSERsTACKEXEUTION)
				.equalsIgnoreCase("Yes"))
		  	     {
		    		initiateBrowserStack();
			     }
			else {
			    	//Call to SauceLabs method for initiating the execution on Sauce Cloud
			    	initiateSauceLabs();
			     }
		 driver.manage().window().maximize();
		 driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.MINUTES);
		 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	/**
	 * Setting up the Chrome version
	 */
	private void initiateIE(){
	    try {
		/*if (System.getProperty("sun.arch.data.model").toString().equals("32")) {
			System.setProperty("webdriver.ie.driver", Config.getConfig()
					.getConfigProperty(Constants.IEEDRIVERPATH32Bit));
		} else {
		    System.setProperty("webdriver.ie.driver", Config.getConfig()
				.getConfigProperty(Constants.IEEDRIVERPATH64bit));
		}*/
		
		
		
			System.setProperty("webdriver.ie.driver", Config.getConfig().getConfigProperty(Constants.IEEDRIVERPATH32Bit));
		

		ieService = InternetExplorerDriverService
				.createDefaultService();
		browserCapability = DesiredCapabilities.internetExplorer();
		browserCapability.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);
		browserCapability
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		browserCapability.setCapability("ignoreZoomSetting", true);
		driver = new InternetExplorerDriver(browserCapability);
		log.info("Successfully started WebDriver Google Chrome Driver with command switches");
               }
		catch (Exception e) {
			
			e.printStackTrace();
			}

                    }
	
	/**
	 * Setting up the Chrome version
	 */
	private void initiateChrome(){
	    try {
		System.setProperty(
			"webdriver.chrome.driver",
			Config.getConfig().getConfigProperty(
					Constants.CHROMEDRIVERPATH));
			chromeService = ChromeDriverService.createDefaultService();

			String commandSwitches = "WebDriverCommandSwitch"; // Get from
														// Configuration
                    	if (!commandSwitches.isEmpty()
                    			|| commandSwitches.contains("--")) {
                    		log.info("User had specified [" + commandSwitches
                    				+ "] command switch for Chrome Browser");
                    		ChromeOptions options = new ChromeOptions();
                    		String[] commandList = commandSwitches.split(",");
                    		for (String command : commandList) {
                    			log.debug("User had specified [" + command
                    					+ "] in command switch");
                    			options.addArguments(command);
                    			options.addArguments("--test-type");
                    			options.addArguments("chrome.switches","--disable-extensions");
                    		}
                    		log.debug("Starting the Chrome driver with Command Switches"
                    				+ commandSwitches);                    		
                    		driver = new ChromeDriver(chromeService, options);
                    		log.info("Successfully started WebDriver Google Chrome Driver with command switches");
                    	} else {
                    		log.debug("Starting the Chrome Driver");
                    		driver = new ChromeDriver(chromeService);
                    		log.info("Successfully started WebDriver Google Chrome Driver");
                    	}
			} catch (Exception e) {
			
			e.printStackTrace();
			}

                    }
	
	/**
	 * Setting the System properties
	 */
	private void initiateSauceLabs() {
		
		try {
			pr = Runtime.getRuntime().exec(
				"cmd.exe /c start "
						+ Config.getConfig().getConfigProperty(
								Constants.SAHCEXE32)
						+ " -u "
						+ Config.getConfig().getConfigProperty(
								Constants.SAUCEUSERNAME)
						+ " -k "
						+ Config.getConfig().getConfigProperty(
								Constants.SAUCEPWD));			
		pr.waitFor();
		Thread.sleep(120000);
		setBrowserCapability(Constants.SAUCELABS);
		if (Config.getConfig().getConfigProperty(Constants.SAUCEBROWSWER)
				.toUpperCase().equalsIgnoreCase("YES")) {
			setSystemProperties();
		}

		driver = new RemoteWebDriver(new URL("http://"
				+ Config.getConfig().getConfigProperty(
						Constants.SAUCEUSERNAME) + ":"
				+ Config.getConfig().getConfigProperty(Constants.SAUCEPWD)
				+ "@ondemand.saucelabs.com:80/wd/hub"), cap);
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * Setting the System properties
	 */
	private void initiateBrowserStack() {
		
		try {
			DesiredCapabilities capabilities=setBrowserCapability("borwserstack");	
			String URL = "http://" + Config.getConfig().getConfigProperty(
					Constants.BROWSERsTACKUSERNAME) + ":" + Constants.BROWSERsTACKKEY + "@hub.browserstack.com/wd/hub";
			driver = new RemoteWebDriver(new URL(URL), capabilities);	
	
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * Setting the System properties
	 */
	private void setSystemProperties() {
		
		try {
			System.setProperty("http.proxyHost", Config.getConfig()
					.getConfigProperty(Constants.SAUCEPROXYHost));
			System.setProperty("http.proxyPort", Config.getConfig()
					.getConfigProperty(Constants.SAUCEPROXYPort));
			System.setProperty("http.proxyUser", Config.getConfig()
					.getConfigProperty(Constants.SAUCEPROXYUserId));
			System.setProperty("http.proxyPassword", Config.getConfig()
					.getConfigProperty(Constants.SAUCEPROXYPwd));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * Setting the Sauce capabilities
	 * @return DesiredCapabilities
	 */
	private DesiredCapabilities setBrowserCapability(String runType) {
		boolean flag = false;
		String browserType;
		try {
			if(runType.equalsIgnoreCase("SAUCE")){
			browserType = Config.getConfig()
						.getConfigProperty(Constants.SAUCEBROWSWER).toUpperCase();
				
			cap.setCapability(CapabilityType.VERSION, Config.getConfig()
					.getConfigProperty(Constants.SAUCEOS));

			String sauceOs = Config.getConfig().getConfigProperty(
					Constants.SAUCEOS);
			setBrowserCapabilities(browserType,sauceOs);
			 cap.setCapability("name", "SauceExecution");
			}else {
				browserType = Config.getConfig()
						.getConfigProperty(Constants.BROWSERsTACKBROWSWER).toUpperCase();
		    
				cap.setCapability(CapabilityType.VERSION, Config.getConfig()
						.getConfigProperty(Constants.BROWSERsTACKOS));			
				String browserStackOs = Config.getConfig().getConfigProperty(
						Constants.BROWSERsTACKOS);
				String browser = Config.getConfig()
						.getConfigProperty(Constants.SAUCEBROWSWER).toUpperCase();
				setBrowserCapabilities(browserType,browserStackOs);
				 cap.setCapability("name", "BrowserStackExecution");				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return cap;
	}
	

	private void setBrowserCapabilities(String browserType,String platform) {
		char lastChar = 0;
		int osplatform = 0;
		boolean flag = false;
		int length = platform.length();
		if (browserType.contains("IE") || browserType.contains("INTERNET EXPLORER")
				|| browserType.contains("INTERNETEXPLORER")) {
			new DesiredCapabilities();
			cap = DesiredCapabilities.internetExplorer();				
		}
		if (browserType.contains("FF") || browserType.contains("FIREFOX")) {
			new DesiredCapabilities();
			cap = DesiredCapabilities.firefox();				
		} else if (browserType.contains("Chrome")) {
			new DesiredCapabilities();
			cap = DesiredCapabilities.chrome();				
		}
		if (length > 0) {
			lastChar = platform.charAt(length - 1);
			if (((int) lastChar > 47) && ((int) lastChar < 59)) {
				flag = true;
			}
		}
		if (platform.toUpperCase().contains("WINDOWS")
				|| platform.toUpperCase().contains("WIN")) {
			if (flag) {
				cap.setCapability(CapabilityType.PLATFORM, "Windows "
						+ lastChar);
			} else {
				cap.setCapability(CapabilityType.PLATFORM, "Windows XP");
			}
		}
		
	}

	/**
	 *  Closing the webdriver instance
	 */
	public void stopEngine() {
		driver.close();
		driver.quit();
		if (chromeService != null) {
			chromeService.stop();
		}
		if (ieService != null) {
			ieService.stop();
		}
		log.info("Successfully closed the browser and Web Driver instance");		
	}

	
}
