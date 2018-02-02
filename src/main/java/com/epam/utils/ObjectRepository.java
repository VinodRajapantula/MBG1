package com.epam.utils;

import java.util.HashMap;
import java.util.Map;




import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class ObjectRepository. Contains the methods related to OR file like loading the OR file and
 * fetching the respective object from the OR file. 
 */
public class ObjectRepository {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(Config.class);

	/** The or. */
	private static ObjectRepository or=null;
	
	/** The content. */
	private Map<String,String> content=null;
	
	/**
	 * Instantiates a new object repository.
	 *
	 * @throws Exception the exception
	 */
	private ObjectRepository()throws Exception{
		LOGGER.trace("Creating the ObjectRepository object");
		getORContent();
	}
	
	/**
	 * Gets the or.
	 *
	 * @return the or
	 * @throws Exception the exception
	 */
	public static ObjectRepository getOR() throws Exception{
		if(null==or){
			LOGGER.trace("Initializing the ObjectRepository object");
			or=new ObjectRepository();
		}
		return or;
	}
	
	
	/**
	 * Gets the oR content.
	 *
	 * @return the oR content
	 * @throws Exception the exception
	 */
	private Map<String,String> getORContent()throws Exception{
		LOGGER.info("Reading the ObjectRepository file");
		content=new HashMap<String,String>();
		try{			
			Document doc=ReadXML.getInstance().getXMLDoc(Config.getConfig().getConfigProperty(Constants.ORFILEPATH));
			NodeList nodeList=doc.getElementsByTagName("Element");
			for (int nodeCount=0;nodeCount<nodeList.getLength();nodeCount++){
				Node node= nodeList.item(nodeCount);
				if(node.hasAttributes()){
					NamedNodeMap map=node.getAttributes();
					String key=map.item(0).getNodeValue();
					if(node.hasChildNodes()){
						NodeList childNodesList=node.getChildNodes();
						for(int childNodeCount=0;childNodeCount<childNodesList.getLength();childNodeCount++){
							Node childNode=childNodesList.item(childNodeCount);
							if(!childNode.toString().contains("text")){
								String identifer=childNode.getNodeName();
								String identiferValue=childNode.getTextContent();
								
								LOGGER.trace("Found the identifer for the object '"+key+"', the identifer is '"+identifer+"' and the value is '"+identiferValue+"'");
								content.put(key.toUpperCase(), identifer+"="+identiferValue);
							}
						}
					}					
				}
			}
		}catch(Exception e){
			LOGGER.error("An exception occured while loading the OR file");
			throw new Exception("An exception occured while getting the content from config file: "+e.getMessage());
		}
		return content;
	}
	
	/**
	 * Gets the oR identifier.
	 *
	 * @param objecName the objec name
	 * @return the oR identifier
	 */
	public String getORIdentifier(String objectName){
		String key=content.get(objectName.toUpperCase()).split("=")[0];
		LOGGER.trace("Fetched the identifier with the object name '"+objectName+"' and the value is '"+key+"'");
		if(null==key){
			LOGGER.error("The specified object name '"+objectName+"' does not exist in the OR file");
		}
		return key;
	}
	
	/**
	 * Gets the oR identifier value.
	 *
	 * @param objecName the object name
	 * @return the oR identifier value
	 */
    @SuppressWarnings("unused")
    public String getORIdentifierValue(String objectName){
        StringBuilder value=new StringBuilder();
        String contentvalue[]=content.get(objectName.toUpperCase()).split("=");
        if(contentvalue.length>2){
        	for(int i=1;i<contentvalue.length;i++){
        		if(i>=2){
        			value.append("=");
        		}
             value.append(contentvalue[i]);
        	}
        }else{
             value.append(contentvalue[1]);
        }        
        LOGGER.trace("Fetched the identifier value with the object name '"+objectName+"' and the value is '"+value+"'");
        if(null==value){
            LOGGER.error("The specified object name '"+objectName+"' does not exist in the OR file");
        }
        return value.toString();
    }
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args)throws Exception{
		System.out.println(ObjectRepository.getOR().getORIdentifier("lnkNext"));
		System.out.println(ObjectRepository.getOR().getORIdentifierValue("lnkNext"));
	}

}
