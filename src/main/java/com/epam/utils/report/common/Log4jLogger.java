package com.epam.utils.report.common;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epam.utils.Config;

public class Log4jLogger {

	private static Logger logger = null;

	private Log4jLogger() {
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLog4jInstance() throws IOException {
        
        try {
            System.out.println("logger-->"+logger);
        if (logger == null) {
            File log4jfile = new File(Config.getConfig().getConfigProperty("Log4JPropertiesFilePath"));
            PropertyConfigurator.configure(log4jfile.getAbsolutePath());
            logger = Logger.getLogger(" Open Source Framework Automation ");
        }
        } catch(Exception e) {
             logger.debug("Issues with the Log4j Config Properties File : " + e.getMessage());
            return null;
        }
        return logger;
    }
}
