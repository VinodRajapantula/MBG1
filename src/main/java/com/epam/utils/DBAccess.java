package com.epam.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.epam.driver.CoreDriverScript;


/**
 * The Class DBAccess. Used to get the DB access based on the database name.
 */
public class DBAccess {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(DBAccess.class);

	/** The statement. */
	private Statement statement = null;
	
	/** The prepared statement. */
	private PreparedStatement preparedStatement = null;
	
	/** The ResultSet object. */
	private ResultSet rs = null;
	
	/** The CallableStatement object. */
	private CallableStatement cstmt=null;


	/**
	 * Gets the connection: It gives the connection based on the database name. Before that ensure that the DB related url, username and password are configured
	 * in the config properties in the way like <DBNAME(Uppercase)>_User,<DBNAME(Uppercase)>_Pwd,<DBNAME(Uppercase)>__CONNECTION_URL and also ensure that
	 * <DBNAME(Uppercase)>_CLASS has to available in Constants.java 
	 *
	 * @param databaseName the database name
	 * @return the connection
	 * @throws Exception the exception
	 */
	public Connection getConnection(String databaseName)throws Exception{
		LOGGER.info("Executing getConnection() to get the connection from DB:"+databaseName);
		LOGGER.trace("Getting the username from the configuration properties");
		
		String user = CoreDriverScript.config.getConfig().getConfigProperty(databaseName.toUpperCase()+"_User");
		LOGGER.trace("Getting the password from the configuration properties");
		String pwd = CoreDriverScript.config.getConfig().getConfigProperty(databaseName.toUpperCase()+"_Pwd");
		LOGGER.trace("Getting the connecton url from the configuration properties");
		String connectionURL=CoreDriverScript.config.getConfig().getConfigProperty(databaseName.toUpperCase()+"_CONNECTION_URL");
		LOGGER.trace("Getting the class name from the configuration properties");
		String className=(Constants.class.getDeclaredField(databaseName.toUpperCase()+"_CLASS").get(String.class).toString());
		System.out.println("className"+className);
		System.out.println("connectionURL"+connectionURL);
		System.out.println("user"+user);
		System.out.println("pwd"+pwd);
		Class.forName(className);
		LOGGER.trace("Getting the connection object");
		return DriverManager.getConnection(connectionURL,user,pwd);
	}

	/**
	 * Execute update: Used to execute the non-select queries.
	 *
	 * @param con the connection object
	 * @param query the query
	 * @return the int
	 * @throws Exception the exception
	 */
	public int executeUpdate(Connection con,String query)throws Exception{
		LOGGER.info("Executng the executeUpdate method for the query:"+query);
		if(!query.startsWith("s") || !query.startsWith("S")){
			LOGGER.trace("Getting the statement object from the connecton");
			statement=con.createStatement();
			rs=null;
			LOGGER.trace("Executing the query:"+query);
			return statement.executeUpdate(query);
		}else{
			LOGGER.warn("The passed query is select query. Use executeQuery() to execute this query!");
			throw new Exception("This method will execute only non-select queries. Use executeQuery()");
		}
	}

	/**
	 * Execute query: Used to exeucte the Select queries and to get the respective value based on row number and column number.
	 *
	 * @param con the connection object
	 * @param query the query
	 * @param rowNum the row number
	 * @param colNum the col number
	 * @return the string
	 * @throws Exception the exception
	 */
	public String executeQuery(Connection con,String query,int rowNum,int colNum)throws Exception{
		LOGGER.info("Executing executeQuery() with the query:"+query+", required row number:"+rowNum+" and the required column number:"+colNum);
		if(query.startsWith("s") || query.startsWith("S")){
			LOGGER.trace("Creating the statement object from the connection object");
			statement=con.createStatement();
			LOGGER.trace("Executing the select query:"+query);
			rs=statement.executeQuery(query);
			rs.absolute(rowNum);
			return rs.getString(colNum);
		}else{
			LOGGER.warn("The passed query is non select query. Use executeUpdate() to execute this query!");
			throw new Exception("This method will execute only select queries. Use executeUpdate()");
		}

	}

	/**
	 * Execute query: Used to execute the select query.
	 *
	 * @param con the connection object
	 * @param query the query
	 * @throws Exception the exception
	 */
	public void executeQuery(Connection con,String query)throws Exception{
		LOGGER.info("Executing executeQuery() with the query:"+query);
		if(query.startsWith("s") || query.startsWith("S")){
			LOGGER.trace("Creating the statement object from the connection object");
			statement=con.createStatement();
			LOGGER.trace("Executing the select query:"+query);
			rs=statement.executeQuery(query);
		}else{
			LOGGER.warn("The passed query is non select query. Use executeUpdate() to execute this query!");
			throw new Exception("This method will execute only select queries. Use executeUpdate()");
		}

	}

	/**
	 * Gets the result set: This has to call after calling the executeQuery method. Then only Result Set object create.
	 *
	 * @return the result set
	 */
	public ResultSet getResultSet(){
		LOGGER.info("Getting the result set");
		return rs;
	}

	/**
	 * Execute procedure: Used to execute the procedures.
	 *
	 * @param con the connection object
	 * @param procedureName the procedure name
	 * @throws Exception the exception
	 */
	public void executeProcedure(Connection con,String procedureName)throws Exception{
		LOGGER.info("Executing the procedure:"+procedureName);
		CallableStatement cstmt=con.prepareCall("{call "+procedureName+"}");
		cstmt.execute();
	}
}
