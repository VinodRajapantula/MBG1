package com.epam.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * The Class ReadXML. Contains the method to load the xml file.
 */
public class ReadXML {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(ReadXML.class);

	/** The read xml. */
	private static ReadXML readXML=null;

	/**
	 * Instantiates a new read xml.
	 */
	private ReadXML(){

	}

	/**
	 * Gets the single instance of ReadXML.
	 *
	 * @return single instance of ReadXML
	 */
	public static ReadXML getInstance(){
		if(null==readXML){
			readXML=new ReadXML();
		}
		return readXML;
	}

	/**
	 * Gets the xML doc.
	 *
	 * @param xmlFilePath the xml file path
	 * @return the xML doc
	 * @throws Exception the exception
	 */
	public Document getXMLDoc(String xmlFilePath)throws Exception{
		try{
			LOGGER.info("Started reading of the xml file: "+xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xmlDOC=dBuilder.parse(new File(xmlFilePath));
			xmlDOC.getDocumentElement().normalize();
			LOGGER.info("Completed reading of the xml file: "+xmlFilePath);
			return xmlDOC;
		}catch(Exception e){
			LOGGER.error("Exception occured while reading the xml file: "+xmlFilePath);
			throw new Exception("Exception occured while reading the xml file. Exception is:: "+e.getMessage());
		}
	}
}