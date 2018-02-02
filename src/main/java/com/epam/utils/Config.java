package com.epam.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class Config Contains the methods to read/update the Configuration file.
 */
public class Config {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(Config.class);


	/** The config. */
	private static Config config=null;

	/** The content. */
	private Map<String,String> content=null;

	/**
	 * Instantiates a new config.
	 *
	 * @throws Exception the exception
	 */
	private Config()throws Exception{
		LOGGER.trace("Creating the Config object");
		getConfigContent();
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 * @throws Exception the exception
	 */
	public static Config getConfig() throws Exception{
		if(null==config){
			LOGGER.trace("Creating the Config object");
			config=new Config();
			LOGGER.trace("Done the creation of Config object");
		}
		return config;
	}


	/**
	 * Gets the config content.
	 *
	 * @return the config content
	 * @throws Exception the exception
	 */
	private Map<String,String> getConfigContent()throws Exception{
		LOGGER.trace("Executing the function to read the Config.xml file content mentioned as:"+Constants.CONFIGFILEPATH);
		LOGGER.info("Reading the Configuartion file");
		content=new HashMap<String,String>();
		try{
			Document doc= ReadXML.getInstance().getXMLDoc(Constants.CONFIGFILEPATH);
			NodeList nodeList=doc.getElementsByTagName("Config");
			for (int nodeCount=0;nodeCount<nodeList.getLength();nodeCount++){
				Node node= nodeList.item(nodeCount);
				if(node.hasAttributes()){
					NamedNodeMap map=node.getAttributes();
					LOGGER.trace("Fetching for the name and value attribute values from the Coniguration file");
					content.put(map.item(0).getNodeValue().toUpperCase(),map.item(1).getNodeValue());
				}
			}
			LOGGER.info("Completed the reading of the Configuration file");
		}catch(Exception e){
			LOGGER.error("Exception occured while reading the contents from Configuration file. The excetpion messge is:"+e.getMessage());
			throw new Exception("An exception occured while getting the content from config file: "+e.getMessage());
		}
		return content;
	}

	/**
	 * Gets the config property.
	 *
	 * @param configName the config name
	 * @return the config property
	 * @throws Exception the exception
	 */
	public String getConfigProperty(String configName)throws Exception{
		LOGGER.trace("Try to fecth the content form Configuration file with key"+configName);
		String configValue=content.get(configName.toUpperCase());
		LOGGER.trace("Fetching the value from the Configuration file with key '"+configName+"' and the result is '"+configValue+"'");
		if(null==configValue){
			LOGGER.error("The specified '"+configName+"'configuartion property is not available");
			throw new Exception("The required config value '"+configName+"' is not available");
		}
		return configValue;
		
	}
	

	/**
	 * Update config property.
	 *
	 * @param configName the config name
	 * @param updatedValue the updated value
	 * @throws Exception the exception
	 */
	public void updateConfigProperty(String configName,String updatedValue)throws Exception{
		LOGGER.trace("Updating the Configuration file with the property '"+configName+"' and with the value '"+updatedValue+"'");
		if(content.containsKey(configName.toUpperCase())){
			LOGGER.info("Updating the existing property '"+configName+"'in the Configuration file with new value '"+updatedValue+"'");
			content.put(configName.toUpperCase(), updatedValue);
		}else{
			LOGGER.info("Adding new property '"+configName+"'to the Configuration file with value '"+updatedValue+"'");
			content.put(configName.toUpperCase(), updatedValue);
		}
	}


}
